package com.goldcompany.apps.koreabike.ui.history_place

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.compose.AddressTextView
import com.goldcompany.apps.koreabike.util.ListPageTopAppBar
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
            ListPageTopAppBar(
                title = R.string.search_list,
                navigateBack = {navController.popBackStack()}
            )
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
                onClick = viewModel::setCurrentAddress
            )
        }
    }
}

@Composable
private fun AddressLazyColumn(
    modifier: Modifier,
    addressList: List<Address>,
    deleteAddress: (Address) -> Unit,
    onClick: (Address) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
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

@Composable
private fun HistoryPlaceAddressItem(
    address: Address,
    deleteAddress: (Address) -> Unit,
    onClick: (Address) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.list_item_horizontal_margin))
            .clickable { onClick(address) }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_search_button),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
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