package com.goldcompany.koreabike.data.model.navigation

data class NavigationTrack(
    val path: List<List<Double>>,
    val distance: Int,
    val duration: Int
)