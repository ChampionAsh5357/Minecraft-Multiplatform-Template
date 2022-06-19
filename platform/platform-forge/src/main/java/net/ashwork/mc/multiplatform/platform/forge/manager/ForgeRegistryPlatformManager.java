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

//TODO: Document
public final class ForgeRegistryPlatformManager implements RegistryPlatformManager {

    private final Map<ResourceKey<?>, ForgeWritableRegistry<?>> registers;

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

    private void registerObjects(final RegisterEvent event) {
        final ForgeWritableRegistry<?> register = this.registers.get(event.getRegistryKey());
        if (register != null) this.registerObjectsInternal(event, register);
    }

    @ApiStatus.Internal
    private <T> void registerObjectsInternal(final RegisterEvent event, final ForgeWritableRegistry<T> register) {
        event.register(register.registryKey, register);
    }

    public static final class ForgeWritableRegistry<T> implements WritableRegistry<T>, Consumer<RegisterEvent.RegisterHelper<T>> {

        private final ResourceKey<? extends Registry<T>> registryKey;
        private List<Consumer<RegisterEvent.RegisterHelper<T>>> objects;

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
