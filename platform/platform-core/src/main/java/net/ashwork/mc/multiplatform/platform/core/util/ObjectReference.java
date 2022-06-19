package net.ashwork.mc.multiplatform.platform.core.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

//TODO: Document
public interface ObjectReference<T> extends Supplier<T> {

    ResourceKey<T> key();

    default ResourceLocation id() {
        return this.key().location();
    }
}
