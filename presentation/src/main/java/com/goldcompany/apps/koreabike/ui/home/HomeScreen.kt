package com.goldcompany.apps.koreabike.ui.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.nav.KBikeNavHost
import com.goldcompany.apps.koreabike.nav.KBikeScreen

@Composable
fun HomeScreen(widthSizeClass: WindowWidthSizeClass) {
    val navController = rememberNavController()
    val items = listOf(
        KBikeScreen.BikeMap,
        KBikeScreen.MyPlace,
        KBikeScreen.Record
    )
    val map = mapOf(
        KBikeScreen.BikeMap.route to R.drawable.ic_bike_map,
        KBikeScreen.MyPlace.route to R.drawable.ic_my_place,
        KBikeScreen.Record.route to R.drawable.ic_edit_square
    )

    when (widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            CompactScreen(
                navController = navController,
                items = items,
                map = map
            )
        }
        else -> {
            ExpandedScreen(
                navController = navController,
                items = items,
                map = map
            )
        }
    }
}

@Composable
private fun CompactScreen(
    navController: NavHostController,
    items: List<KBikeScreen>,
    map: Map<String, Int>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar = rememberSaveable { mutableStateOf(true) }

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
        KBikeNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}

@Composable
private fun ExpandedScreen(
    navController: NavHostController,
    items: List<KBikeScreen>,
    map: Map<String, Int>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Row(modifier = Modifier.fillMaxSize()) {
        NavigationRail {
            val currentDestination = navBackStackEntry?.destination
            items.forEach { screen ->
                NavigationRailItem(
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
        KBikeNavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController
        )
    }
}