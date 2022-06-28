/*
 * Copyright (c) ChampionAsh5357
 * SPDX-License-Identifier: MIT
 */

package net.ashwork.mc.multiplatform.platform.forge.util;

import net.ashwork.mc.multiplatform.platform.core.util.ObjectReference;
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
