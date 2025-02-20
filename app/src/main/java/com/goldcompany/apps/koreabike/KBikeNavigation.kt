package com.goldcompany.apps.koreabike

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.goldcompany.apps.koreabike.ui.bikemap.BikeMapScreen
import com.goldcompany.apps.koreabike.ui.historyplace.HistoryPlaceScreen
import com.goldcompany.apps.koreabike.ui.navigation.NavigationScreen
import com.goldcompany.apps.koreabike.ui.navigationdetail.NavigationDetailScreen
import com.goldcompany.apps.koreabike.ui.searchplace.SearchPlaceScreen

object KBikeDestinations {
    const val BIKE_MAP_SCREEN = "bike_map"
    const val SEARCH_PLACE_SCREEN = "search_place"
    const val MY_PLACE_SCREEN = "my_place"
    const val RECORD_SCREEN = "record"
    const val NAVIGATION = "navigation"
    const val NAVIGATION_DETAIL = "navigation_detail"
}

sealed class KBikeScreen(val route: String, @StringRes val resourceId: Int) {
    data object BikeMap: KBikeScreen(KBikeDestinations.BIKE_MAP_SCREEN, R.string.bike_map)
    data object SearchPlace: KBikeScreen(KBikeDestinations.SEARCH_PLACE_SCREEN, R.string.search_place)
    data object MyPlace: KBikeScreen(KBikeDestinations.MY_PLACE_SCREEN, R.string.my_place)
    data object Record: KBikeScreen(KBikeDestinations.RECORD_SCREEN, R.string.record)
    data object Navigation: KBikeScreen(KBikeDestinations.NAVIGATION, R.string.navigation)
    data object NavigationDetail: KBikeScreen(KBikeDestinations.NAVIGATION_DETAIL, R.string.navigation_detail)
}

@Composable
fun KBikeNavigation() {
    NavigationGraph()
}

@Composable
private fun NavigationGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar = rememberSaveable { mutableStateOf(true) }

    val tabs = listOf(
        KBikeScreen.BikeMap,
        KBikeScreen.MyPlace
    )
    val icons = mapOf(
        KBikeScreen.BikeMap.route to R.drawable.ic_bike_map,
        KBikeScreen.MyPlace.route to R.drawable.ic_my_place,
        KBikeScreen.Record.route to R.drawable.ic_edit_square
    )

    showBottomBar.value = when (navBackStackEntry?.destination?.route) {
        KBikeScreen.BikeMap.route -> true
        KBikeScreen.MyPlace.route -> true
        KBikeScreen.Record.route -> true
        else -> false
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar.value) {
                BottomNavigation(
                    backgroundColor = colorResource(id = R.color.white),
                    contentColor = colorResource(id = R.color.colorPrimary)
                ) {
                    val currentDestination = navBackStackEntry?.destination
                    tabs.forEach { screen ->
                        BottomNavigationItem(
                            icon = {
                                icons[screen.route]?.let { painterResource(id = it) }?.let {
                                    Icon(
                                        painter = it,
                                        contentDescription = stringResource(id = screen.resourceId)
                                    )
                                }
                            },
                            label = { Text(stringResource(id = screen.resourceId)) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        KBikeNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}

@Composable
fun KBikeNavHost(
    modifier: Modifier,
    navController: NavHostController,
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
            SearchPlaceScreen(
                navController = navController
            )
        }
        composable(KBikeScreen.MyPlace.route) {
            HistoryPlaceScreen(
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
            route = "${KBikeScreen.NavigationDetail.route}?startCoordinate={startCoordinate},endCoordinate={endCoordinate}",
            arguments = listOf(
                navArgument("startCoordinate") {
                    type = NavType.StringType
                },
                navArgument("endCoordinate") {
                    type = NavType.StringType
                }
            )
        ) {
            NavigationDetailScreen(
                navController = navController,
                modifier = navModifier
            )
        }
    }
}