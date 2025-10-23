package pl.senordeveloper.kmpmerchant.di

import org.koin.core.module.Module
import pl.senordeveloper.kmpmerchant.androidModule

actual fun platformModules(): List<Module> = listOf(androidModule)