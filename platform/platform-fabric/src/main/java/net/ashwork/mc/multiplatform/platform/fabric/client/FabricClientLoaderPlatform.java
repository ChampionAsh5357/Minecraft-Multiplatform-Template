/*
 * Copyright (c) ChampionAsh5357
 * SPDX-License-Identifier: MIT
 */

package net.ashwork.mc.multiplatform.platform.fabric.client;

import net.ashwork.mc.multiplatform.platform.core.client.ClientModLoaderPlatform;
import net.ashwork.mc.multiplatform.platform.core.client.manager.ParticlePlatformManager;
import net.ashwork.mc.multiplatform.platform.fabric.client.manager.FabricParticlePlatformManager;

/**
 * An implementation of {@link ClientModLoaderPlatform} for the Fabric Mod Loader.
 *
 * @see ClientModLoaderPlatform
 */
public final class FabricClientLoaderPlatform implements ClientModLoaderPlatform {

    private final ParticlePlatformManager particle;

    /**
     * Default constructor.
     */
    public FabricClientLoaderPlatform() {
        this.particle = new FabricParticlePlatformManager();
    }

    @Override
    public ParticlePlatformManager particle() {
        return this.particle;
    }
}
