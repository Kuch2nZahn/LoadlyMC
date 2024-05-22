package io.github.kuchenzahn;

import io.github.kuchenzahn.handler.PacketDecoder;
import io.github.kuchenzahn.handler.PacketEncoder;
import io.github.kuchenzahn.packet.LoadlyPacket;
import io.github.kuchenzahn.registry.IPacketRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class LoadlyServer extends ChannelInitializer<Channel> {
    private LoadlyUUID serverAddressUUID;

    private final ServerBootstrap bootstrap;
    private final IPacketRegistry packetRegistry;

    private EventLoopGroup parentGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    private Channel connectedChannel;

    public LoadlyServer(InetSocketAddress adress, IPacketRegistry packetRegistry, Consumer<Future<? super Void>> doneCallback, LoadlyUUID uuid) {
        this.serverAddressUUID = uuid;
        this.packetRegistry = packetRegistry;
        this.bootstrap = new ServerBootstrap()
                .option(ChannelOption.AUTO_READ, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .group(parentGroup, workerGroup)
                .childHandler(this)
                .channel(NioServerSocketChannel.class);

        try {
            this.bootstrap.bind(adress)
                    .awaitUninterruptibly().sync().addListener(doneCallback::accept);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new PacketDecoder(packetRegistry, serverAddressUUID), new PacketEncoder(packetRegistry, serverAddressUUID));
        this.connectedChannel = channel;
    }

    public void send(LoadlyPacket loadlyPacket){
        this.connectedChannel.writeAndFlush(loadlyPacket);
    }

    public void shutdown() {
        try {
            parentGroup.shutdownGracefully().get();
            workerGroup.shutdownGracefully().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
