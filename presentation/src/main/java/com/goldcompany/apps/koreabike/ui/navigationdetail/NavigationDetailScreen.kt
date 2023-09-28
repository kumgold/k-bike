package com.goldcompany.apps.koreabike.ui.navigationdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.goldcompany.apps.koreabike.compose.ui.CircularLoadingView
import com.goldcompany.apps.koreabike.util.UIState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline

@Composable
fun NavigationDetailScreen(
    viewModel: NavigationDetailViewModel = hiltViewModel(),
    navController: NavController,
    modifier: Modifier,
) {
    val uiState = viewModel.uiState.collectAsState()

    when (uiState.value.uiState) {
        UIState.LOADING -> {
            CircularLoadingView(modifier)
        }
        UIState.DONE -> {
            NavigationPathView(
                modifier = modifier,
                path = uiState.value.path
            )
        }
        else -> {

        }
    }
}

@Composable
private fun NavigationPathView(
    modifier: Modifier,
    path: List<LatLng>
) {
    GoogleMap(
        modifier = modifier,
        cameraPositionState = CameraPositionState(
            position = CameraPosition.fromLatLngZoom(path.first(), 16f)
        )
    ) {
        Marker(
            state = MarkerState(path.first()),
            title = "From"
        )
        Polyline(
            points = path,
            color = Color.Green
        )
        Marker(
            state = MarkerState(path.last()),
            title = "To"
        )
    }
}