package com.example.examplemod;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Items;

public class CommonClass {
    
    public static void init() {
        
        System.out.println("Hello from Common init!");
        System.out.println("Diamond Item >> " + Registry.ITEM.getKey(Items.DIAMOND));
    }
    
}
