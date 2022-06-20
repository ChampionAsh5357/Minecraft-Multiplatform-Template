package net.ashwork.mc.multiplatform.platform.core.manager;

import net.ashwork.mc.multiplatform.platform.core.util.FoodPropertiesBuilder;
import net.ashwork.mc.multiplatform.platform.core.util.MinecraftFoodPropertiesBuilder;

/**
 * An implementation of {@link DataPlatformManager} for Vanilla-based Mod Loaders.
 *
 * @see DataPlatformManager
 */
public class MinecraftDataPlatformManager implements DataPlatformManager {

    @Override
    public FoodPropertiesBuilder newFood() {
        return new MinecraftFoodPropertiesBuilder();
    }
}
