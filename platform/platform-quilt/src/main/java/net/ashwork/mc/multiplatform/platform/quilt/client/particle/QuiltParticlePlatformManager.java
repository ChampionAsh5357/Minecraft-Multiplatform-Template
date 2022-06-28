/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.quilt.client.particle;

import net.ashwork.mc.multiplatform.platform.core.client.particle.ParticlePlatformManager;
import net.ashwork.mc.multiplatform.platform.core.util.NotNullFunction;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

/**
 * An implementation of {@link ParticlePlatformManager} for the Quilt Mod Loader.
 *
 * @see ParticlePlatformManager
 */
public class QuiltParticlePlatformManager implements ParticlePlatformManager {

    @Override
    public <T extends ParticleOptions> void register(ParticleType<T> type, ParticleProvider<T> provider) {
        ParticleFactoryRegistry.getInstance().register(type, provider);
    }

    @Override
    public <T extends ParticleOptions> void register(ParticleType<T> type, NotNullFunction<SpriteSet, ParticleProvider<T>> providerFactory) {
        ParticleFactoryRegistry.getInstance().register(type, providerFactory::checkedApply);
    }
}
