package net.ashwork.mc.multiplatform.platform.fabric.client.manager;

import net.ashwork.mc.multiplatform.platform.core.client.manager.ParticlePlatformManager;
import net.ashwork.mc.multiplatform.platform.core.util.NotNullFunction;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

/**
 * An implementation of {@link ParticlePlatformManager} for the Fabric Mod Loader.
 *
 * @see ParticlePlatformManager
 */
public class FabricParticlePlatformManager implements ParticlePlatformManager {

    @Override
    public <T extends ParticleOptions> void register(ParticleType<T> type, ParticleProvider<T> provider) {
        ParticleFactoryRegistry.getInstance().register(type, provider);
    }

    @Override
    public <T extends ParticleOptions> void register(ParticleType<T> type, NotNullFunction<SpriteSet, ParticleProvider<T>> providerFactory) {
        ParticleFactoryRegistry.getInstance().register(type, providerFactory::checkedApply);
    }
}
