/*
 * Copyright (c) ChampionAsh5357
 * SPDX-License-Identifier: MIT
 */

package net.ashwork.mc.multiplatform.platform.core.property;

import net.ashwork.mc.multiplatform.platform.core.manager.PropertyPlatformManager;
import net.ashwork.mc.multiplatform.platform.core.util.ObjectReference;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Function;
import java.util.function.ToIntFunction;

/**
 * A builder for specifying the properties of a block.
 */
public interface BlockProperties {

    /**
     * Creates the properties for a block. Assumes all states of the block has
     * the same color pulled from the material.
     *
     * @param material the material the block is made out of
     */
    static BlockProperties of(Material material) {
        return PropertyPlatformManager.get().block(material);
    }

    /**
     * Creates the properties for a block. Assumes all states of the block has
     * the same color pulled from a dye.
     *
     * @param material the material the block is made out of
     * @param color the color of the block when dyed
     * @return the properties of a block
     */
    static BlockProperties of(Material material, DyeColor color) {
        return PropertyPlatformManager.get().block(material, color);
    }

    /**
     * Creates the properties for a block. Assumes all states of the block has
     * the same color.
     *
     * @param material the material the block is made out of
     * @param color the color of a block
     * @return the properties of a block
     */
    static BlockProperties of(Material material, MaterialColor color) {
        return PropertyPlatformManager.get().block(material, color);
    }

    /**
     * Creates the properties for a block.
     *
     * @param material the material the block is made out of
     * @param color a function that converts a state block to a color
     * @return the properties of a block
     */
    static BlockProperties of(Material material, Function<BlockState, MaterialColor> color) {
        return PropertyPlatformManager.get().block(material, color);
    }

    /**
     * Copies the properties from another block.
     *
     * @param block the block to copy properties from
     * @return the properties of a block
     */
    static BlockProperties copy(ObjectReference<Block> block) {
        return PropertyPlatformManager.get().block(block);
    }

    /**
     * Sets that entities can walk through this block. Additionally, light will
     * pass through the block as if it were air.
     *
     * @return the properties instance
     */
    BlockProperties noCollision();

    /**
     * Sets that light can pass through this block as if it were air.
     *
     * @return the properties instance
     */
    BlockProperties noOcclusion();

    /**
     * Sets a scalar to determine how much an entity would slip when stopping on this
     * block. Values less than {@code 1F} will increase how far the entity slips
     * across the block.
     *
     * @param friction a scalar which determine how much an entity would slip on this
     *                 block
     * @return the properties instance
     */
    BlockProperties friction(float friction);

    /**
     * Sets a scalar to determine how much this block should increase an entity's
     * speed when traversing across it. Values greater than {@code 1F} will
     * increase an entity's speed when walking across this block.
     *
     * @param speedFactor a scalar which increases an entity's speed when
     *                    traversing across this block
     * @return the properties instance
     */
    BlockProperties speedFactor(float speedFactor);

    /**
     * Sets a scalar to determine how high an entity can jump off this block.
     * Values greater than {@code 1F} will increase an entity's jump height
     *
     * @param jumpFactor a scalar which increases an entity's jump height on this
     *                   block
     * @return the properties instance
     */
    BlockProperties jumpFactor(float jumpFactor);

    /**
     * Sets the sounds that will be played when the block is broken, stepped on,
     * placed, hit, and falls on.
     *
     * @param type an object holding the block sounds
     * @return the properties instance
     */
    BlockProperties sound(SoundType type);

    /**
     * Sets the amount of light the block can emit. Assumes that all states will
     * emit the same amount of light.
     *
     * @implSpec
     * The light level should be in the range of {@code [0,15]}.
     *
     * @param lightEmission the amount of light to emit from the block
     * @return the properties instance
     */
    default BlockProperties lightLevel(int lightEmission) {
        return this.lightLevel(state -> lightEmission);
    }

    /**
     * Sets the amount of light the block can emit.
     *
     * @implSpec
     * The light level should be in the range of {@code [0,15]}.
     *
     * @param lightEmission a function which gets the amount light a state block
     *                      can emit
     * @return the properties instance
     */
    BlockProperties lightLevel(ToIntFunction<BlockState> lightEmission);

    /**
     * Sets the strength of the block to {@code 0F}. It can be broken instantly and
     * destroyed by any explosion.
     *
     * @return the properties instance
     */
    default BlockProperties instabreak() {
        return this.strength(0F);
    }

    /**
     * Sets the strength of the block. Assumes that the time to destroy and explosion
     * resistance is the same.
     *
     * @param strength a scalar of the length of time to destroy a block and a bound
     *                 for the minimum strength an explosion to have to affect this
     *                 block
     * @return the properties instance
     */
    default BlockProperties strength(float strength) {
        return this.strength(strength, strength);
    }

    /**
     * Sets the strength of a block, defined as the block's time to destroy and
     * resistance to explosions.
     *
     * @param destroyTime a scalar to determine how much longer it will take to
     *                    destroy a block
     * @param explosionResistance a bound which specifies the minimum strength an
     *                            explosion must have to affect this block
     * @return the properties instance
     *
     * @see #destroyTime(float)
     * @see #explosionResistance(float)
     */
    default BlockProperties strength(float destroyTime, float explosionResistance) {
        return this.destroyTime(destroyTime).explosionResistance(explosionResistance);
    }

