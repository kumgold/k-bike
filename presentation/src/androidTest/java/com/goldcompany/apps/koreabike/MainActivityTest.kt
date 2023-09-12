package com.goldcompany.apps.koreabike

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.goldcompany.apps.koreabike.compose.KBikeComposeTheme
import com.goldcompany.apps.koreabike.nav.KBikeScreen
import com.goldcompany.apps.koreabike.ui.bike_map.BikeMapScreen
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule

@HiltAndroidTest
class MainActivityTest {

    companion object {
        const val SEARCH_ADDRESS_TAG = "주소를 검색하세요."
    }

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeRule.activity.apply {
            composeRule.setContent {
                val navController = rememberNavController()
                KBikeComposeTheme {
                    NavHost(
                        navController = navController,
                        startDestination = KBikeScreen.BikeMap.route
                    ) {
                        composable(KBikeScreen.BikeMap.route) {
                            BikeMapScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}