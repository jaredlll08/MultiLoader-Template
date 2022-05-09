package com.example.examplemod;

import com.example.examplemod.registration.RegistrationProvider;
import com.example.examplemod.registration.RegistryObject;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class FabricRegistrationFactory implements RegistrationProvider.Factory {

    @Override
    public <T> RegistrationProvider<T> create(ResourceKey<? extends Registry<T>> resourceKey, String modId) {
        return new Provider<>(modId, resourceKey);
    }

    @Override
    public <T> RegistrationProvider<T> create(Registry<T> registry, String modId) {
        return new Provider<>(modId, registry);
    }

    private static class Provider<T> implements RegistrationProvider<T> {
        private final String modId;
        private final Registry<T> registry;

        private final Set<RegistryObject<T>> entries = new HashSet<>();
        private final Set<RegistryObject<T>> entriesView = Collections.unmodifiableSet(entries);

        @SuppressWarnings({"unchecked"})
        private Provider(String modId, ResourceKey<? extends Registry<T>> key) {
            this.modId = modId;

            final var reg = Registry.REGISTRY.get(key.location());
            if (reg == null) {
                throw new RuntimeException("Registry with name " + key.location() + " was not found!");
            }
            registry = (Registry<T>) reg;
        }

        private Provider(String modId, Registry<T> registry) {
            this.modId = modId;
            this.registry = registry;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier) {
            final var rl = new ResourceLocation(modId, name);
            final var obj = Registry.register(registry, rl, supplier.get());
            final var ro = new RegistryObject<I>() {
                final ResourceKey<I> key = ResourceKey.create((ResourceKey<? extends Registry<I>>) registry.key(), rl);

                @Override
                public ResourceKey<I> getResourceKey() {
                    return key;
                }

                @Override
                public ResourceLocation getId() {
                    return rl;
                }

                @Override
                public I get() {
                    return obj;
                }

                @Override
                public Holder<I> asHolder() {
                    return (Holder<I>) registry.getOrCreateHolder((ResourceKey<T>) this.key);
                }
            };
            entries.add((RegistryObject<T>) ro);
            return ro;
        }

        @Override
        public Collection<RegistryObject<T>> getEntries() {
            return entriesView;
        }

        @Override
        public String getModId() {
            return modId;
        }
    }
}