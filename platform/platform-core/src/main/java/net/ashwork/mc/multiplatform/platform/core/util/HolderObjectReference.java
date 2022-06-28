/*
 * Copyright (c) ChampionAsh5357
 * SPDX-License-Identifier: MIT
 */

package net.ashwork.mc.multiplatform.platform.core.util;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;

/**
 * An implementation of {@link ObjectReference} for Vanilla-based holders.
 *
 * @see ObjectReference
 */
public final class HolderObjectReference<T> implements ObjectReference<T> {

    private final ResourceKey<T> key;
    private final Holder<T> holder;

    /**
     * Default constructor.
     *
     * @param key the registry key of the object
     * @param holder a holder reference to the registry object
     */
    public HolderObjectReference(final ResourceKey<T> key, final Holder<T> holder) {
        this.key = key;
        this.holder = holder;
    }

    @Override
    public ResourceKey<T> key() {
        return this.key;
    }

    @Override
    public T get() {
        return this.holder.value();
    }
}
