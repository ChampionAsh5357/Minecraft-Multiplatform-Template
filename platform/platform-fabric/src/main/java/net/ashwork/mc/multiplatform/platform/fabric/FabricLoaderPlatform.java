/*
 * Copyright (c) ChampionAsh5357
 * SPDX-License-Identifier: MIT
 */

package net.ashwork.mc.multiplatform.platform.fabric;

import net.ashwork.mc.multiplatform.ModInstance;
import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;
import net.ashwork.mc.multiplatform.platform.core.manager.MinecraftRegistryPlatformManager;
import net.ashwork.mc.multiplatform.platform.core.manager.RegistryPlatformManager;

/**
 * An implementation of {@link ModLoaderPlatform} for the Fabric Mod Loader.
 *
 * @see ModLoaderPlatform
 */
public final class FabricLoaderPlatform implements ModLoaderPlatform {

    private final RegistryPlatformManager registries;

    /**
     * Default constructor.
     */
    public FabricLoaderPlatform() {
        this.registries = new MinecraftRegistryPlatformManager();
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
