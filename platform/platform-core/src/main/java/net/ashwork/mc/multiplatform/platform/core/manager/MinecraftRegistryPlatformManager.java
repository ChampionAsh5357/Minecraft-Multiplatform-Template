package net.ashwork.mc.multiplatform.platform.core.manager;

import com.mojang.serialization.Lifecycle;
import net.ashwork.mc.multiplatform.platform.core.util.HolderObjectReference;
import net.ashwork.mc.multiplatform.platform.core.util.ObjectReference;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

//TODO: Document
public final class MinecraftRegistryPlatformManager implements RegistryPlatformManager {

    private final Map<ResourceKey<?>, WritableRegistry<?>> registers;

    public MinecraftRegistryPlatformManager() {
        this.registers = new HashMap<>();
    }

    @Override
    public <T> WritableRegistry<T> register(final ResourceKey<? extends Registry<T>> registryKey) {
        @SuppressWarnings("unchecked")
        final WritableRegistry<T> register = (WritableRegistry<T>) this.registers.computeIfAbsent(registryKey, key -> {
            @SuppressWarnings("unchecked")
            final WritableRegistry<T> r = new MinecraftWritableRegistry<>((ResourceKey<? extends Registry<T>>) key);
            return r;
        });
        return register;
    }

    public static final class MinecraftWritableRegistry<T> implements WritableRegistry<T> {

        private final net.minecraft.core.WritableRegistry<T> registry;

        public MinecraftWritableRegistry(final ResourceKey<? extends Registry<T>> registryKey) {
            @SuppressWarnings("unchecked")
            var registry = (Optional<net.minecraft.core.WritableRegistry<T>>) Registry.REGISTRY.getOptional(registryKey.location());
            this.registry = registry.or(
                    () -> {
                        @SuppressWarnings("unchecked")
                        var builtIn = (Optional<net.minecraft.core.WritableRegistry<T>>) BuiltinRegistries.REGISTRY.getOptional(registryKey.location());
                        return builtIn;
                    }
            ).orElseThrow();
        }

        @Override
        public <I extends T> ObjectReference<I> register(final ResourceLocation name, final Supplier<? extends I> object) {
            @SuppressWarnings("unchecked")
            ResourceKey<I> key = (ResourceKey<I>) ResourceKey.create(this.registry.key(), name);
            @SuppressWarnings("unchecked")
            Holder<I> holder = (Holder<I>) this.registry.register((ResourceKey<T>) key, object.get(), Lifecycle.stable());
            return new HolderObjectReference<>(key, holder);
        }
    }
}
