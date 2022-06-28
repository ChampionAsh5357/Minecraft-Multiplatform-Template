/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.forge.registry;

import net.ashwork.mc.multiplatform.platform.core.registry.ObjectReference;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.RegistryObject;

/**
 * An implementation of {@link ObjectReference} for Forge {@link RegistryObject}s.
 *
 * @see ObjectReference
 */
public final class RegistryObjectDelegate<T> implements ObjectReference<T> {

    private final RegistryObject<T> object;

    /**
     * Default constructor
     *
     * @param object a {@link RegistryObject}
     */
    public RegistryObjectDelegate(final RegistryObject<T> object) {
        this.object = object;
    }

    @Override
    public ResourceKey<T> key() {
        return this.object.getKey();
    }

    @Override
    public T get() {
        return this.object.get();
    }
}
