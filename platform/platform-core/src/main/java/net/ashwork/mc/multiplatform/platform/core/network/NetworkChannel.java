/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.core.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A channel used to register and send packets across a given network.
 */
public interface NetworkChannel {

    /**
     * Creates a network channel used to communicate between the logical client
     * and server.
     *
     * @param name the name of the channel
     * @return a new network channel
     */
    static NetworkChannel create(ResourceLocation name) {
        return NetworkPlatformManager.get().createChannel(name);
    }

    /**
     * Registers a packet that can be sent across the network. Makes no
     * assumptions about which direction that packet will be sent
     *
     * @param type the class of the packet data
     * @param encoder a consumer to write a packet to a buffer
     * @param decoder a function to read a packet from a buffer
     * @param handler a supplier used to handle the packet when received
     * @param <MSG> the type of the packet data
     */
    default <MSG> void registerPacket(Class<MSG> type, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, Supplier<PacketHandler<MSG>> handler) {
        this.registerPacket(type, encoder, decoder, handler, null);
    }

    /**
     * Registers a packet that can be sent across the network.
     *
     * @param type the class of the packet data
     * @param encoder a consumer to write a packet to a buffer
     * @param decoder a function to read a packet from a buffer
     * @param handler a supplier used to handle the packet when received
     * @param direction the network direction the packet will be sent in
     * @param <MSG> the type of the packet data
     */
    <MSG> void registerPacket(Class<MSG> type, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, Supplier<PacketHandler<MSG>> handler, @Nullable Direction direction);

    /**
     * Sends a packet to the specified player from the server to the client.
     *
     * @param message the packet to send
     * @param player the player receiving the packet
     * @param <MSG> the type of the packet data
     */
    <MSG> void sendToPlayer(MSG message, ServerPlayer player);

    /**
     * Sends a packet to all players in a level from the server to the client.
     *
     * @param message the packet to send
     * @param key the level where players will receive the packet
     * @param <MSG> the type of the packet data
     */
    <MSG> void sendToPlayersInLevel(MSG message, ResourceKey<Level> key);

    /**
     * Sends a packet to all players from the server to the client.
     *
     * @param message the packet to send
     * @param <MSG> the type of the packet data
     */
    <MSG> void sendToAllPlayers(MSG message);

    /**
     * Sends a packet from the client to the server.
     *
     * @param message the packet to send
     * @param <MSG> the type of the packet data
     */
    <MSG> void sendToServer(MSG message);

    /**
     * Sends a packet to all players tracking an entity from the server to the
     * client.
     *
     * @param message the packet to send
     * @param tracking the entity being tracked
     * @param <MSG> the type of the packet data
     */
    <MSG> void sendToPlayersTrackingEntity(MSG message, Entity tracking);

    /**
     * Sends a packet to all players tracking an entity and the tracked entity
     * if they are a player from the server to the client.
     *
     * @param message the packet to send
     * @param tracking the entity being tracked
     * @param <MSG> the type of the packet data
     */
    <MSG> void sendToPlayersTrackingEntityAndSelf(MSG message, Entity tracking);

    /**
     * Sends a packet to all players tracking a chunk from the server to the client.
     *
     * @param message the packet to send
     * @param chunk the chunk where players tracking will receive the packet
     * @param <MSG> the type of the packet data
     */
    <MSG> void sendToPlayersTrackingChunk(MSG message, LevelChunk chunk);

    /**
     * A handler for dealing with custom packets sent across the server or client.
     *
     * @param <MSG> the type of the packet data
     */
    @FunctionalInterface
    interface PacketHandler<MSG> {

        /**
         * Handles the packet on the correct thread. Used for handling packets on
         * the client.
         *
         * @param message the message sent from the server
         */
        default void handle(MSG message) {
            this.handle(message, null);
        }

        /**
         * Handles the packet on the correct thread. When {@code sender} is not
         * {@code null}, then the packet is handled on the server. Vice versa for
         * the client.
         *
         * @param message the message sent across the channel
         * @param sender the sender of the message, {@code null} if on the client,
         *               present if on the server
         */
        void handle(MSG message, @Nullable ServerPlayer sender);
    }

    /**
     * An enum used to hold which network and direction the packet will be sent in.
     */
    enum Direction {

        /**
         * Sends a packet clientbound through the play network packet.
         */
        PLAY_CLIENTBOUND,

        /**
         * Sends a packet serverbound through the play network packet.
         */
        PLAY_SERVERBOUND
    }
}
