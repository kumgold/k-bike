package com.goldcompany.apps.koreabike.nav

import androidx.annotation.StringRes
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.constants.KBikeDestinations

sealed class KBikeScreen(val route: String, @StringRes val resourceId: Int) {
    object BikeMap: KBikeScreen(KBikeDestinations.BIKE_MAP_SCREEN, R.string.bike_map)
    object SearchPlace: KBikeScreen(KBikeDestinations.SEARCH_PLACE_SCREEN, R.string.search_place)
    object MyPlace: KBikeScreen(KBikeDestinations.MY_PLACE_SCREEN, R.string.my_place)
    object Record: KBikeScreen(KBikeDestinations.RECORD_SCREEN, R.string.record)
    object Navigation: KBikeScreen(KBikeDestinations.NAVIGATION, R.string.navigation)
    object NavigationDetail: KBikeScreen(KBikeDestinations.NAVIGATION_DETAIL, R.string.navigation_detail)
}
