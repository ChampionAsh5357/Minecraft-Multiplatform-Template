package net.ashwork.mc.multiplatform.platform.forge.util;

import net.ashwork.mc.multiplatform.platform.core.util.ObjectReference;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.RegistryObject;

//TODO: Document
public final class RegistryObjectDelegate<T> implements ObjectReference<T> {

    private final RegistryObject<T> object;

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
