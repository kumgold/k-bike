package com.goldcompany.apps.koreabike.ui.navigation

import android.view.KeyEvent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.goldcompany.apps.koreabike.KBikeScreen
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.compose.KBikeTypography
import com.goldcompany.apps.koreabike.compose.LightGray
import com.goldcompany.apps.koreabike.util.CircularLoadingView
import com.goldcompany.apps.koreabike.util.DefaultKBikeTopAppBar
import com.goldcompany.apps.koreabike.util.DefaultTextView
import com.goldcompany.apps.koreabike.util.ErrorMessageTextView
import com.goldcompany.apps.koreabike.util.SearchAddressResultView
import com.goldcompany.apps.koreabike.util.UIState
import com.goldcompany.koreabike.data.model.address.Address

@Composable
fun NavigationScreen(
    viewModel: NavigationViewModel = hiltViewModel(),
    navController: NavController,
    modifier: Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
    ) {
        DefaultKBikeTopAppBar(
            title = R.string.navigation,
            navigateBack = {
                navController.popBackStack()
            }
        )
        SearchNavigationView(
            viewModel = viewModel,
            navController = navController
        )
        SearchAddressListView(
            uiState = uiState,
            onClick = viewModel::setNavAddress
        )
    }
}

@Composable
private fun SearchNavigationView(
    viewModel: NavigationViewModel,
    navController: NavController
) {
    val searchStartAddress by viewModel.searchStartAddress
    val searchEndAddress by viewModel.searchEndAddress
    val defaultMargin = dimensionResource(id = R.dimen.default_margin)

    Row(
        modifier = Modifier
            .padding(top = defaultMargin)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        val shape = RoundedCornerShape(dimensionResource(id = R.dimen.default_corner_radius))

        Column(
            modifier = Modifier
                .padding(start = defaultMargin)
                .weight(2f)
        ) {
            val modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, LightGray, shape)
            val colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.white),
                textColor = colorResource(id = R.color.black),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
            val keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            )

            TextField(
                modifier = modifier.onKeyEvent {
                    if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER){
                        viewModel.searchAddress(searchStartAddress)
                    }
                    false
                },
                value = searchStartAddress,
                shape = shape,
                colors = colors,
                onValueChange = { address ->
                    viewModel.setSearchStartAddress(address)
                    viewModel.setIsStartAddressFlag(true)
                },
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.searchAddress(searchStartAddress)
                    }
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(defaultMargin))
            TextField(
                modifier = modifier.onKeyEvent {
                    if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER){
                        viewModel.searchAddress(searchEndAddress)
                    }
                    false
                },
                value = searchEndAddress,
                shape = shape,
                colors = colors,
                onValueChange = { address ->
                    viewModel.setSearchEndAddress(address)
                    viewModel.setIsStartAddressFlag(false)
                },
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.searchAddress(searchEndAddress)
                    }
                ),
                singleLine = true
            )
        }
        Button(
            modifier = Modifier
                .padding(horizontal = defaultMargin)
                .fillMaxHeight()
                .weight(1f),
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.colorPrimary),
                contentColor = colorResource(id = R.color.white)
            ),
            onClick = {
                navController.navigate(
                    KBikeScreen.NavigationDetail.route +
                            "?startCoordinate=${viewModel.startCoordinate}," +
                            "endCoordinate=${viewModel.endCoordinate}"
                )
            },
            content = {
                Text(
                    text = stringResource(id = R.string.navigation_button),
                    textAlign = TextAlign.Center,
                    style = KBikeTypography.button
                )
            }
        )
    }
}

@Composable
private fun SearchAddressListView(
    uiState: NavigationUiState,
    onClick: (Address) -> Unit
) {
    val modifier = Modifier
        .fillMaxSize()
        .padding(dimensionResource(id = R.dimen.default_margin))

    when (uiState.uiState) {
        UIState.INIT -> {
            DefaultTextView(
                modifier = modifier,
                stringResource = R.string.init_page
            )
        }
        UIState.LOADING -> {
//            CircularLoadingView(modifier)
        }
        UIState.DONE -> {
            SearchAddressResultView(
                modifier = modifier,
                addressList = uiState.addresses,
                onClick = onClick,
                searchNextAddressPage = {}
            )
        }
        else -> {
            ErrorMessageTextView(modifier)
        }
    }
}
