/*
 * Copyright (c) ChampionAsh5357
 * SPDX-License-Identifier: MIT
 */

package net.ashwork.mc.multiplatform.platform.forge.manager;

import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;
import net.ashwork.mc.multiplatform.platform.core.manager.RegistryPlatformManager;
import net.ashwork.mc.multiplatform.platform.core.util.ObjectReference;
import net.ashwork.mc.multiplatform.platform.forge.util.RegistryObjectDelegate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * An implementation of {@link RegistryPlatformManager} for the Forge Mod Loader.
 *
 * @see RegistryPlatformManager
 */
public final class ForgeRegistryPlatformManager implements RegistryPlatformManager {

    private final Map<ResourceKey<?>, ForgeWritableRegistry<?>> registers;

    /**
     * Default constructor.
     *
     * @param modBus the event bus for mod initialization
     */
    public ForgeRegistryPlatformManager(final IEventBus modBus) {
        this.registers = new HashMap<>();
        modBus.addListener(this::registerObjects);
    }

    @Override
    public <T> WritableRegistry<T> register(final ResourceKey<? extends Registry<T>> registryKey) {
        @SuppressWarnings("unchecked")
        final WritableRegistry<T> register = (WritableRegistry<T>) this.registers.computeIfAbsent(registryKey, key -> {
            @SuppressWarnings("unchecked")
            final ForgeWritableRegistry<T> r = new ForgeWritableRegistry<>((ResourceKey<? extends Registry<T>>) key);
            return r;
        });
        return register;
    }

    /**
     * An event listener for registering objects at the correct time.
     *
     * @param event the event instance
     */
    private void registerObjects(final RegisterEvent event) {
        final ForgeWritableRegistry<?> register = this.registers.get(event.getRegistryKey());
        if (register != null) this.registerObjectsInternal(event, register);
    }

    /**
     * A generic method for easily handling the registration of objects.
     *
     * @param event the event instance
     * @param registrar the writable registry to register objects to
     * @param <T> the type of the objects in the registry
     */
    @ApiStatus.Internal
    private <T> void registerObjectsInternal(final RegisterEvent event, final ForgeWritableRegistry<T> registrar) {
        event.register(registrar.registryKey, registrar);
    }

    /**
     * An implementation of {@link WritableRegistry} for the Forge Mod Loader.
     *
     * @see WritableRegistry
     */
    public static final class ForgeWritableRegistry<T> implements WritableRegistry<T>, Consumer<RegisterEvent.RegisterHelper<T>> {

        private final ResourceKey<? extends Registry<T>> registryKey;
        private List<Consumer<RegisterEvent.RegisterHelper<T>>> objects;

        /**
         * Default constructor.
         *
         * @param registryKey the key of the registry
         */
        public ForgeWritableRegistry(final ResourceKey<? extends Registry<T>> registryKey) {
            this.registryKey = registryKey;
            this.objects = new ArrayList<>();
        }

        @Override
        public void accept(RegisterEvent.RegisterHelper<T> helper) {
            this.objects.forEach(consumer -> consumer.accept(helper));
            this.objects.clear();
            this.objects = null;
        }

        @Override
        public <I extends T> ObjectReference<I> register(ResourceLocation name, Supplier<? extends I> object) {
            this.objects.add(helper -> helper.register(name, object.get()));
            return new RegistryObjectDelegate<>(RegistryObject.create(name, this.registryKey, ModLoaderPlatform.get().modId()));
        }
    }
}
