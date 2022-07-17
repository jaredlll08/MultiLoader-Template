package com.example.examplemod

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

class ExampleMod : ModInitializer {
    override fun onInitialize() {
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!")
        CommonClass.init()

        // Some code like events require special initialization from the
        // loader specific code.
        ItemTooltipCallback.EVENT.register(ItemTooltipCallback { stack: ItemStack, context: TooltipFlag?, tooltip: MutableList<Component?> ->
            CommonClass.onItemTooltip(
                stack,
                context,
                tooltip
            )
        })
    }
}