package com.goldcompany.koreabike.domain.model.navigation

data class NavigationTrack(
    val path: List<List<Double>>,
    val distance: Int,
    val duration: Int
)