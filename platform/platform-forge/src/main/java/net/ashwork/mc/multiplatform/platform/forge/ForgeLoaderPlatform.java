/*
 * Copyright (c) ChampionAsh5357
 * SPDX-License-Identifier: MIT
 */

package net.ashwork.mc.multiplatform.platform.forge;

import net.ashwork.mc.multiplatform.ModInstance;
import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;
import net.ashwork.mc.multiplatform.platform.core.manager.RegistryPlatformManager;
import net.ashwork.mc.multiplatform.platform.forge.manager.ForgeRegistryPlatformManager;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * An implementation of {@link ModLoaderPlatform} for the Forge Mod Loader.
 *
 * @see ModLoaderPlatform
 */
public final class ForgeLoaderPlatform implements ModLoaderPlatform {

    private final RegistryPlatformManager registries;

    /**
     * Default constructor.
     *
     * @param modBus the event bus for mod initialization
     */
    public ForgeLoaderPlatform(final IEventBus modBus) {
        this.registries = new ForgeRegistryPlatformManager(modBus);
    }

    @Override
    public String modId() {
        return ModInstance.ID;
    }

    @Override
    public RegistryPlatformManager registries() {
        return this.registries;
    }
}
