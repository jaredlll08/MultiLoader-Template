package com.example.examplemod.mixin

import com.example.examplemod.Constants
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.TitleScreen
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(TitleScreen::class)
class ExampleMixin {
    @Inject(at = [At("HEAD")], method = ["init()V"])
    private fun init(info: CallbackInfo) {
        Constants.LOG.info("This line is printed by an example mod mixin from Fabric!")
        Constants.LOG.info("MC Version: {}", Minecraft.getInstance().versionType)
        Constants.LOG.info("Classloader: {}", this.javaClass.classLoader)
    }
}