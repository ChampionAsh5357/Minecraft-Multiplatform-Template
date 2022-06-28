/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.core.property;

import net.ashwork.mc.multiplatform.platform.core.registry.ObjectReference;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Function;

/**
 * An implementation of {@link PropertyPlatformManager} for Vanilla-based Mod Loaders.
 *
 * @see PropertyPlatformManager
 */
public class MinecraftPropertyPlatformManager implements PropertyPlatformManager {

    @Override
    public FoodPropertiesBuilder food() {
        return new MinecraftFoodPropertiesBuilder();
    }

    @Override
    public BlockProperties block(Material material, Function<BlockState, MaterialColor> color) {
        return new MinecraftBlockProperties(material, color);
    }

    @Override
    public BlockProperties block(ObjectReference<Block> block) {
        return new MinecraftBlockProperties(block);
    }
}
