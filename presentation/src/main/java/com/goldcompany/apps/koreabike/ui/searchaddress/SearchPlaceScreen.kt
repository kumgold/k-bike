package com.goldcompany.apps.koreabike.ui.searchaddress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.compose.ui.CircularLoadingView
import com.goldcompany.apps.koreabike.compose.ui.DefaultSearchAppBar
import com.goldcompany.apps.koreabike.compose.ui.DefaultTextView
import com.goldcompany.apps.koreabike.compose.ui.ErrorMessageTextView
import com.goldcompany.apps.koreabike.compose.ui.SearchAddressResultView
import com.goldcompany.apps.koreabike.util.UIState

@Composable
fun SearchAddressScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchAddressViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        DefaultSearchAppBar(
            title = R.string.search_list,
            navigateBack = { navController.popBackStack() }
        )

        when (uiState.uiState) {
            UIState.INIT -> {
                DefaultTextView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.default_margin)),
                    stringResource = R.string.init_page
                )
            }
            UIState.LOADING -> {
                CircularLoadingView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.default_margin))
                )
            }
            UIState.DONE -> {
                val listState: LazyListState = rememberLazyListState()

                SearchAddressResultView(
                    modifier = Modifier
                        .fillMaxSize(),
                    addressList = uiState.addresses,
                    onClick = viewModel::setCurrentAddress,
                    navigateBack = { navController.popBackStack() },
                    searchNextAddressPage = viewModel::searchAddress,
                    listState = listState
                )
            }
            else -> {
                ErrorMessageTextView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.default_margin))
                )
            }
        }
    }
}
