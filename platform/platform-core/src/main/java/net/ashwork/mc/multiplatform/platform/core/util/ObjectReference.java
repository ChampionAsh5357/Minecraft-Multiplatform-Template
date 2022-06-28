/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.core.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

/**
 * An object used to hold a weak reference to a given registry object based on its
 * identifier.
 *
 * @param <T> the type of the registry object
 */
public interface ObjectReference<T> extends Supplier<T> {

    /**
     * Returns the registry key of the object.
     *
     * @return the registry key of the object
     */
    ResourceKey<T> key();

    /**
     * Returns the registry name of the object.
     *
     * @return the registry name of the object
     */
    default ResourceLocation id() {
        return this.key().location();
    }
}
