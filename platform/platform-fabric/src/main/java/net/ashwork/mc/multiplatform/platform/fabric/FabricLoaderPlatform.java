/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.fabric;

import net.ashwork.mc.multiplatform.ModInstance;
import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;
import net.ashwork.mc.multiplatform.platform.core.dist.Dist;
import net.ashwork.mc.multiplatform.platform.core.network.NetworkPlatformManager;
import net.ashwork.mc.multiplatform.platform.core.property.PropertyPlatformManager;
import net.ashwork.mc.multiplatform.platform.core.property.MinecraftPropertyPlatformManager;
import net.ashwork.mc.multiplatform.platform.core.registry.MinecraftRegistryPlatformManager;
import net.ashwork.mc.multiplatform.platform.core.registry.RegistryPlatformManager;
import net.ashwork.mc.multiplatform.platform.fabric.network.FabricNetworkPlatformManager;
import net.fabricmc.loader.api.FabricLoader;

/**
 * An implementation of {@link ModLoaderPlatform} for the Fabric Mod Loader.
 *
 * @see ModLoaderPlatform
 */
public final class FabricLoaderPlatform implements ModLoaderPlatform {

    private final RegistryPlatformManager registries;
    private final PropertyPlatformManager data;
    private final Dist dist;
    private final NetworkPlatformManager network;

    /**
     * Default constructor.
     */
    public FabricLoaderPlatform() {
        this.registries = new MinecraftRegistryPlatformManager();
        this.data = new MinecraftPropertyPlatformManager();
        this.dist = switch (FabricLoader.getInstance().getEnvironmentType()) {
            case CLIENT -> Dist.CLIENT;
            case SERVER -> Dist.DEDICATED_SERVER;
        };
        this.network = new FabricNetworkPlatformManager();
    }

    @Override
    public String modId() {
        return ModInstance.ID;
    }

    @Override
    public RegistryPlatformManager registries() {
        return this.registries;
    }

    @Override
    public PropertyPlatformManager data() {
        return this.data;
    }

    @Override
    public Dist dist() {
        return this.dist;
    }

    @Override
    public NetworkPlatformManager network() {
        return this.network;
    }
}