    /**
     * Sets that the block will call {@link Block#randomTick(BlockState, ServerLevel, BlockPos, RandomSource)}
     * at random intervals.
     *
     * @return the properties instance
     */
    BlockProperties ticksRandomly();

    /**
     * Sets that the state data of a block should not be cached.
     *
     * @return the properties instance
     */
    BlockProperties dynamicShape();

    /**
     * Sets that the block will not drop anything when broken. A loot table does
     * not need to exist for this block.
     *
     * @return the properties instance
     */
    BlockProperties noLootTable();

    /**
     * Sets that the block will drop the same loot as another block.
     *
     * @param block the block holding the defined loot table.
     * @return the properties instance
     */
    BlockProperties dropsLike(ObjectReference<Block> block);

    /**
     * Sets that this block should be treated as if it were air.
     *
     * @return the properties instance
     */
    BlockProperties air();

    /**
     * Sets a predicate which determines whether a specific entity type can spawn on
     * this block.
     *
     * @param validSpawn a predicate which returns {@code true} if the entity type
     *                   can spawn on this block
     * @return the properties instance
     */
    BlockProperties validSpawn(BlockBehaviour.StateArgumentPredicate<EntityType<?>> validSpawn);

    /**
     * Sets a predicate which determines whether the block can conduct a redstone
     * signal through it.
     *
     * @param redstoneConductor a predicate which returns {@code true} if a redstone
     *                          signal can be conducted through this block
     * @return the properties instance
     */
    BlockProperties redstoneConductor(BlockBehaviour.StatePredicate redstoneConductor);

    /**
     * Sets a predicate which determines whether the block can suffocate an entity.
     *
     * @param suffocateIn a predicate which returns {@code true} if the block can
     *                    suffocate an entity
     * @return the properties instance
     */
    BlockProperties suffocateIn(BlockBehaviour.StatePredicate suffocateIn);

    /**
     * Sets a predicate which determines whether the block blocks the view of the
     * camera. When {@code true}, displays a block overlay while inside and will
     * render light and shadows as if they were unaffected by the model.
     *
     * @param blockView a predicate which returns {@code true} if the block can
     *                  block the camera's view
     * @return the properties instance
     */
    BlockProperties blockView(BlockBehaviour.StatePredicate blockView);

    /**
     * Sets a predicate which determines whether the block should update immediately
     * after the chunk is generated.
     *
     * @param postProcess a predicate which returns {@code true} if the block should
     *                    be updated immediately upon generation
     * @return the properties instance
     */
    BlockProperties postProcess(BlockBehaviour.StatePredicate postProcess);

    /**
     * Sets a predicate which determines whether the block should radiate white light
     * softly.
     *
     * @param emissiveRendering a predicate which returns {@code true} if the block
     *                          should emit soft, white light
     * @return the properties instance
     */
    BlockProperties emissiveRendering(BlockBehaviour.StatePredicate emissiveRendering);

    /**
     * Sets that the block will only drop when mined with a tool of the specified
     * tier level or higher.
     *
     * @return the properties instance
     */
    BlockProperties requiresCorrectToolForDrops();

    /**
     * Sets a scalar which is divided to determine how long it takes to destroy a
     * block. If set to {@code -1F}, the block cannot be broken in non-operator
     * modes.
     *
     * @param destroyTime a scalar to determine how much longer it will take to
     *                    destroy a block
     * @return the properties instance
     */
    BlockProperties destroyTime(float destroyTime);

    /**
     * Sets a bound which determines the minimum strength an explosion must have to
     * affect this block.
     *
     * <p>A typical explosion from a single TNT requires an explosion resistance
     * of {@code 14F} on average, or {@code 18F} in the worst case scenario to
     * not be affected at the epicenter.
     *
     * @param explosionResistance a bound which specifies the minimum strength an
     *                            explosion must have to affect this block
     * @return the properties instance
     */
    BlockProperties explosionResistance(float explosionResistance);

    /**
     * Sets the type of offset that should be randomly applied to a state block's
     * location when rendering. Assumes that all states of a block have the same
     * type of offset.
     *
     * @apiNote
     * The offset can be applied to a collision by getting the associated offset
     * and then moving the shape via {@link VoxelShape#move(double, double, double)}.
     *
     * @param type a type of offset to apply for each block
     * @return the properties instance
     */
    default BlockProperties offsetType(BlockBehaviour.OffsetType type) {
        return this.offsetType(state -> type);
    }

    /**
     * Sets the type of offset that should be randomly applied to a state block's
     * location when rendering.
     *
     * @apiNote
     * The offset can be applied to a collision by getting the associated offset
     * and then moving the shape via {@link VoxelShape#move(double, double, double)}.
     *
     * @param offsetType a function that sets a type of offset for each state
     * @return the properties instance
     */
    BlockProperties offsetType(Function<BlockState, BlockBehaviour.OffsetType> offsetType);

    /**
     * Defines the vanilla object representing the block's properties.
     *
     * @return a vanilla block properties instance
     */
    BlockBehaviour.Properties finish();
}
