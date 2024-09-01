package com.goldcompany.apps.koreabike.ui.searchaddress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
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
import com.goldcompany.apps.koreabike.compose.ui.SearchAddressResultView
import com.goldcompany.apps.koreabike.compose.ui.SearchTextField

@Composable
fun SearchPlaceScreen(
    viewModel: SearchPlaceViewModel = hiltViewModel(),
    navController: NavController,
    snackBarHostState: SnackbarHostState
) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        DefaultSearchAppBar(
            title = R.string.search_list,
            navigateBack = { navController.popBackStack() }
        )
        SearchTextField { place ->
            viewModel.searchPlace(place)
        }

        if (uiState.isLoading) {
            CircularLoadingView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.default_margin))
            )
        } else {
            if (uiState.addressList.isNotEmpty()) {
                SearchAddressResultView(
                    modifier = Modifier.fillMaxSize(),
                    addressList = uiState.addressList,
                    onClick = {},
                    navigateBack = { navController.popBackStack() },
                    searchNextAddressPage = { viewModel.getNextPage() },
                    isEnd = uiState.isEnd
                )
            } else {
                DefaultTextView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.default_margin)),
                    stringResource = R.string.init_page
                )
            }
        }
    }
}
