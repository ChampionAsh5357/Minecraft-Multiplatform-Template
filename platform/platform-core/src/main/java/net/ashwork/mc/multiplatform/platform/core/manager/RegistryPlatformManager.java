/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.core.manager;

import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;
import net.ashwork.mc.multiplatform.platform.core.util.ObjectReference;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

/**
 * A manager for handling registries within a platform.
 */
public interface RegistryPlatformManager {

    /**
     * Gets the current platform instance.
     *
     * @return the platform instance
     * @throws NullPointerException if the platform has not been initialized
     */
    static RegistryPlatformManager get() {
        return ModLoaderPlatform.get().registries();
    }

    /**
     * Gets or creates a registrar for a given registry.
     *
     * @param registryKey the key of the registry
     * @return a writable registry to register objects to
     * @param <T> the type of the objects in the registry
     */
    <T> WritableRegistry<T> registrar(final ResourceKey<? extends Registry<T>> registryKey);

    /**
     * A registrar used to register objects to a registry.
     *
     * @param <T> the type of the objects in the registry
     */
    interface WritableRegistry<T> {

        /**
         * Registers an object and returns a reference to said object. The object
         * will be registered under the current mod identifier.
         *
         * @param name the name of the object
         * @param object the supplied object to register
         * @return a reference to the registered object
         * @param <I> the type of the registered object
         */
        default <I extends T> ObjectReference<I> register(final String name, final Supplier<? extends I> object) {
            return this.register(new ResourceLocation(ModLoaderPlatform.get().modId(), name), object);
        }

        /**
         * Registers an object and returns a reference to said object.
         *
         * @param key the registry key of the object
         * @param object the supplied object to register
         * @return a reference to the registered object
         * @param <I> the type of the registered object
         */
        default <I extends T> ObjectReference<I> register(final ResourceKey<T> key, final Supplier<? extends I> object) {
            return this.register(key.location(), object);
        }

        /**
         * Registers an object and returns a reference to said object.
         *
         * @param name the registry name of the object
         * @param object the supplied object to register
         * @return a reference to the registered object
         * @param <I> the type of the registered object
         */
        <I extends T> ObjectReference<I> register(final ResourceLocation name, final Supplier<? extends I> object);
    }
}
