package net.ashwork.mc.multiplatform.platform.forge.manager;

import net.ashwork.mc.multiplatform.platform.core.manager.DataPlatformManager;
import net.ashwork.mc.multiplatform.platform.core.util.FoodPropertiesBuilder;
import net.ashwork.mc.multiplatform.platform.forge.util.ForgeFoodPropertiesBuilder;

/**
 * An implementation of {@link DataPlatformManager} for the Forge Mod Loader.
 *
 * @see DataPlatformManager
 */
public class ForgeDataPlatformManager implements DataPlatformManager {

    @Override
    public FoodPropertiesBuilder newFood() {
        return new ForgeFoodPropertiesBuilder();
    }
}
