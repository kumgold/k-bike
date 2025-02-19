package com.goldcompany.koreabike.data.model.address

import com.google.gson.annotations.SerializedName

data class RemoteAddressResponse(
    @SerializedName("documents") val addressList: List<RemoteAddress>,
    val meta: RemoteAddressMetaData
)