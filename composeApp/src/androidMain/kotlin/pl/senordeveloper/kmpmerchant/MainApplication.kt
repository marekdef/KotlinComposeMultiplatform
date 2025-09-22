package pl.senordeveloper.kmpmerchant

import android.app.Application
import org.koin.core.context.startKoin
import pl.senordeveloper.kmpmerchant.di.initKoin
import pl.senordeveloper.kmpmerchant.di.koinModules

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }
}