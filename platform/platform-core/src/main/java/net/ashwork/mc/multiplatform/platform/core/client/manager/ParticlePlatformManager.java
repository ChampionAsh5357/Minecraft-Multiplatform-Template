/*
 * Copyright (c) ChampionAsh5357
 * SPDX-License-Identifier: MIT
 */

package net.ashwork.mc.multiplatform.platform.core.client.manager;

import net.ashwork.mc.multiplatform.platform.core.client.ClientModLoaderPlatform;
import net.ashwork.mc.multiplatform.platform.core.util.NotNullFunction;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

/**
 * A manager for handling particles within a platform.
 */
public interface ParticlePlatformManager {

    /**
     * Gets the current client platform instance.
     *
     * @return the platform instance
     * @throws NullPointerException if the platform has not been initialized
     */
    static ParticlePlatformManager get() {
        return ClientModLoaderPlatform.get().particle();
    }

    /**
     * Registers a particle factory which does not use a particle sprite set.
     *
     * @param type the type of the particle being generated
     * @param provider the provider used to create the particle
     * @param <T> the type of the data within the particle
     */
    <T extends ParticleOptions> void register(ParticleType<T> type, ParticleProvider<T> provider);

    /**
     * Registers a particle factory which requires a particle sprite sheet.
     *
     * @apiNote
     * This requires a sprite set definition at {@code textures/particle}.
     *
     * @param type the type of the particle being generated
     * @param providerFactory the factory used to generate the provider to create
     *                        the particle
     * @param <T> the type of the data within the particle
     */
    <T extends ParticleOptions> void register(ParticleType<T> type, NotNullFunction<SpriteSet, ParticleProvider<T>> providerFactory);
}
