package io.github.kuchenzahn.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.kuchenzahn.LoadlyClient;
import io.github.kuchenzahn.LoadlyFabric;
import io.github.kuchenzahn.LoadlyUUID;
import io.github.kuchenzahn.event.EventRegistry;
import io.github.kuchenzahn.packet.LoadlyPacket;
import io.github.kuchenzahn.registry.LoadlyPacketRegistry;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.render.Tessellator;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

@Mixin(MultiplayerServerListWidget.ServerEntry.class)
public class ServerEntryMixin {
    @Shadow @Final private ServerInfo server;

    private ArrayList<ServerInfo> loadlyServers = new ArrayList<>();

    @Inject(at = @At("RETURN"), method = "<init>")
    public void onInit(MultiplayerServerListWidget multiplayerServerListWidget, MultiplayerScreen screen, ServerInfo server, CallbackInfo ci) {

        boolean loadlyServer = isLoadlyServer(server.address, 2244);
        if (loadlyServer) {
            loadlyServers.add(server);
        }
    }

    @Inject(at = @At("TAIL"), method = "render")
    private void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta, CallbackInfo ci) {
        if(loadlyServers.contains(server)){
            Identifier starIcon = new Identifier(LoadlyFabric.MOD_ID, "star.png");
            Identifier loadlyLogo = new Identifier(LoadlyFabric.MOD_ID, "logo_transparent.png");

            RenderSystem.enableBlend();
            context.drawTexture(starIcon, x - 20, y, 0.0f, 0.0f, 20, 20, 20, 20);
            context.drawTexture(loadlyLogo, x - 16, y + 4, 0.0f, 0.0f, 12, 12, 12, 12);
            RenderSystem.disableBlend();
        }

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
