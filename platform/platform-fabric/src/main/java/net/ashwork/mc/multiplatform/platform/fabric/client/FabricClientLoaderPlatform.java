/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.fabric.client;

import net.ashwork.mc.multiplatform.platform.core.client.ClientModLoaderPlatform;
import net.ashwork.mc.multiplatform.platform.core.client.particle.ParticlePlatformManager;
import net.ashwork.mc.multiplatform.platform.fabric.client.particle.FabricParticlePlatformManager;

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
