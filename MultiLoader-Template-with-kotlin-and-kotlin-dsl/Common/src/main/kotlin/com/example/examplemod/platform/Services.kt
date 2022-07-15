package com.example.examplemod.platform

import com.example.examplemod.Constants
import com.example.examplemod.platform.services.IPlatformHelper
import java.util.*


class Services {
    companion object {
        val PLATFORM: IPlatformHelper = load(IPlatformHelper::class.java)

        private fun <T> load(clazz: Class<T>): T {
            val loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow { NullPointerException("Failed to load service for " + clazz.name) }
            Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz)
            return loadedService
        }
    }
}