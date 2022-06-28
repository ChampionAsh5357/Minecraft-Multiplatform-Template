/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.forge.client;

import net.ashwork.mc.multiplatform.platform.core.client.ClientModLoaderPlatform;
import net.ashwork.mc.multiplatform.platform.core.client.manager.ParticlePlatformManager;
import net.ashwork.mc.multiplatform.platform.forge.client.manager.ForgeParticlePlatformManager;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * An implementation of {@link ClientModLoaderPlatform} for the Forge Mod Loader.
 *
 * @see ClientModLoaderPlatform
 */
public final class ForgeClientLoaderPlatform implements ClientModLoaderPlatform {

    private final ParticlePlatformManager particle;

    /**
     * Default constructor.
     *
     * @param modBus the event bus for mod initialization
     */
    public ForgeClientLoaderPlatform(IEventBus modBus) {
        this.particle = new ForgeParticlePlatformManager(modBus);
    }

    @Override
    public ParticlePlatformManager particle() {
        return this.particle;
    }
}
