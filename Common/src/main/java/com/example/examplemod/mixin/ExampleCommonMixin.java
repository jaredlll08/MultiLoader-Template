package com.example.examplemod.mixin;

import com.example.examplemod.Constants;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class ExampleCommonMixin {
    
    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {
        
        Constants.LOG.info("This line is printed by an example mod mixin from Common!");
        Constants.LOG.info("MC Version: {}", SharedConstants.getCurrentVersion().getId());
        Constants.LOG.info("Classloader: {}", this.getClass().getClassLoader());
    }
}