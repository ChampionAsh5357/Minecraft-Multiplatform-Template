/*
 * Copyright (c) ChampionAsh5357
 * SPDX-License-Identifier: MIT
 */

package net.ashwork.mc.multiplatform.platform.quilt;

import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;
import net.ashwork.mc.multiplatform.platform.core.manager.MinecraftRegistryPlatformManager;
import net.ashwork.mc.multiplatform.platform.core.manager.RegistryPlatformManager;
import org.quiltmc.loader.api.ModContainer;

/**
 * An implementation of {@link ModLoaderPlatform} for the Quilt Mod Loader.
 *
 * @see ModLoaderPlatform
 */
public final class QuiltLoaderPlatform implements ModLoaderPlatform {

    private final String modId;
    private final RegistryPlatformManager registries;

    /**
     * Default constructor.
     *
     * @param mod the mod container
     */
    public QuiltLoaderPlatform(final ModContainer mod) {
        this.modId = mod.metadata().id();
        this.registries = new MinecraftRegistryPlatformManager();
    }

    @Override
    public String modId() {
        return this.modId;
    }

    @Override
    public RegistryPlatformManager registries() {
        return this.registries;
    }
}
