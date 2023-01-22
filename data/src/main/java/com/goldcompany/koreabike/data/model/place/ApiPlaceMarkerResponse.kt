package com.goldcompany.koreabike.data.model.place

import com.goldcompany.koreabike.data.model.address.ApiAddress
import com.google.gson.annotations.SerializedName

data class ApiPlaceMarkerResponse(
    @SerializedName("documents") val apiPlaces: List<ApiAddress>,
    val meta: PlaceMarkerMetaData
)