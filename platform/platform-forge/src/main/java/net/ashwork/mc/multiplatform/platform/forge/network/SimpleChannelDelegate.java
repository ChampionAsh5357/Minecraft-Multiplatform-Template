/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.forge.network;

import net.ashwork.mc.multiplatform.platform.core.dist.Dist;
import net.ashwork.mc.multiplatform.platform.core.network.NetworkChannel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An implementation of {@link NetworkChannel} for Forge {@link SimpleChannel}s.
 *
 * @see NetworkChannel
 */
public class SimpleChannelDelegate implements NetworkChannel {

    private final SimpleChannel channel;
    private int idCounter;

    /**
     * Default constructor.
     *
     * @param name the name of the channel
     */
    public SimpleChannelDelegate(ResourceLocation name) {
        var version = "1";
        this.channel = NetworkRegistry.newSimpleChannel(name, () -> version, version::equals, version::equals);
        this.idCounter = 0;
    }

    @Override
    public <MSG> void registerPacket(Class<MSG> type, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, Supplier<PacketHandler<MSG>> handler, @Nullable Direction direction) {
        SimpleChannel.MessageBuilder<MSG> builder = direction == null ? this.channel.messageBuilder(type, this.idCounter++)
                : this.channel.messageBuilder(type, this.idCounter++, switch (direction) {
            case PLAY_CLIENTBOUND -> NetworkDirection.PLAY_TO_CLIENT;
            case PLAY_SERVERBOUND -> NetworkDirection.PLAY_TO_SERVER;
        });
        builder.encoder(encoder).decoder(decoder).consumer((message, ctxs) -> {
            var ctx = ctxs.get();
            return switch (ctx.getDirection()) {
                case PLAY_TO_SERVER -> {
                    ctx.enqueueWork(() -> handler.get().handle(message, ctx.getSender()));
                    yield true;
                }
                case PLAY_TO_CLIENT -> {
                    ctx.enqueueWork(() -> Dist.CLIENT.runWhenOn(() -> () -> handler.get().handle(message)));
                    yield true;
                }
                default -> false;
            };
        }).add();
    }

    @Override
    public <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        this.channel.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    @Override
    public <MSG> void sendToPlayersInLevel(MSG message, ResourceKey<Level> key) {
        this.channel.send(PacketDistributor.DIMENSION.with(() -> key), message);
    }

    @Override
    public <MSG> void sendToAllPlayers(MSG message) {
        this.channel.send(PacketDistributor.ALL.noArg(), message);
    }

    @Override
    public <MSG> void sendToServer(MSG message) {
        this.channel.send(PacketDistributor.SERVER.noArg(), message);
    }

    @Override
    public <MSG> void sendToPlayersTrackingEntity(MSG message, Entity tracking) {
        this.channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> tracking), message);
    }

    @Override
    public <MSG> void sendToPlayersTrackingEntityAndSelf(MSG message, Entity tracking) {
        this.channel.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> tracking), message);
    }

    @Override
    public <MSG> void sendToPlayersTrackingChunk(MSG message, LevelChunk chunk) {
        this.channel.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), message);
    }
}
