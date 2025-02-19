package com.goldcompany.apps.koreabike.ui.bikemap

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.util.CircularLoadingView
import com.goldcompany.apps.koreabike.nav.KBikeScreen
import com.goldcompany.apps.koreabike.compose.KBikeTypography
import com.goldcompany.koreabike.data.model.address.Address
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BikeMapScreen(
    modifier: Modifier = Modifier,
    viewModel: BikeMapViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavController
) {
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
    ) { paddingValues ->

        val uiState by viewModel.uiState.collectAsState()
        val bottomSheetUiState by viewModel.bottomSheetUiState.collectAsState()

        BikeMapDefaultScreen(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            address = uiState.address,
            bottomSheetUiState = bottomSheetUiState,
            coroutineScope = coroutineScope,
            searchPlace = viewModel::searchPlace,
            navController = navController
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BikeMapDefaultScreen(
    modifier: Modifier,
    address: Address?,
    bottomSheetUiState: BikeMapBottomSheetUiState,
    coroutineScope: CoroutineScope,
    searchPlace: (String) -> Unit,
    navController: NavController
) {
    val bottomState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    BikeMap(
        modifier = modifier,
        address = address,
        searchPlace = searchPlace,
        coroutineScope = coroutineScope,
        bottomState = bottomState
    )
    SearchAddressBar(navController = navController)
    BottomSheetLayout(
        bottomSheetUiState = bottomSheetUiState,
        bottomState = bottomState
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BikeMap(
    modifier: Modifier,
    address: Address?,
    searchPlace: (String) -> Unit,
    coroutineScope: CoroutineScope,
    bottomState: ModalBottomSheetState
) {
    val latitude = address?.y?.toDouble() ?: 37.5643
    val longitude = address?.x?.toDouble() ?: 126.9801
    val initialPosition = LatLng(latitude, longitude)

    GoogleMap(
        modifier = modifier,
        cameraPositionState = CameraPositionState(
            position = CameraPosition.fromLatLngZoom(initialPosition, 16f)
        ),
        onPOIClick = { marker ->
            searchPlace(marker.name)
            coroutineScope.launch {
                bottomState.show()
            }
        }
    ) {
        Marker(
            state = MarkerState(position = initialPosition),
            title = stringResource(id = R.string.current_location)
        )
    }
}

@Composable
private fun SearchAddressBar(
    navController: NavController
) {
    Row {
        val shape = Shapes(medium = RoundedCornerShape(dimensionResource(id = R.dimen.default_corner_radius))).medium
        val border = BorderStroke(1.dp, colorResource(id = R.color.black))
        val defaultMargin = dimensionResource(id = R.dimen.default_margin)

        Button(
            modifier = Modifier
                .semantics {
                    testTag = "주소를 검색하세요."
                }
                .padding(horizontal = defaultMargin)
                .weight(3f),
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.white),
                contentColor = colorResource(id = R.color.black)
            ),
            border = border,
            onClick = {
                navController.navigate(KBikeScreen.SearchPlace.route)
            },
            content = {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.init_page),
                    textAlign = TextAlign.Start,
                    style = KBikeTypography.button
                )
            }
        )

        Button(
            modifier = Modifier
                .padding(end = defaultMargin)
                .weight(1f),
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.colorPrimary),
                contentColor = colorResource(id = R.color.white)
            ),
            border = border,
            onClick = {
                navController.navigate(KBikeScreen.Navigation.route)
            },
            content = {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.navigation_button),
                    textAlign = TextAlign.Center,
                    style = KBikeTypography.button
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomSheetLayout(
    bottomSheetUiState: BikeMapBottomSheetUiState,
    bottomState: ModalBottomSheetState
) {
    ModalBottomSheetLayout(
        sheetState = bottomState,
        sheetContent = {
            when (bottomSheetUiState.isLoading) {
                true -> {
                    CircularLoadingView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize()
                    )
                }
                false -> {
                    PlaceWebView(bottomSheetUiState = bottomSheetUiState)
                }
            }
        }
    ) {
        // TODO Anchor
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun PlaceWebView(
    bottomSheetUiState: BikeMapBottomSheetUiState
) {
    var backEnabled by remember { mutableStateOf(false) }
    var view: WebView? = null

    BackHandler(enabled = backEnabled) {
        view?.goBack()
    }

    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        factory = { context ->
            WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                        backEnabled = view.canGoBack()
                    }
                }
                settings.javaScriptEnabled = true
                view = this
            }
        },
        update = { webView ->
            view = webView
            (bottomSheetUiState.currentPlace?.placeUrl
                ?: bottomSheetUiState.searchUrl)?.let {
                webView.loadUrl(
                    it
                )
            }
        }
    )
}
