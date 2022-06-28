/*
 * Copyright (c) ChampionAsh5357
 * SPDX-License-Identifier: MIT
 */

package net.ashwork.mc.multiplatform.platform.core.property;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

import java.util.function.Supplier;

/**
 * An implementation of {@link FoodPropertiesBuilder} for Vanilla-based Mod Loaders.
 *
 * @see FoodPropertiesBuilder
 */
public class MinecraftFoodPropertiesBuilder implements FoodPropertiesBuilder {

    protected final FoodProperties.Builder builder;

    /**
     * Default constructor.
     */
    public MinecraftFoodPropertiesBuilder() {
        this.builder = new FoodProperties.Builder();
    }

    @Override
    public FoodPropertiesBuilder nutrition(int nutrition) {
        this.builder.nutrition(nutrition);
        return this;
    }

    @Override
    public FoodPropertiesBuilder saturationModifier(float saturationModifier) {
        this.builder.saturationMod(saturationModifier);
        return this;
    }

    @Override
    public FoodPropertiesBuilder meat() {
        this.builder.meat();
        return this;
    }

    @Override
    public FoodPropertiesBuilder alwaysEat() {
        this.builder.alwaysEat();
        return this;
    }

    @Override
    public FoodPropertiesBuilder eatFast() {
        this.builder.fast();
        return this;
    }

    @Override
    public FoodPropertiesBuilder effect(Supplier<MobEffectInstance> effect, float probability) {
        this.builder.effect(effect.get(), probability);
        return this;
    }

    @Override
    public FoodProperties build() {
        return this.builder.build();
    }
}
