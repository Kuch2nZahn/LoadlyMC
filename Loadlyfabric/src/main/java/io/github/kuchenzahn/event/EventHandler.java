package io.github.kuchenzahn.event;

import io.github.kuchenzahn.packet.impl.LoadlyHelloS2CPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.Text;

public class EventHandler {
    @PacketSubscriber
    public void onHelloPacket(LoadlyHelloS2CPacket packet) {
        ToastManager toastManager = MinecraftClient.getInstance().getToastManager();
        toastManager.add(new SystemToast(SystemToast.Type.PERIODIC_NOTIFICATION, Text.translatable("loadly.serverconnect.title"), Text.translatable("loadly.serverconnect.description")));
    }
}
