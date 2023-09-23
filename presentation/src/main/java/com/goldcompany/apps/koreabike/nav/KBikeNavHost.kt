package com.goldcompany.apps.koreabike.nav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        composable(KBikeScreen.NavigationDetail.route) {
            NavigationDetailScreen(
                navController = navController,
                modifier = navModifier
            )
        }
    }
}