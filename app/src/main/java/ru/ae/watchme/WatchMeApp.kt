package ru.ae.watchme

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.ae.watchme.di.appModule
import ru.ae.watchme.di.databaseModule
import ru.ae.watchme.di.networkModule

class WatchMeApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WatchMeApp)

            /* NOTE: Тут будут добавляться модули.
            Не помню как мы реализовывали это на паре,
            может быть со временем перемещу */

            // Source - https://medium.com/@humzakhalid94/koin-a-practical-guide-to-dependency-injection-in-android-143cdab1756b

            modules(appModule, databaseModule, networkModule)
        }
    }
}