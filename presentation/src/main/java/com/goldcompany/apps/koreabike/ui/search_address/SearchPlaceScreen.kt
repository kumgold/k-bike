package com.goldcompany.apps.koreabike.ui.search_address

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.compose.CircularLoadingView
import com.goldcompany.apps.koreabike.compose.DefaultAddressItemView
import com.goldcompany.apps.koreabike.compose.DefaultTextView
import com.goldcompany.apps.koreabike.compose.ErrorMessageTextView
import com.goldcompany.apps.koreabike.compose.SearchAppBar
import com.goldcompany.apps.koreabike.util.LoadingState
import com.goldcompany.koreabike.domain.model.address.Address

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
                    addressList = uiState.items,
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

@Composable
private fun SearchAddressResultView(
    modifier: Modifier,
    addressList: List<Address>,
    onClick: (Address) -> Unit,
    navigateBack: () -> Unit,
    searchNextAddressPage: () -> Unit,
    listState: LazyListState
) {
    if (addressList.isEmpty()) {
        Text(
            text = stringResource(id = R.string.no_data),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.default_margin)),
            textAlign = TextAlign.Center
        )
    } else {
        AddressLazyColumn(
            modifier = modifier,
            addressList = addressList,
            onClick = onClick,
            navigateBack = navigateBack,
            searchNextAddressPage = searchNextAddressPage,
            listState = listState
        )
    }
}

@Composable
private fun AddressLazyColumn(
    modifier: Modifier,
    addressList: List<Address>,
    onClick: (Address) -> Unit,
    navigateBack: () -> Unit,
    searchNextAddressPage: () -> Unit,
    listState: LazyListState = rememberLazyListState()
) {
    // remember -> save some state to composable
    val endOfListReached by remember {
        // derivedStateOf -> can be used for this to reduce the number of recompositions.
        // https://medium.com/androiddevelopers/jetpack-compose-when-should-i-use-derivedstateof-63ce7954c11b
        derivedStateOf {
            listState.isScrolledToEnd()
        }
    }
    val defaultMargin = dimensionResource(id = R.dimen.default_margin)

    LaunchedEffect(
        endOfListReached
    ) {
        if (endOfListReached) {
            searchNextAddressPage()
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(
            top = defaultMargin,
            bottom = defaultMargin
        ),
        verticalArrangement = Arrangement.spacedBy(defaultMargin),
        modifier = modifier,
        state = listState
    ) {
        items(
            items = addressList,
            key = { address ->
                address.id
            }
        ) { address ->
            DefaultAddressItemView(
                address = address,
                onClick = onClick,
                navigateBack = navigateBack
            )
        }
    }
}

fun LazyListState.isScrolledToEnd(): Boolean {
    val lastItem = layoutInfo.visibleItemsInfo.lastOrNull()
    return lastItem?.index == layoutInfo.totalItemsCount - 1
}