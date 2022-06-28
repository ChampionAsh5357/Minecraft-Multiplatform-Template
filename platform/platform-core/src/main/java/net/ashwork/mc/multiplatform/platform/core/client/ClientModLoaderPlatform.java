/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.core.client;

import net.ashwork.mc.multiplatform.platform.core.client.particle.ParticlePlatformManager;
import org.jetbrains.annotations.ApiStatus;

import java.util.Objects;

/**
 * An accessor for client code within a platform required to run the current
 * mod on some mod loader.
 */
public interface ClientModLoaderPlatform {

    /**
     * Gets the current client platform instance.
     *
     * @return the platform instance
     * @throws NullPointerException if the platform has not been initialized
     */
    static ClientModLoaderPlatform get() {
        return Reference.get();
    }

    /**
     * Gets the platform manager for particles.
     *
     * @return the platform manager for particles
     */
    ParticlePlatformManager particle();

    /**
     * A reference holder to store the currently loaded client platform on
     * initialization.
     */
    @ApiStatus.Internal
    class Reference {

        private static ClientModLoaderPlatform platform;

        /**
         * Sets the current client platform instance.
         *
         * @param platform the platform instance
         * @throws NullPointerException if platform is {@code null}
         * @throws IllegalStateException if the platform is already set
         */
        public static void set(final ClientModLoaderPlatform platform) {
            if (Reference.platform != null) throw new IllegalStateException("Cannot set the client platform a second time.");
            Reference.platform = Objects.requireNonNull(platform, "Cannot set a null platform.");
        }

        /**
         * Gets the current client platform instance.
         *
         * @return the platform instance
         * @throws NullPointerException if the platform has not been initialized
         */
        public static ClientModLoaderPlatform get() {
            return Objects.requireNonNull(Reference.platform, "Tried to obtain platform before it was initialized.");
        }
    }
}
