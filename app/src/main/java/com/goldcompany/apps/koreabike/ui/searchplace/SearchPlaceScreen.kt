package com.goldcompany.apps.koreabike.ui.searchplace

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.util.CircularLoadingView
import com.goldcompany.apps.koreabike.util.DefaultSearchAppBar
import com.goldcompany.apps.koreabike.util.DefaultTextView
import com.goldcompany.apps.koreabike.util.SearchAddressResultView
import com.goldcompany.apps.koreabike.util.SearchTextField

@Composable
fun SearchPlaceScreen(
    viewModel: SearchPlaceViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                    onClick = { address -> viewModel.setCurrentAddress(address) },
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
