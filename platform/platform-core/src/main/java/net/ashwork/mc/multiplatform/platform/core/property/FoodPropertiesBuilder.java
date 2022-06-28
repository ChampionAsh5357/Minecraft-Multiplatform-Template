/*
 * Copyright (c) ChampionAsh5357
 * SPDX-License-Identifier: MIT
 */

package net.ashwork.mc.multiplatform.platform.core.property;

import net.ashwork.mc.multiplatform.platform.core.manager.PropertyPlatformManager;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

import java.util.function.Supplier;

/**
 * A builder for constructing food properties.
 */
public interface FoodPropertiesBuilder {

    /**
     * Creates a builder for food properties.
     *
     * @return a builder for food properties
     */
    static FoodPropertiesBuilder create() {
        return PropertyPlatformManager.get().food();
    }

    /**
     * Sets the number of ticks the food adds.
     *
     * @param nutrition the number of ticks the food adds
     * @return the builder instance
     */
    FoodPropertiesBuilder nutrition(int nutrition);

    /**
     * Sets the modifier for how satiated the eater is.
     *
     * @param saturationModifier the modifier for how satiated the eater is
     * @return the builder instance
     */
    FoodPropertiesBuilder saturationModifier(float saturationModifier);

    /**
     * Sets that the food is meat, used for feeding a wolf.
     *
     * @return the builder instance
     */
    FoodPropertiesBuilder meat();

    /**
     * Sets that the food can always be eaten, even when the user has no hunger.
     *
     * @return the builder instance
     */
    FoodPropertiesBuilder alwaysEat();

    /**
     * Sets that the food is eaten fast, usually 2x the speed of normal eating.
     *
     * @return the builder instance
     */
    FoodPropertiesBuilder eatFast();

    /**
     * Adds an effect to the user when the food is eaten.
     *
     * @param effect the effect to apply when eaten
     * @param probability a {@code [0,1]} number that determines the chance of the
     *                    effect being applied
     * @return the builder instance
     */
    FoodPropertiesBuilder effect(Supplier<MobEffectInstance> effect, float probability);

    /**
     * Creates the food properties based on the set properties.
     *
     * @return a new food properties instance
     */
    FoodProperties build();
}
