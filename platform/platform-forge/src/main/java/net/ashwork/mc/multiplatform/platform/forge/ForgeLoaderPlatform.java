/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.forge;

import net.ashwork.mc.multiplatform.ModInstance;
import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;
import net.ashwork.mc.multiplatform.platform.core.dist.Dist;
import net.ashwork.mc.multiplatform.platform.core.network.NetworkPlatformManager;
import net.ashwork.mc.multiplatform.platform.core.property.PropertyPlatformManager;
import net.ashwork.mc.multiplatform.platform.core.registry.RegistryPlatformManager;
import net.ashwork.mc.multiplatform.platform.forge.network.ForgeNetworkPlatformManager;
import net.ashwork.mc.multiplatform.platform.forge.property.ForgePropertyPlatformManager;
import net.ashwork.mc.multiplatform.platform.forge.registry.ForgeRegistryPlatformManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.loading.FMLEnvironment;

/**
 * An implementation of {@link ModLoaderPlatform} for the Forge Mod Loader.
 *
 * @see ModLoaderPlatform
 */
public final class ForgeLoaderPlatform implements ModLoaderPlatform {

    private final RegistryPlatformManager registries;
    private final PropertyPlatformManager data;
    private final Dist dist;
    private final NetworkPlatformManager network;

    /**
     * Default constructor.
     *
     * @param modBus the event bus for mod initialization
     */
    public ForgeLoaderPlatform(final IEventBus modBus) {
        this.registries = new ForgeRegistryPlatformManager(modBus);
        this.data = new ForgePropertyPlatformManager();
        this.dist = switch (FMLEnvironment.dist) {
            case CLIENT -> Dist.CLIENT;
            case DEDICATED_SERVER -> Dist.DEDICATED_SERVER;
        };
        this.network = new ForgeNetworkPlatformManager();
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
