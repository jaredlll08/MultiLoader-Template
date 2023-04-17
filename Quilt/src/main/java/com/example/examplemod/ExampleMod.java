package com.example.examplemod;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class ExampleMod implements ModInitializer {

	@Override
	public void onInitialize(ModContainer mod) {
		Constants.LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		CommonInit.init();
	}
}
