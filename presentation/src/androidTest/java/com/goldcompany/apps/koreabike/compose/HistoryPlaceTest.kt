package com.goldcompany.apps.koreabike.compose

import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.goldcompany.apps.koreabike.MainActivity
import com.goldcompany.apps.koreabike.ui.historyplace.AddressLazyColumn
import com.goldcompany.koreabike.domain.model.address.Address
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistoryPlaceTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test()
    fun myPlace_listShown() {
        startAddressList()

        composeTestRule.onNodeWithText("서울역").assertIsDisplayed()
        composeTestRule.onNodeWithText("서울 용산구 한강대로 405").assertIsDisplayed()
    }

    private fun startAddressList() {
        composeTestRule.activity.setContent {
            AddressLazyColumn(
                modifier = Modifier,
                addressList = listOf(addressForTest()),
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