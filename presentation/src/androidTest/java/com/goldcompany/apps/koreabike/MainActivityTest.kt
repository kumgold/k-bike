package com.goldcompany.apps.koreabike

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.goldcompany.apps.koreabike.compose.KBikeComposeTheme
import com.goldcompany.apps.koreabike.nav.KBikeScreen
import com.goldcompany.apps.koreabike.ui.bike_map.BikeMapScreen
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeRule.activity.setContent {
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

    @Test
    fun clickSearchAddress_isVisible() {
        val label = composeRule.activity.getString(R.string.search_address_hint2)
        composeRule.onNodeWithTag(label).assertIsDisplayed()
    }
}