package com.goldcompany.apps.koreabike.ui.navigationdetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.maps.android.compose.GoogleMap

@Composable
fun NavigationDetailScreen(
    viewModel: NavigationDetailViewModel = hiltViewModel(),
    navController: NavController,
    modifier: Modifier,
    startCoordinate: String?,
    endCoordinate: String?
) {
    viewModel.getNavigationPath(startCoordinate!!, endCoordinate!!)
    GoogleMap(
        modifier = modifier
    ) {

    }
}