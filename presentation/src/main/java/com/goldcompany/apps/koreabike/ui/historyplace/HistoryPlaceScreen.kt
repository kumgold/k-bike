package com.goldcompany.apps.koreabike.ui.historyplace

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.goldcompany.apps.koreabike.nav.KBikeScreen
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.compose.ui.AddressTextView
import com.goldcompany.apps.koreabike.compose.ui.DefaultKBikeTopAppBar
import com.goldcompany.apps.koreabike.compose.ui.DefaultTextView
import com.goldcompany.koreabike.domain.model.address.Address

@Composable
fun HistoryPlaceScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryPlaceViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavController
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { 
            DefaultKBikeTopAppBar(title = R.string.search_list)
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsState()

        if (uiState.isLoading) {
            Box(modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.colorPrimary),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            AddressLazyColumn(
                modifier = Modifier.padding(paddingValues),
                addressList = uiState.items,
                deleteAddress = viewModel::deleteAddress,
                onClick = {
                    viewModel.setCurrentAddress(it)
                    navController.navigate(KBikeScreen.BikeMap.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
fun AddressLazyColumn(
    modifier: Modifier = Modifier,
    addressList: List<Address>,
    deleteAddress: (Address) -> Unit,
    onClick: (Address) -> Unit
) {
    if (addressList.isEmpty()) {
        DefaultTextView(
            modifier = modifier,
            stringResource = R.string.search_address_hint2
        )
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.default_margin)),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.default_margin)),
            modifier = modifier
        ) {
            items(addressList) { address ->
                HistoryPlaceAddressItem(
                    address = address,
                    deleteAddress = { deleteAddress(address) },
                    onClick = { onClick(address) }
                )
            }
        }
    }
}

@Composable
private fun HistoryPlaceAddressItem(
    address: Address,
    deleteAddress: (Address) -> Unit,
    onClick: (Address) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(address)
            }
            .padding(horizontal = dimensionResource(id = R.dimen.default_margin))
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_search_button),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = dimensionResource(id = R.dimen.default_margin))
        ) {
            AddressTextView(text = address.placeName)
            AddressTextView(text = address.addressName)
        }
        Image(
            painter = painterResource(id = R.drawable.ic_delete_button),
            contentDescription = null,
            modifier = Modifier
                .clickable { deleteAddress(address) }
        )
    }
}

@Preview
@Composable
private fun HistoryPlaceAddressItemPreView() {
    MaterialTheme {
        Surface {
            HistoryPlaceAddressItem(
                address = Address("", "addressNameaddressNameaddressNameaddressName", "", "", "", "placeName", "", "", ""),
                deleteAddress = { },
                onClick = { }
            )
        }
    }
}