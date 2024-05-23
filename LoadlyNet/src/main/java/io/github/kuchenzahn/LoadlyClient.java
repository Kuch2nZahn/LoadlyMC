package io.github.kuchenzahn;

import com.sun.tools.jdi.Packet;
import io.github.kuchenzahn.event.EventRegistry;
import io.github.kuchenzahn.handler.PacketChannelInboundHandler;
import io.github.kuchenzahn.handler.PacketDecoder;
import io.github.kuchenzahn.handler.PacketEncoder;
import io.github.kuchenzahn.packet.LoadlyPacket;
import io.github.kuchenzahn.registry.IPacketRegistry;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class LoadlyClient extends ChannelInitializer<Channel> {
    private LoadlyUUID clientID;
    private final Bootstrap bootstrap;
    private final IPacketRegistry packetRegistry;
    private final EventRegistry eventRegistry;

    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private Channel connectedChannel;

    public LoadlyClient(InetSocketAddress address, IPacketRegistry packetRegistry, Consumer<Future<? super Void>> doneCallback, EventRegistry eventRegistry, LoadlyUUID uuid) throws ConnectException {
        this.packetRegistry = packetRegistry;
        this.clientID = uuid;
        this.eventRegistry = eventRegistry;
        this.bootstrap = new Bootstrap()
                .option(ChannelOption.AUTO_READ, true)
                .group(workerGroup)
                .handler(this)
                .channel(NioSocketChannel.class);

        try {
            this.bootstrap.connect(address)
                    .awaitUninterruptibly().sync().addListener(doneCallback::accept);
        } catch (InterruptedException e) {
            throw new ConnectException("Failed to connect to server");
        }

        System.out.println(String.format("Started LoadlyClient with Client ID: %s", uuid.getUuid().toString()));
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new PacketDecoder(packetRegistry, clientID), new PacketEncoder(packetRegistry, clientID), new PacketChannelInboundHandler(eventRegistry));
        this.connectedChannel = channel;
    }

    public void sendPacket(LoadlyPacket packet){
        if(this.connectedChannel == null){
            throw new IllegalStateException("Channel not connected");
        }

        this.connectedChannel.writeAndFlush(packet);
    }

    public void shutdown() {
        try {
            workerGroup.shutdownGracefully().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
