package com.goldcompany.apps.koreabike.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.goldcompany.apps.koreabike.R
import com.goldcompany.koreabike.data.model.address.Address


@Composable
fun DefaultAddressItemView(
    address: Address,
    onClick: (Address) -> Unit,
    navigateBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.default_margin))
            .clickable {
                onClick(address)
                navigateBack()
            }
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
    }
}

@Composable
fun HistoryPlaceAddressItemView(
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
private fun DefaultAddressItemPreView() {
    MaterialTheme {
        Surface {
            DefaultAddressItemView(
                address = Address("", "addressNameaddressNameaddressNameaddressName", "", "", "", "placeName", "", "", ""),
                onClick = {},
                navigateBack = {}
            )
        }
    }
}

@Preview
@Composable
private fun HistoryPlaceAddressItemPreView() {
    MaterialTheme {
        Surface {
            HistoryPlaceAddressItemView(
                address = Address("", "addressNameaddressNameaddressNameaddressName", "", "", "", "placeName", "", "", ""),
                deleteAddress = { },
                onClick = { }
            )
        }
    }
}