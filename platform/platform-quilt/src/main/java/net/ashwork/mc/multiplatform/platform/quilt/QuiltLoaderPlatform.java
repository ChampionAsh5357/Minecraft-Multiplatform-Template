/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.quilt;

import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;
import net.ashwork.mc.multiplatform.platform.core.property.PropertyPlatformManager;
import net.ashwork.mc.multiplatform.platform.core.property.MinecraftPropertyPlatformManager;
import net.ashwork.mc.multiplatform.platform.core.registry.MinecraftRegistryPlatformManager;
import net.ashwork.mc.multiplatform.platform.core.registry.RegistryPlatformManager;
import org.quiltmc.loader.api.ModContainer;

/**
 * An implementation of {@link ModLoaderPlatform} for the Quilt Mod Loader.
 *
 * @see ModLoaderPlatform
 */
public final class QuiltLoaderPlatform implements ModLoaderPlatform {

    private final String modId;
    private final RegistryPlatformManager registries;
    private final PropertyPlatformManager data;

    /**
     * Default constructor.
     *
     * @param mod the mod container
     */
    public QuiltLoaderPlatform(final ModContainer mod) {
        this.modId = mod.metadata().id();
        this.registries = new MinecraftRegistryPlatformManager();
        this.data = new MinecraftPropertyPlatformManager();
    }

    @Override
    public String modId() {
        return this.modId;
    }

    @Override
    public RegistryPlatformManager registries() {
        return this.registries;
    }

    @Override
    public PropertyPlatformManager data() {
        return this.data;
    }
}
