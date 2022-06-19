package net.ashwork.mc.multiplatform.platform.core.util;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;

//TODO: Document
public final class HolderObjectReference<T> implements ObjectReference<T> {

    private final ResourceKey<T> key;
    private final Holder<T> holder;

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
