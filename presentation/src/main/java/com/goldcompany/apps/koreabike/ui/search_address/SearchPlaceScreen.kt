package com.goldcompany.apps.koreabike.ui.search_address

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
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
import com.goldcompany.apps.koreabike.compose.ui.DefaultTextView
import com.goldcompany.apps.koreabike.compose.ui.ErrorMessageTextView
import com.goldcompany.apps.koreabike.compose.ui.SearchAddressResultView
import com.goldcompany.apps.koreabike.compose.ui.SearchAppBar
import com.goldcompany.apps.koreabike.util.LoadingState

@Composable
fun SearchAddressScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchAddressViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavController
) {
    val searchAppBarState by viewModel.searchAppBarState
    val searchAddressState by viewModel.searchAddressState

    val listState: LazyListState = rememberLazyListState()

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            SearchAppBar(
                title = R.string.search_list,
                place = searchAddressState,
                searchAppBarState = searchAppBarState,
                navigateBack = { navController.popBackStack() },
                onClickForSearch = { viewModel.setSearchAppBarStateOpen() },
                onClickForSearchClose = { viewModel.setSearchAppBarStateClose() },
                onSearchPlaceChange = viewModel::setSearchAddressState,
                onSearch = viewModel::searchAddress
            )
        }
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsState()
        val commonModifier = Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.default_margin))

        when (uiState.loadingState) {
            LoadingState.INIT -> {
                DefaultTextView(commonModifier)
            }
            LoadingState.LOADING -> {
                CircularLoadingView(commonModifier)
            }
            LoadingState.DONE -> {
                SearchAddressResultView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    addressList = uiState.addresses,
                    onClick = viewModel::setCurrentAddress,
                    navigateBack = { navController.popBackStack() },
                    searchNextAddressPage = viewModel::searchAddress,
                    listState = listState
                )
            }
            else -> {
                ErrorMessageTextView(commonModifier)
            }
        }
    }
}
