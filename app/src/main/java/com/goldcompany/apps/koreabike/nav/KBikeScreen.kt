package com.goldcompany.apps.koreabike.nav

import androidx.annotation.StringRes
import com.goldcompany.apps.koreabike.R

sealed class KBikeScreen(val route: String, @StringRes val resourceId: Int) {
    data object BikeMap: KBikeScreen(KBikeDestinations.BIKE_MAP_SCREEN, R.string.bike_map)
    data object SearchPlace: KBikeScreen(KBikeDestinations.SEARCH_PLACE_SCREEN, R.string.search_place)
    data object MyPlace: KBikeScreen(KBikeDestinations.MY_PLACE_SCREEN, R.string.my_place)
    data object Record: KBikeScreen(KBikeDestinations.RECORD_SCREEN, R.string.record)
    data object Navigation: KBikeScreen(KBikeDestinations.NAVIGATION, R.string.navigation)
    data object NavigationDetail: KBikeScreen(KBikeDestinations.NAVIGATION_DETAIL, R.string.navigation_detail)
}
