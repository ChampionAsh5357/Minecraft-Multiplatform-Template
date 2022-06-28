/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.forge.client.particle;

import net.ashwork.mc.multiplatform.platform.core.client.particle.ParticlePlatformManager;
import net.ashwork.mc.multiplatform.platform.core.util.NotNullFunction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.List;
import java.util.function.Consumer;

/**
 * An implementation of {@link ParticlePlatformManager} for the Forge Mod Loader.
 *
 * @see ParticlePlatformManager
 */
public class ForgeParticlePlatformManager implements ParticlePlatformManager {

    private List<Consumer<ParticleEngine>> factories;

    /**
     * Default constructor.
     *
     * @param modBus the event bus for mod initialization
     */
    public ForgeParticlePlatformManager(IEventBus modBus) {
        modBus.addListener(this::registerParticleFactories);
    }

    /**
     * An event listener for registering particle factories at the correct time
     *
     * @param event the event instance
     */
    private void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        var engine = Minecraft.getInstance().particleEngine;
        this.factories.forEach(factory -> factory.accept(engine));
        this.factories.clear();
        this.factories = null;
    }

    @Override
    public <T extends ParticleOptions> void register(ParticleType<T> type, ParticleProvider<T> provider) {
        this.factories.add(engine -> engine.register(type, provider));
    }

    @Override
    public <T extends ParticleOptions> void register(ParticleType<T> type, NotNullFunction<SpriteSet, ParticleProvider<T>> providerFactory) {
        this.factories.add(engine -> engine.register(type, providerFactory::checkedApply));
    }
}
