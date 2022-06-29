/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.quilt.network;

import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.ashwork.mc.multiplatform.platform.core.dist.Dist;
import net.ashwork.mc.multiplatform.platform.core.network.NetworkChannel;
import net.ashwork.mc.multiplatform.platform.quilt.client.network.QuiltClientNetworkChannel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.lifecycle.api.event.ServerLifecycleEvents;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An implementation of {@link NetworkChannel} for the Quilt Mod Loader.
 *
 * @see NetworkChannel
 */
public class QuiltNetworkChannel implements NetworkChannel {

    private final ResourceLocation name;
    private final Object2IntMap<Class<?>> messageMap;
    private final List<PacketCodec<?>> codecs;
    private boolean isServerInitialized, isClientInitialized;
    private final MutableObject<MinecraftServer> server;

    /**
     * Default constructor
     *
     * @param name the name of the channel
     */
    public QuiltNetworkChannel(ResourceLocation name) {
        this.name = name;
        this.messageMap = new Object2IntOpenHashMap<>();
        this.codecs = new ArrayList<>();
        this.isServerInitialized = false;
        this.isClientInitialized = false;
        this.server = new MutableObject<>();
        ServerLifecycleEvents.STARTING.register(this.server::setValue);
        ServerLifecycleEvents.STOPPED.register(s -> this.server.setValue(null));
    }

    @Override
    public <MSG> void registerPacket(Class<MSG> type, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, Supplier<PacketHandler<MSG>> handler, @Nullable Direction direction) {
        if (direction != null) {
            switch (direction) {
                case PLAY_CLIENTBOUND -> {
                    if (!isClientInitialized) {
                        Dist.CLIENT.runWhenOn(() -> () -> QuiltClientNetworkChannel.init(this.name, this.codecs));
                        this.isClientInitialized = true;
                    }
                }
                case PLAY_SERVERBOUND -> {
                    if (!isServerInitialized) {
                        ServerPlayNetworking.registerGlobalReceiver(name, (server, player, listener, buf, sender) -> {
                            var packetType = buf.readVarInt();
                            handlePacket(server, player, buf, codecs.get(packetType));
                        });
                        this.isServerInitialized = true;
                    }
                }
            }
        }
        this.messageMap.put(type, this.codecs.size());
        this.codecs.add(new PacketCodec<>(encoder, decoder, handler));
    }

    @Override
    public <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        ServerPlayNetworking.send(player, this.name, toBuf(message));
    }

    @Override
    public <MSG> void sendToPlayersInLevel(MSG message, ResourceKey<Level> key) {
        this.server.getValue().getPlayerList().broadcastAll(createPacket(message), key);
    }

    @Override
    public <MSG> void sendToAllPlayers(MSG message) {
        this.server.getValue().getPlayerList().broadcastAll(createPacket(message));
    }

    @Override
    public <MSG> void sendToServer(MSG message) {
        Dist.CLIENT.runWhenOn(() -> () -> QuiltClientNetworkChannel.sendToServer(this.name, toBuf(message)));
    }

    @Override
    public <MSG> void sendToPlayersTrackingEntity(MSG message, Entity tracking) {
        if (tracking.getLevel().getChunkSource() instanceof ServerChunkCache cache)
            cache.broadcast(tracking, createPacket(message));
    }

    @Override
    public <MSG> void sendToPlayersTrackingEntityAndSelf(MSG message, Entity tracking) {
        if (tracking.getLevel().getChunkSource() instanceof ServerChunkCache cache)
            cache.broadcastAndSend(tracking, createPacket(message));
    }

    @Override
    public <MSG> void sendToPlayersTrackingChunk(MSG message, LevelChunk chunk) {
        var packet = createPacket(message);
        if (chunk.getLevel().getChunkSource() instanceof ServerChunkCache cache)
            cache.chunkMap.getPlayers(chunk.getPos(), false).forEach(player -> player.connection.send(packet));
    }

    /**
     * Constructs a vanilla packet from a custom message.
     *
     * @param message the message being sent across the network
     * @return the packet being sent across the network
     * @param <MSG> the type of the packet data
     */
    private <MSG> Packet<?> createPacket(MSG message) {
        return ServerPlayNetworking.createS2CPacket(this.name, toBuf(message));
    }

    /**
     * Writes the packet data to a buffer.
     *
     * @param message the message being sent across the network
     * @return a buffer containing the packet data
     * @param <MSG> the type of the packet data
     */
    private <MSG> FriendlyByteBuf toBuf(MSG message) {
        var buf = new FriendlyByteBuf(Unpooled.buffer());
        var id = this.messageMap.getInt(message.getClass());
        @SuppressWarnings("unchecked")
        PacketCodec<MSG> codec = (PacketCodec<MSG>) this.codecs.get(id);
        buf.writeVarInt(id);
        codec.encode(message, buf);
        return buf;
    }

    /**
     * A helper to decode and handle the received packet on the server.
     *
     * @param server the server receiving the packet
     * @param player the player who sent the packet
     * @param buf the buffer holding the packet data
     * @param codec an encoder/decoder used to process the packet
     * @param <MSG> the type of the packet data
     */
    private static <MSG> void handlePacket(MinecraftServer server, ServerPlayer player, FriendlyByteBuf buf, PacketCodec<MSG> codec) {
        MSG packet = codec.decode(buf);
        server.execute(() -> codec.handler().handle(packet, player));
    }

    /**
     * A codec object used to hold encoding/decoding handlers pertaining to a
     * specific packet type.
     *
     * @param <MSG> the type of the packet data
     */
    public static class PacketCodec<MSG> {

        private final BiConsumer<MSG, FriendlyByteBuf> encoder;
        private final Function<FriendlyByteBuf, MSG> decoder;
        private final Supplier<PacketHandler<MSG>> handler;

        /**
         * Default constructor.
         *
         * @param encoder a consumer to write a packet to a buffer
         * @param decoder a function to read a packet from a buffer
         * @param handler a supplier used to handle the packet when received
         */
        public PacketCodec(BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, Supplier<PacketHandler<MSG>> handler) {
            this.encoder = encoder;
            this.decoder = decoder;
            this.handler = handler;
        }

        /**
         * Encodes the packet data to the buffer.
         *
         * @param message the message to be encoded
         * @param buf the buffer to encode the message to
         */
        public void encode(MSG message, FriendlyByteBuf buf) {
            this.encoder.accept(message, buf);
        }

        /**
         * Decodes the packet data object from the buffer.
         *
         * @param buf the buffer to decode the message from
         * @return the packet data object
         */
        public MSG decode(FriendlyByteBuf buf) {
            return this.decoder.apply(buf);
        }

        /**
         * Returns the handler used to execute logic when a packet is received.
         *
         * @return the handler used to execute logic when a packet is received
         */
        public PacketHandler<MSG> handler() {
            return this.handler.get();
        }
    }
}
