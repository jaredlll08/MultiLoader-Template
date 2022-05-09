package com.example.examplemod.registration;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

/**
 * Represents a lazy wrapper for registry object.
 *
 * @param <T> the type of the object
 */
public interface RegistryObject<T> extends Supplier<T> {

    /**
     * Gets the {@link ResourceKey} of the registry of the object wrapped.
     *
     * @return the {@link ResourceKey} of the registry
     */
    ResourceKey<T> getResourceKey();

    /**
     * Gets the id of the object.
     *
     * @return the id of the object
     */
    ResourceLocation getId();

    /**
     * Gets the object behind this wrapper. Calling this method too early
     * might result in crashes.
     *
     * @return the object behind this wrapper
     */
    @Override
    T get();

    /**
     * Gets this object wrapped in a vanilla {@link Holder}.
     *
     * @return the holder
     */
    Holder<T> asHolder();
}
