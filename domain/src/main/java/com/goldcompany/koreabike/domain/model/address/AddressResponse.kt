package com.goldcompany.koreabike.domain.model.address

data class AddressResponse(
    val list: List<Address>,
    val isEnd: Boolean
)