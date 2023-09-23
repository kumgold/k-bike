package com.goldcompany.apps.koreabike.ui.navigationdetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.maps.android.compose.GoogleMap

@Composable
fun NavigationDetailScreen(
    navController: NavController,
    modifier: Modifier
) {
    GoogleMap(
        modifier = modifier
    ) {

    }
}