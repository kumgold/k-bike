package com.goldcompany.koreabike.data.model.place

import com.goldcompany.koreabike.data.model.address.RemoteAddress
import com.google.gson.annotations.SerializedName

data class RemotePlaceMarkerResponse(
    @SerializedName("documents") val apiPlaces: List<RemoteAddress>,
    val meta: RemotePlaceMarkerMetaData
)