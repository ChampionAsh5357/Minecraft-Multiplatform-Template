package net.ashwork.mc.multiplatform.platform.core.manager;

import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;
import net.ashwork.mc.multiplatform.platform.core.util.FoodPropertiesBuilder;

/**
 * A manager for handling non-registry object data.
 */
public interface DataPlatformManager {

    /**
     * Gets the current platform instance.
     *
     * @return the platform instance
     * @throws NullPointerException if the platform has not been initialized
     */
    static DataPlatformManager get() {
        return ModLoaderPlatform.get().data();
    }

    /**
     * Creates a builder for food properties.
     *
     * @return a builder for food properties
     */
    FoodPropertiesBuilder newFood();
}
