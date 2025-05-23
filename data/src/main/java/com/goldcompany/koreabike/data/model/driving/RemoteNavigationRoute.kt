package com.goldcompany.koreabike.data.model.driving

import com.google.gson.annotations.SerializedName

data class RemoteNavigationRoute(
    @SerializedName("tracomfort") val comfort: List<ApiNavigationTrack>?,
    @SerializedName("traoptimal") val optimal: List<ApiNavigationTrack>?,
    @SerializedName("trafast") val fast: List<ApiNavigationTrack>?
)