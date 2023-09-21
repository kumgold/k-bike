package com.goldcompany.apps.koreabike

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}

@HiltAndroidApp
class KBikeApplication  : BaseApplication()