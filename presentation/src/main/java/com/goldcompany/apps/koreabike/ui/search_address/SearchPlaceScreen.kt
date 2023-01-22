package com.goldcompany.apps.koreabike.ui.search_address

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.compose.DefaultAddressItemView
import com.goldcompany.apps.koreabike.util.LoadingState
import com.goldcompany.apps.koreabike.util.SearchAppBar
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
        val commonModifier = Modifier.fillMaxSize().padding(8.dp)

        when (uiState.isLoading) {
            LoadingState.INIT -> {
                Text(
                    text = stringResource(id = R.string.init_page),
                    modifier = commonModifier,
                    textAlign = TextAlign.Center
                )
            }
            LoadingState.LOADING -> {
                Box(modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.colorPrimary),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
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
                Text(
                    text = stringResource(id = R.string.error_code),
                    modifier = commonModifier,
                    textAlign = TextAlign.Center
                )
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
                .padding(8.dp),
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
    val endOfListReached by remember {
        derivedStateOf {
            listState.isScrolledToEnd()
        }
    }

    LaunchedEffect(
        endOfListReached
    ) {
        if (endOfListReached) {
            searchNextAddressPage()
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
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