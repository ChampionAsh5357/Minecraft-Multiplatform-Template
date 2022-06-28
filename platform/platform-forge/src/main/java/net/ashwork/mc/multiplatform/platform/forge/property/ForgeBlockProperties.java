/*
 * Copyright (c) ChampionAsh5357
 * SPDX-License-Identifier: MIT
 */

package net.ashwork.mc.multiplatform.platform.forge.property;

import net.ashwork.mc.multiplatform.platform.core.property.BlockProperties;
import net.ashwork.mc.multiplatform.platform.core.property.MinecraftBlockProperties;
import net.ashwork.mc.multiplatform.platform.core.util.ObjectReference;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Function;

/**
 * An implementation of {@link BlockProperties} for the Forge Mod Loader.
 *
 * @see BlockProperties
 */
public class ForgeBlockProperties extends MinecraftBlockProperties {

    /**
     * Default constructor.
     *
     * @param material the material the block is made out of
     * @param color a function that converts a state block to a color
     */
    public ForgeBlockProperties(Material material, Function<BlockState, MaterialColor> color) {
        super(material, color);
    }

    /**
     * Copy constructor.
     *
     * @param block the block to copy properties from
     */
    public ForgeBlockProperties(ObjectReference<Block> block) {
        super(block);
    }

    @Override
    public BlockProperties dropsLike(ObjectReference<Block> block) {
        this.properties.lootFrom(block);
        return this;
    }
}
