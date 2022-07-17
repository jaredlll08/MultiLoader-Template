package com.example.examplemod.platform

import com.example.examplemod.platform.services.IPlatformHelper
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.loading.FMLLoader

class ForgePlatformHelper : IPlatformHelper {
    override fun getPlatformName(): String {
        return "Forge"
    }

    override fun isModLoaded(modId: String?): Boolean {
        return ModList.get().isLoaded(modId)
    }

    override fun isDevelopmentEnvironment(): Boolean {
        return !FMLLoader.isProduction()
    }
}