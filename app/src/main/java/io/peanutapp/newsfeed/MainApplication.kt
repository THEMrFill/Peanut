package io.peanutapp.newsfeed

import android.app.Application
import io.peanutapp.newsfeed.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin

class MainApplication: Application() , KoinComponent {
  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidContext(this@MainApplication)
      modules(appModules)
    }
  }
}
