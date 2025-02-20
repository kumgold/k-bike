package com.goldcompany.apps.koreabike

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.goldcompany.apps.koreabike.compose.KBikeComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        setContent {
            KBikeComposeTheme {
                KBikeNavigation()
            }
        }
    }
}