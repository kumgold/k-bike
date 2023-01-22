package com.goldcompany.koreabike.data.model.address

import com.google.gson.annotations.SerializedName

data class ApiAddressResponse(
    @SerializedName("documents") val addressList: List<ApiAddress>,
    val meta: AddressMetaData
)