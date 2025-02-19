package com.goldcompany.koreabike.data.model.driving

import com.google.gson.annotations.SerializedName

data class RemoteNavigationPathResponse(
    val code: Int,
    val currentDateTime: String,
    val message: String,
    @SerializedName("route") val remoteNavigationRoute: RemoteNavigationRoute
)