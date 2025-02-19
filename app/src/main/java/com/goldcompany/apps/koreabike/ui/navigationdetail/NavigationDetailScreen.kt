package com.goldcompany.apps.koreabike.ui.navigationdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.util.CircularLoadingView
import com.goldcompany.apps.koreabike.util.DefaultKBikeTopAppBar
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

    Column(
        modifier = modifier
    ) {
        DefaultKBikeTopAppBar(
            title = R.string.navigation,
            navigateBack = {
                navController.popBackStack()
            }
        )
        MapView(
            modifier = modifier,
            uiState = uiState.value.uiState,
            path = uiState.value.path,
            duration = uiState.value.duration,
            distance = uiState.value.distance
        )
    }


}

@Composable
private fun MapView(
    modifier: Modifier,
    uiState: UIState,
    path: List<LatLng>,
    duration: Int,
    distance: Int
) {
    when (uiState) {
        UIState.LOADING -> {
            CircularLoadingView(modifier)
        }
        UIState.DONE -> {
            NavigationPathView(
                modifier = modifier,
                path = path,
                duration = duration,
                distance = distance
            )
        }
        else -> {

        }
    }
}

@Composable
private fun NavigationPathView(
    modifier: Modifier,
    path: List<LatLng>,
    duration: Int,
    distance: Int
) {
    GoogleMap(
        modifier = modifier,
        cameraPositionState = CameraPositionState(
            position = CameraPosition.fromLatLngZoom(path.first(), 13f)
        )
    ) {
        Marker(
            state = MarkerState(path.first()),
            title = stringResource(id = R.string.from)
        )
        Polyline(
            points = path,
            color = colorResource(id = R.color.green)
        )
        Marker(
            state = MarkerState(path.last()),
            title = stringResource(id = R.string.to)
        )
    }
}