package com.example.examplemod.mixin;

import com.example.examplemod.Constants;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.stream.Collectors;

@Mixin(TitleScreen.class)
public class ExampleForgeMixin {
    
    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {
        
        Constants.LOG.info("This line is printed by an example mod mixin from Forge!");
        Constants.LOG.info(ModList.get()
                .getMods()
                .stream()
                .map(IModInfo::getModId)
                .collect(Collectors.joining(", ", "Current loaded mods: [", "]")));
    }
    
}