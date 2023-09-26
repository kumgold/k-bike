package com.goldcompany.apps.koreabike.nav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.goldcompany.apps.koreabike.ui.bikemap.BikeMapScreen
import com.goldcompany.apps.koreabike.ui.historyplace.HistoryPlaceScreen
import com.goldcompany.apps.koreabike.ui.navigation.NavigationScreen
import com.goldcompany.apps.koreabike.ui.navigationdetail.NavigationDetailScreen
import com.goldcompany.apps.koreabike.ui.record.RecordScreen
import com.goldcompany.apps.koreabike.ui.searchaddress.SearchAddressScreen

@Composable
fun KBikeNavHost(
    modifier: Modifier,
    navController: NavHostController
) {
    val navModifier = Modifier.fillMaxSize()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = KBikeScreen.BikeMap.route
    ) {
        composable(KBikeScreen.BikeMap.route) {
            BikeMapScreen(
                modifier = navModifier,
                navController = navController
            )
        }
        composable(KBikeScreen.SearchPlace.route) {
            SearchAddressScreen(
                modifier = navModifier,
                navController = navController
            )
        }
        composable(KBikeScreen.MyPlace.route) {
            HistoryPlaceScreen(
                modifier = navModifier,
                navController = navController
            )
        }
        composable(KBikeScreen.Record.route) {
            RecordScreen(
                modifier = navModifier,
                navController = navController
            )
        }
        composable(KBikeScreen.Navigation.route) {
            NavigationScreen(
                navController = navController,
                modifier = navModifier
            )
        }
        composable(
            route = "${KBikeScreen.NavigationDetail.route}/{startCoordinate},{endCoordinate}",
            arguments = listOf(
                navArgument("startCoordinate") {
                    type = NavType.StringType
                },
                navArgument("endCoordinate") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val startCoordinate = entry.arguments?.getString("startCoordinate")
            val endCoordinate = entry.arguments?.getString("endCoordinate")

            NavigationDetailScreen(
                navController = navController,
                modifier = navModifier,
                startCoordinate = startCoordinate,
                endCoordinate = endCoordinate
            )
        }
    }
}