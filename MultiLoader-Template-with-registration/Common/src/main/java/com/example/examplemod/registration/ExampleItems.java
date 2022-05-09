package com.example.examplemod.registration;

import com.example.examplemod.Constants;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

/**
 * Example class for item registration
 */
public class ExampleItems {

    /**
     * The provider for items
     */
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registry.ITEM_REGISTRY, Constants.MOD_ID);

    public static final RegistryObject<Item> EXAMPLE = ITEMS.register("example", () -> new Item(new Item.Properties().fireResistant().stacksTo(12)));

    // Called in the mod initializer / constructor in order to make sure that items are registered
    public static void loadClass() {}
}
