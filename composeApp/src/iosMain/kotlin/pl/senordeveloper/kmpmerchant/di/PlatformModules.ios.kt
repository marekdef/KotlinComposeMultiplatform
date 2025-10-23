package pl.senordeveloper.kmpmerchant.di

import org.koin.core.module.Module
import pl.senordeveloper.kmpmerchant.iosModule

actual fun platformModules(): List<Module> = listOf(iosModule)