package com.example.examplemod

import com.example.examplemod.platform.Services
import net.minecraft.core.Registry
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.TooltipFlag

class CommonClass {
    companion object {
        // This method serves as an initialization hook for the mod. The vanilla
        // game has no mechanism to load tooltip listeners so this must be
        // invoked from a mod loader specific project like Forge or Fabric.
        fun init() {
            Constants.LOG.info(
                "Hello from Common init on {}! we are currently in a {} environment!",
                Services.PLATFORM.getPlatformName(),
                if (Services.PLATFORM.isDevelopmentEnvironment()) "development" else "production"
            )
            Constants.LOG.info("Diamond Item >> {}", Registry.ITEM.getKey(Items.DIAMOND))
        }

        // This method serves as a hook to modify item tooltips. The vanilla game
        // has no mechanism to load tooltip listeners so this must be registered
        // by a mod loader like Forge or Fabric.
        fun onItemTooltip(stack: ItemStack, context: TooltipFlag?, tooltip: MutableList<Component?>) {
            if (!stack.isEmpty) {
                val food = stack.item.foodProperties
                if (food != null) {
                    tooltip.add(TextComponent("Nutrition: " + food.nutrition))
                    tooltip.add(TextComponent("Saturation: " + food.saturationModifier))
                }
            }
        }
    }
}