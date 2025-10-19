package pl.senordeveloper.kmpmerchant

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.senordeveloper.kmpmerchant.di.initKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin() {
            androidContext(this@MainApplication)
        }
    }
}