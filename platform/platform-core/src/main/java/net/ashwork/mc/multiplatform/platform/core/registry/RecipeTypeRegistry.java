/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.core.registry;

import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

/**
 * A helper registry for creating {@link RecipeType}s.
 */
public final class RecipeTypeRegistry {

    /**
     * Constructor to prevent initialization.
     */
    private RecipeTypeRegistry() {
        throw new AssertionError("Do not initialize " + this.getClass().getSimpleName());
    }

    /**
     * A registrar for for {@link RecipeType}s.
     */
    public static final RegistryPlatformManager.WritableRegistry<RecipeType<?>> REGISTRAR = RegistryPlatformManager.get().registrar(Registry.RECIPE_TYPE_REGISTRY);

    /**
     * Registers a {@link RecipeType} and returns a reference to it. The object
     * will be registered under the current mod identifier.
     *
     * @param name the name of the object
     * @param <T> the type of the recipe
     */
    public static <T extends Recipe<?>> ObjectReference<RecipeType<T>> register(final String name) {
        return register(new ResourceLocation(ModLoaderPlatform.get().modId(), name));
    }

    /**
     * Registers a {@link RecipeType} and returns a reference to it.
     *
     * @param key the registry key of the object
     * @param <T> the type of the recipe
     */
    public static <T extends Recipe<?>> ObjectReference<RecipeType<T>> register(final ResourceKey<T> key) {
        return register(key.location());
    }

    /**
     * Registers a {@link RecipeType} and returns a reference to it.
     *
     * @param name the registry name of the object
     * @param <T> the type of the recipe
     */
    public static <T extends Recipe<?>> ObjectReference<RecipeType<T>> register(final ResourceLocation name) {
        return REGISTRAR.register(name, recipeType(name));
    }

    /**
     * Creates a factory for a {@link RecipeType}.
     *
     * @param name the registry name of the object
     * @return a {@link RecipeType} factory
     * @param <T> the type of the recipe
     */
    public static <T extends Recipe<?>> Supplier<RecipeType<T>> recipeType(final ResourceLocation name) {
        return () -> new RecipeType<>() {
            @Override
            public String toString() {
                return name.toString();
            }
        };
    }
}
