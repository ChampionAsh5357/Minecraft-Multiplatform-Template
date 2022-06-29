/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.core.network;

import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;
import net.minecraft.resources.ResourceLocation;

/**
 * A manager for handling networks.
 */
public interface NetworkPlatformManager {

    /**
     * Gets the current platform instance.
     *
     * @return the platform instance
     * @throws NullPointerException if the platform has not been initialized
     */
    static NetworkPlatformManager get() {
        return ModLoaderPlatform.get().network();
    }

    /**
     * Creates a network channel used to communicate between the logical client
     * and server.
     *
     * @param name the name of the channel
     * @return a new network channel
     */
    NetworkChannel createChannel(ResourceLocation name);
}
