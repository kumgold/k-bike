package com.goldcompany.koreabike.data.model.driving

data class ApiNavigationTrack(
    val guide: List<Guide>,
    val path: List<List<Double>>,
    val section: List<Section>,
    val summary: Summary
)

data class Guide(
    val distance: Int,
    val duration: Int,
    val instructions: String,
    val pointIndex: Int,
    val type: Int
)

data class Section(
    val congestion: Int,
    val distance: Int,
    val name: String,
    val pointCount: Int,
    val pointIndex: Int,
    val speed: Int
)

data class Summary(
    val bbox: List<List<Double>>,
    val distance: Int,
    val duration: Int,
    val fuelPrice: Int,
    val goal: Goal,
    val start: Start,
    val taxiFare: Int,
    val tollFare: Int
)

data class Start(
    val location: List<Double>
)

data class Goal(
    val dir: Int,
    val location: List<Double>
)