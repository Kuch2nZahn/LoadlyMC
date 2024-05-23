package io.github.kuchenzahn.mixin;

import io.github.kuchenzahn.LoadlyClient;
import io.github.kuchenzahn.LoadlyUUID;
import io.github.kuchenzahn.event.EventRegistry;
import io.github.kuchenzahn.registry.IPacketRegistry;
import io.github.kuchenzahn.registry.LoadlyPacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.ServerList;
import net.minecraft.server.ServerMetadata;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Mixin(MultiplayerScreen.class)
public abstract class MultiplayerScreenMixin {
}
