package com.goldcompany.apps.koreabike

import androidx.annotation.StringRes

sealed class KBikeScreen(val route: String, @StringRes val resourceId: Int) {
    object BikeMap: KBikeScreen(KBikeDestinations.BIKE_MAP_SCREEN, R.string.bike_map)
    object SearchPlace: KBikeScreen(KBikeDestinations.SEARCH_PLACE_SCREEN, R.string.search_place)
    object MyPlace: KBikeScreen(KBikeDestinations.MY_PLACE_SCREEN, R.string.my_place)
}
