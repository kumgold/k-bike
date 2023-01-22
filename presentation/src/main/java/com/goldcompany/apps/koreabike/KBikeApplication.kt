package com.goldcompany.apps.koreabike

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KBikeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}