/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.forge.property;

import net.ashwork.mc.multiplatform.platform.core.property.FoodPropertiesBuilder;
import net.ashwork.mc.multiplatform.platform.core.property.MinecraftFoodPropertiesBuilder;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.Supplier;

/**
 * An implementation of {@link FoodPropertiesBuilder} for the Forge Mod Loader.
 *
 * @see FoodPropertiesBuilder
 */
public class ForgeFoodPropertiesBuilder extends MinecraftFoodPropertiesBuilder {

    @Override
    public FoodPropertiesBuilder effect(Supplier<MobEffectInstance> effect, float probability) {
        this.builder.effect(effect, probability);
        return this;
    }
}
