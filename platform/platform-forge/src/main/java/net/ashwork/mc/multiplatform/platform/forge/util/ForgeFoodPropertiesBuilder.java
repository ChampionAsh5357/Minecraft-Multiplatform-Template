package net.ashwork.mc.multiplatform.platform.forge.util;

import net.ashwork.mc.multiplatform.platform.core.util.FoodPropertiesBuilder;
import net.ashwork.mc.multiplatform.platform.core.util.MinecraftFoodPropertiesBuilder;
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
