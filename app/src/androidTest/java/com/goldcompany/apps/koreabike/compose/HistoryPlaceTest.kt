package com.goldcompany.apps.koreabike.compose

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.goldcompany.apps.koreabike.ui.historyplace.AddressLazyColumn
import com.goldcompany.koreabike.data.model.address.Address
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HistoryPlaceTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myPlace_emptyPlace() {
        startAddressList(emptyList())

        composeTestRule.onNodeWithText("주소를 검색하세요.").assertIsDisplayed()
    }

    @Test()
    fun myPlace_notEmptyPlace() {
        startAddressList(listOf(addressForTest()))

        composeTestRule.onNodeWithText("서울역").assertIsDisplayed()
        composeTestRule.onNodeWithText("서울 용산구 한강대로 405").assertIsDisplayed()
    }

    private fun startAddressList(list: List<Address>) {
        composeTestRule.setContent {
            AddressLazyColumn(
                modifier = Modifier,
                addressList = list,
                deleteAddress = {},
                onClick = {}
            )
        }
    }
}

internal fun addressForTest(): Address {
    return Address(
        id = "0000",
        addressName = "서울 용산구 한강대로 405",
        roadAddressName = "서울역 123",
        categoryName = "지하철",
        phone = "",
        placeName = "서울역",
        placeUrl = "",
        x = "",
        y = ""
    )
}