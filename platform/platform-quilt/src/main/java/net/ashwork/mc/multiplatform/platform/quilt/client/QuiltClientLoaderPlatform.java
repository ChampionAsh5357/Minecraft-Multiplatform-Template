/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.quilt.client;

import net.ashwork.mc.multiplatform.platform.core.client.ClientModLoaderPlatform;
import net.ashwork.mc.multiplatform.platform.core.client.manager.ParticlePlatformManager;
import net.ashwork.mc.multiplatform.platform.quilt.client.manager.QuiltParticlePlatformManager;

/**
 * An implementation of {@link ClientModLoaderPlatform} for the Quilt Mod Loader.
 *
 * @see ClientModLoaderPlatform
 */
public final class QuiltClientLoaderPlatform implements ClientModLoaderPlatform {

    private final ParticlePlatformManager particle;

    /**
     * Default constructor.
     */
    public QuiltClientLoaderPlatform() {
        this.particle = new QuiltParticlePlatformManager();
    }

    @Override
    public ParticlePlatformManager particle() {
        return this.particle;
    }
}
