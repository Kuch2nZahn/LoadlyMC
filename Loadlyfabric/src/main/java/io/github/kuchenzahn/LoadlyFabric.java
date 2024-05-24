package io.github.kuchenzahn;

import io.github.kuchenzahn.event.EventRegistry;
import io.github.kuchenzahn.packet.impl.LoadlyHelloC2SPacket;
import io.github.kuchenzahn.registry.LoadlyPacketRegistry;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.UUID;

public class LoadlyFabric implements ModInitializer {
	public static final String MOD_ID = "loadlyfabric";

	private LoadlyClient loadlyClient = null;
	private EventRegistry eventRegistry;
	private LoadlyPacketRegistry packetRegistry;

	@Override
	public void onInitialize() {
		this.eventRegistry = new EventRegistry();
		this.packetRegistry = new LoadlyPacketRegistry();

		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {

			if(isLoadlyServer(handler.getServerInfo().address, 2244) == false) return;

			Logger logger = LoggerFactory.getLogger("LoadlyFabric");
			ServerInfo serverInfo = handler.getServerInfo();
			InetSocketAddress address = new InetSocketAddress(serverInfo.address, 2244);
			UUID uuid = MinecraftClient.getInstance().player.getUuid();

			logger.info("Connecting to Loadly server at " + address + " with UUID " + uuid);

            try {
                this.loadlyClient = new LoadlyClient(address, packetRegistry, (future -> {}), eventRegistry, LoadlyUUID.fromUUID(uuid));
            } catch (ConnectException e) {
				handler.getConnection().disconnect(Text.translatable("loadly.connection_error"));
            }

			LoadlyHelloC2SPacket packet = new LoadlyHelloC2SPacket();
			packet.setPacketReceiverUUID(LoadlyUUID.LoadlyUUIDS.SERVER.get());
			loadlyClient.sendPacket(packet);
        });

		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			if(loadlyClient != null) {
				loadlyClient.shutdown();
				loadlyClient = null;
			}
		});
	}

	private boolean isLoadlyServer(String address, int port) throws IllegalStateException {
		try (Socket ignored = new Socket(address, port)) {
			return true;
		} catch (ConnectException e) {
			return false;
		} catch (IOException e) {
			throw new IllegalStateException("Error while trying to check open port", e);
		}
	}
}