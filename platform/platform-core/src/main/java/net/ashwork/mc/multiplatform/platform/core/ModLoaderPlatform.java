/*
 * Copyright (c) ChampionAsh5357
 * SPDX-License-Identifier: MIT
 */

package net.ashwork.mc.multiplatform.platform.core;

import net.ashwork.mc.multiplatform.platform.core.manager.DataPlatformManager;
import net.ashwork.mc.multiplatform.platform.core.manager.RegistryPlatformManager;
import org.jetbrains.annotations.ApiStatus;

import java.util.Objects;

/**
 * An accessor for common code within a platform required to run the current
 * mod on some mod loader.
 */
public interface ModLoaderPlatform {

    /**
     * Gets the current platform instance.
     *
     * @return the platform instance
     * @throws NullPointerException if the platform has not been initialized
     */
    static ModLoaderPlatform get() {
        return Reference.get();
    }

    /**
     * Gets the identifier of the mod.
     *
     * @return the identifier of the mod
     */
    String modId();

    /**
     * Gets the platform manager for registries.
     *
     * @return the platform manager for registries
     */
    RegistryPlatformManager registries();

    /**
     * Gets the platform manager for object data.
     *
     * @return the platform manager for object data
     */
    DataPlatformManager data();

    /**
     * A reference holder to store the currently loaded platform on
     * initialization.
     */
    @ApiStatus.Internal
    class Reference {

        private static ModLoaderPlatform platform;

        /**
         * Sets the current platform instance.
         *
         * @param platform the platform instance
         * @throws NullPointerException if platform is {@code null}
         * @throws IllegalStateException if the platform is already set
         */
        public static void set(final ModLoaderPlatform platform) {
            if (Reference.platform != null) throw new IllegalStateException("Cannot set the platform a second time.");
            Reference.platform = Objects.requireNonNull(platform, "Cannot set a null platform.");
        }

        /**
         * Gets the current platform instance.
         *
         * @return the platform instance
         * @throws NullPointerException if the platform has not been initialized
         */
        public static ModLoaderPlatform get() {
            return Objects.requireNonNull(Reference.platform, "Tried to obtain platform before it was initialized.");
        }
    }
}
