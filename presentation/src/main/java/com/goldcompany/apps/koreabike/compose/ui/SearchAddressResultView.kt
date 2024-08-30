package com.goldcompany.apps.koreabike.compose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.goldcompany.apps.koreabike.R
import com.goldcompany.koreabike.domain.model.address.Address

@Composable
fun SearchAddressResultView(
    modifier: Modifier,
    addressList: List<Address>,
    onClick: (Address) -> Unit,
    navigateBack: () -> Unit = {},
    searchNextAddressPage: () -> Unit,
    listState: LazyListState,
    isEnd: Boolean = true
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
            onAddressClick = onClick,
            navigateBack = navigateBack,
            searchNextAddressPage = searchNextAddressPage,
            listState = listState,
            isEnd = isEnd
        )
    }
}

@Composable
private fun AddressLazyColumn(
    modifier: Modifier,
    addressList: List<Address>,
    onAddressClick: (Address) -> Unit,
    navigateBack: () -> Unit,
    searchNextAddressPage: () -> Unit,
    listState: LazyListState = rememberLazyListState(),
    isEnd: Boolean = true
) {
    LazyColumn(
        contentPadding = PaddingValues(
            vertical = dimensionResource(id = R.dimen.default_margin)
        ),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.default_margin)),
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
                onClick = onAddressClick,
                navigateBack = navigateBack
            )
        }
        if (addressList.isNotEmpty() && !isEnd) {
            item {
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { searchNextAddressPage() }
                ) {
                    Text(text = "More")
                }
            }
        }
    }
}