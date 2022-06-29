/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.fabric.network;

import net.ashwork.mc.multiplatform.platform.core.network.NetworkChannel;
import net.ashwork.mc.multiplatform.platform.core.network.NetworkPlatformManager;
import net.minecraft.resources.ResourceLocation;

/**
 * An implementation of {@link NetworkPlatformManager} for the Fabric Mod Loader.
 *
 * @see NetworkPlatformManager
 */
public class FabricNetworkPlatformManager implements NetworkPlatformManager {

    @Override
    public NetworkChannel createChannel(ResourceLocation name) {
        return new FabricNetworkChannel(name);
    }
}
