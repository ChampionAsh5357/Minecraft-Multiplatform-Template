/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.forge.manager;

import net.ashwork.mc.multiplatform.platform.core.manager.PropertyPlatformManager;
import net.ashwork.mc.multiplatform.platform.core.property.BlockProperties;
import net.ashwork.mc.multiplatform.platform.core.property.FoodPropertiesBuilder;
import net.ashwork.mc.multiplatform.platform.core.util.ObjectReference;
import net.ashwork.mc.multiplatform.platform.forge.property.ForgeBlockProperties;
import net.ashwork.mc.multiplatform.platform.forge.property.ForgeFoodPropertiesBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Function;

/**
 * An implementation of {@link PropertyPlatformManager} for the Forge Mod Loader.
 *
 * @see PropertyPlatformManager
 */
public class ForgePropertyPlatformManager implements PropertyPlatformManager {

    @Override
    public FoodPropertiesBuilder food() {
        return new ForgeFoodPropertiesBuilder();
    }

    @Override
    public BlockProperties block(Material material, Function<BlockState, MaterialColor> color) {
        return new ForgeBlockProperties(material, color);
    }

    @Override
    public BlockProperties block(ObjectReference<Block> block) {
        return new ForgeBlockProperties(block);
    }
}
