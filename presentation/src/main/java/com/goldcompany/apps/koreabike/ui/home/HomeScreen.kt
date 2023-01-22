package com.goldcompany.apps.koreabike.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.goldcompany.apps.koreabike.KBikeScreen
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.ui.bike_map.BikeMapScreen
import com.goldcompany.apps.koreabike.ui.history_place.HistoryPlaceScreen
import com.goldcompany.apps.koreabike.ui.search_address.SearchAddressScreen

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar = rememberSaveable { mutableStateOf(true) }
    val items = listOf(
        KBikeScreen.BikeMap,
        KBikeScreen.MyPlace
    )
    val map = mapOf(
        KBikeScreen.BikeMap.route to R.drawable.ic_bike_map,
        KBikeScreen.MyPlace.route to R.drawable.ic_my_place
    )

    showBottomBar.value = when (navBackStackEntry?.destination?.route) {
        KBikeScreen.BikeMap.route -> true
        KBikeScreen.MyPlace.route -> true
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
                    items.forEach { screen ->
                        BottomNavigationItem(
                            icon = {
                                map[screen.route]?.let { painterResource(id = it) }?.let {
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
        NavHost(
            navController = navController,
            startDestination = KBikeScreen.BikeMap.route,
            Modifier.padding(innerPadding)
        ) {
            composable(KBikeScreen.BikeMap.route) { BikeMapScreen(navController = navController)}
            composable(KBikeScreen.SearchPlace.route) { SearchAddressScreen(navController = navController)}
            composable(KBikeScreen.MyPlace.route) { HistoryPlaceScreen(navController = navController)}
        }
    }
}