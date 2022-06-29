/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.fabric.client.network;

import net.ashwork.mc.multiplatform.platform.fabric.network.FabricNetworkChannel;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * A utility used to hold client related handlers for a network.
 */
public final class FabricClientNetworkChannel {

    /**
     * Constructor to prevent initialization.
     */
    private FabricClientNetworkChannel() {
        throw new AssertionError("Do not initialize " + this.getClass().getSimpleName());
    }

    /**
     * Initializes the logical client packet receiver.
     *
     * @param name the name of the channel sending the packets
     * @param codecs an index holding packet codec handlers
     */
    public static void init(ResourceLocation name, List<FabricNetworkChannel.PacketCodec<?>> codecs) {
        ClientPlayNetworking.registerGlobalReceiver(name, (client, listener, buf, sender) -> {
            var type = buf.readVarInt();
            handlePacket(client, buf, codecs.get(type));
        });
    }

    /**
     * Sends a packet from the client to the server.
     *
     * @param name the name of the channel sending the packet
     * @param buf a buffer containing the packet data
     */
    public static void sendToServer(ResourceLocation name, FriendlyByteBuf buf) {
        ClientPlayNetworking.send(name, buf);
    }

    /**
     * A helper to decode and handle the received packet on the client.
     *
     * @param client the client receiving the packet
     * @param buf the buffer holding the packet data
     * @param codec an encoder/decoder used to process the packet
     * @param <MSG> the type of the packet data
     */
    private static <MSG> void handlePacket(Minecraft client, FriendlyByteBuf buf, FabricNetworkChannel.PacketCodec<MSG> codec) {
        MSG packet = codec.decode(buf);
        client.execute(() -> codec.handler().handle(packet));
    }
}
