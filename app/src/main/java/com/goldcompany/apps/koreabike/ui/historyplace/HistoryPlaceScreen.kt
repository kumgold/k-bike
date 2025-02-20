package com.goldcompany.apps.koreabike.ui.historyplace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.goldcompany.apps.koreabike.KBikeScreen
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.util.DefaultKBikeTopAppBar
import com.goldcompany.apps.koreabike.util.DefaultTextView
import com.goldcompany.apps.koreabike.util.HistoryPlaceAddressItemView
import com.goldcompany.koreabike.data.model.address.Address

@Composable
fun HistoryPlaceScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryPlaceViewModel = hiltViewModel(),
    navController: NavController
) {
    Column(
        modifier = modifier
    ) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        DefaultKBikeTopAppBar(title = R.string.search_list)
        AddressLazyColumn(
            modifier = modifier,
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

@Composable
fun AddressLazyColumn(
    modifier: Modifier = Modifier,
    addressList: List<Address>,
    deleteAddress: (Address) -> Unit,
    onClick: (Address) -> Unit
) {
    if (addressList.isEmpty()) {
        DefaultTextView(
            modifier = modifier.padding(top = 10.dp),
            stringResource = R.string.init_page
        )
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.default_margin)),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.default_margin)),
            modifier = modifier
        ) {
            items(addressList) { address ->
                HistoryPlaceAddressItemView(
                    address = address,
                    deleteAddress = { deleteAddress(address) },
                    onClick = { onClick(address) }
                )
            }
        }
    }
}
