package com.example.examplemod.mixin;

import com.example.examplemod.Constants;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class CommonMixin {
	@Inject(method = "init", at = @At("TAIL"))
	public void exampleMod$onInit(CallbackInfo ci) {
		Constants.LOGGER.info("This line is printed by a common mixin!");
	}
}
