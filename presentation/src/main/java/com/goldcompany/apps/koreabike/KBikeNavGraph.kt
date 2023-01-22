package com.goldcompany.apps.koreabike

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.goldcompany.apps.koreabike.ui.bike_map.BikeMapScreen
import com.goldcompany.apps.koreabike.ui.history_place.HistoryPlaceScreen
import com.goldcompany.apps.koreabike.ui.home.HomeScreen
import com.goldcompany.apps.koreabike.ui.search_address.SearchAddressScreen

@Composable
fun KBikeNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = KBikeDestinations.HOME
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(KBikeDestinations.HOME) {
            HomeScreen()
        }
        composable(KBikeDestinations.BIKE_MAP_SCREEN) {
            BikeMapScreen(navController = navController)
        }
        composable(KBikeDestinations.SEARCH_PLACE_SCREEN) {
            SearchAddressScreen(navController = navController)
        }
        composable(KBikeDestinations.MY_PLACE_SCREEN) {
            HistoryPlaceScreen(navController = navController)
        }
    }
}