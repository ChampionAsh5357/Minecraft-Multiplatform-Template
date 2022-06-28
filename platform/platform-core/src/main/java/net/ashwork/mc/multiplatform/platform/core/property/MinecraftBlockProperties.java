/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.core.property;

import net.ashwork.mc.multiplatform.platform.core.util.ObjectReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Function;
import java.util.function.ToIntFunction;

/**
 * An implementation of {@link BlockProperties} for Vanilla-based Mod Loaders.
 *
 * @see BlockProperties
 */
public class MinecraftBlockProperties implements BlockProperties {

    protected final BlockBehaviour.Properties properties;

    /**
     * Default constructor.
     *
     * @param material the material the block is made out of
     * @param color a function that converts a state block to a color
     */
    public MinecraftBlockProperties(Material material, Function<BlockState, MaterialColor> color) {
        this.properties = BlockBehaviour.Properties.of(material, color);
    }

    /**
     * Copy constructor.
     *
     * @param block the block to copy properties from
     */
    public MinecraftBlockProperties(ObjectReference<Block> block) {
        this.properties = BlockBehaviour.Properties.copy(block.get());
    }

    @Override
    public BlockProperties noCollision() {
        this.properties.noCollission();
        return this;
    }

    @Override
    public BlockProperties noOcclusion() {
        this.properties.noOcclusion();
        return this;
    }

    @Override
    public BlockProperties friction(float friction) {
        this.properties.friction(friction);
        return this;
    }

    @Override
    public BlockProperties speedFactor(float speedFactor) {
        this.properties.speedFactor(speedFactor);
        return this;
    }

    @Override
    public BlockProperties jumpFactor(float jumpFactor) {
        this.properties.jumpFactor(jumpFactor);
        return this;
    }

    @Override
    public BlockProperties sound(SoundType type) {
        this.properties.sound(type);
        return this;
    }

    @Override
    public BlockProperties lightLevel(ToIntFunction<BlockState> lightEmission) {
        this.properties.lightLevel(lightEmission);
        return this;
    }

    @Override
    public BlockProperties ticksRandomly() {
        this.properties.randomTicks();
        return this;
    }

    @Override
    public BlockProperties dynamicShape() {
        this.properties.dynamicShape();
        return this;
    }

    @Override
    public BlockProperties noLootTable() {
        this.properties.noLootTable();
        return this;
    }

    @Override
    public BlockProperties dropsLike(ObjectReference<Block> block) {
        this.properties.dropsLike(block.get());
        return this;
    }

    @Override
    public BlockProperties air() {
        this.properties.air();
        return this;
    }

    @Override
    public BlockProperties validSpawn(BlockBehaviour.StateArgumentPredicate<EntityType<?>> validSpawn) {
        this.properties.isValidSpawn(validSpawn);
        return this;
    }

    @Override
    public BlockProperties redstoneConductor(BlockBehaviour.StatePredicate redstoneConductor) {
        this.properties.isRedstoneConductor(redstoneConductor);
        return this;
    }

    @Override
    public BlockProperties suffocateIn(BlockBehaviour.StatePredicate suffocateIn) {
        this.properties.isSuffocating(suffocateIn);
        return this;
    }

    @Override
    public BlockProperties blockView(BlockBehaviour.StatePredicate blockView) {
        this.properties.isViewBlocking(blockView);
        return this;
    }

    @Override
    public BlockProperties postProcess(BlockBehaviour.StatePredicate postProcess) {
        this.properties.hasPostProcess(postProcess);
        return this;
    }

    @Override
    public BlockProperties emissiveRendering(BlockBehaviour.StatePredicate emissiveRendering) {
        this.properties.emissiveRendering(emissiveRendering);
        return this;
    }

    @Override
    public BlockProperties requiresCorrectToolForDrops() {
        this.properties.requiresCorrectToolForDrops();
        return this;
    }

    @Override
    public BlockProperties destroyTime(float destroyTime) {
        this.properties.destroyTime(destroyTime);
        return this;
    }

    @Override
    public BlockProperties explosionResistance(float explosionResistance) {
        this.properties.explosionResistance(explosionResistance);
        return this;
    }

    @Override
    public BlockProperties offsetType(Function<BlockState, BlockBehaviour.OffsetType> offsetType) {
        this.properties.offsetType(offsetType);
        return this;
    }

    @Override
    public BlockBehaviour.Properties finish() {
        return this.properties;
    }
}
