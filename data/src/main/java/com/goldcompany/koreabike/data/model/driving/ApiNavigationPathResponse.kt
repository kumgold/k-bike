package com.goldcompany.koreabike.data.model.driving

import com.google.gson.annotations.SerializedName

data class ApiNavigationPathResponse(
    val code: Int,
    val currentDateTime: String,
    val message: String,
    @SerializedName("route") val apiNavigationRoute: ApiNavigationRoute
)