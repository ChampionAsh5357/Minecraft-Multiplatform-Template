package net.ashwork.mc.multiplatform.platform.core.manager;

import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;
import net.ashwork.mc.multiplatform.platform.core.util.ObjectReference;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

//TODO: Document
public interface RegistryPlatformManager {

    static RegistryPlatformManager get() {
        return ModLoaderPlatform.get().registries();
    }

    <T> WritableRegistry<T> register(final ResourceKey<? extends Registry<T>> registryKey);

    interface WritableRegistry<T> {

        default <I extends T> ObjectReference<I> register(final String name, final Supplier<? extends I> object) {
            return this.register(new ResourceLocation(ModLoaderPlatform.get().modId(), name), object);
        }

        default <I extends T> ObjectReference<I> register(final ResourceKey<T> key, final Supplier<? extends I> object) {
            return this.register(key.location(), object);
        }

        <I extends T> ObjectReference<I> register(final ResourceLocation name, final Supplier<? extends I> object);
    }
}
