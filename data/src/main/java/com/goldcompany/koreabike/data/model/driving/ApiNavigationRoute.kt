package com.goldcompany.koreabike.data.model.driving

import com.google.gson.annotations.SerializedName

data class ApiNavigationRoute(
    @SerializedName("tracomfort") val comfort: List<ApiNavigationTrack>
//    @SerializedName("traoptimal") val optimal: List<Track>,
//    @SerializedName("trafast") val fast: List<Track>
)