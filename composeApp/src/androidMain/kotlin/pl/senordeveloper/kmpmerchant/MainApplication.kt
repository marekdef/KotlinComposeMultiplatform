package pl.senordeveloper.kmpmerchant

import android.app.Application
import pl.senordeveloper.kmpmerchant.di.initKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }
}