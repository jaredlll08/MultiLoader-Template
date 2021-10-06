package com.example.examplemod;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Items;

public class CommonClass {
    
    public static void init() {
        
        Constants.LOG.info("Hello from Common init!");
        Constants.LOG.info("Diamond Item >> {}", Registry.ITEM.getKey(Items.DIAMOND));
    }
}