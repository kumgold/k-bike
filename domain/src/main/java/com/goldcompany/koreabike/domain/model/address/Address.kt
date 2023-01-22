package com.goldcompany.koreabike.domain.model.address

data class Address(
    val id: String,
    val addressName: String,
    val roadAddressName: String,
    val categoryName: String,
    val phone: String,
    val placeName: String,
    val placeUrl: String,
    val x: String,
    val y: String
)