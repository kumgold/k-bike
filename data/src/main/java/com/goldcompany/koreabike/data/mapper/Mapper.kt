package com.goldcompany.koreabike.data.mapper

import com.goldcompany.koreabike.data.db.AddressEntity
import com.goldcompany.koreabike.data.model.address.ApiAddress
import com.goldcompany.koreabike.data.model.driving.ApiNavigationTrack
import com.goldcompany.koreabike.domain.model.address.Address
import com.goldcompany.koreabike.domain.model.navigation.Navigation
import com.goldcompany.koreabike.domain.model.navigation.NavigationTrack

fun mapperApiAddressToAddress(apiAddress: ApiAddress): Address {
    return Address(
        id = apiAddress.id,
        addressName = apiAddress.addressName,
        roadAddressName = apiAddress.roadAddressName,
        categoryName = apiAddress.categoryName,
        phone = apiAddress.phone,
        placeName = apiAddress.placeName,
        placeUrl = apiAddress.placeUrl,
        x = apiAddress.x,
        y = apiAddress.y
    )
}

fun mapperAddressEntityListToAddressList(list: List<AddressEntity>): List<Address> {
    val addressList = mutableListOf<Address>()

    list.forEach {
        addressList.add(mapperUserAddressEntityToAddress(it))
    }

    return addressList
}

fun mapperUserAddressEntityToAddress(entity: AddressEntity): Address {
    return Address(
        id = entity.id,
        addressName = entity.address,
        roadAddressName = entity.roadAddress,
        categoryName = entity.category,
        phone = entity.phone,
        placeUrl = entity.placeUrl,
        placeName = entity.placeName,
        x = entity.latitude.toString(),
        y = entity.longitude.toString()
    )
}

fun mapperAddressToUserAddressEntity(address: Address): AddressEntity {
    return AddressEntity(
        selected = true,
        id = address.id,
        address = address.addressName,
        roadAddress = address.roadAddressName,
        category = address.categoryName,
        phone = address.phone,
        placeName = address.placeName,
        placeUrl = address.placeUrl,
        latitude = address.x.toDouble(),
        longitude = address.y.toDouble()
    )
}

fun mapperApiRouteToNavigation(tracks: List<ApiNavigationTrack>): Navigation {
    val list = arrayListOf<NavigationTrack>()
    tracks.forEach {
        list.add(
            NavigationTrack(
                path = it.path,
                distance = it.summary.distance,
                duration = it.summary.duration
            )
        )
    }
    return Navigation(list)
}