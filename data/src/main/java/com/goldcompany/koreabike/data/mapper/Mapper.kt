package com.goldcompany.koreabike.data.mapper

import com.goldcompany.koreabike.data.db.AddressEntity
import com.goldcompany.koreabike.data.model.address.RemoteAddress
import com.goldcompany.koreabike.data.model.driving.ApiNavigationTrack
import com.goldcompany.koreabike.data.model.address.Address
import com.goldcompany.koreabike.data.model.navigation.Navigation
import com.goldcompany.koreabike.data.model.navigation.NavigationTrack

fun mapperApiAddressToAddress(remoteAddress: RemoteAddress): Address {
    return Address(
        id = remoteAddress.id,
        addressName = remoteAddress.addressName,
        roadAddressName = remoteAddress.roadAddressName,
        categoryName = remoteAddress.categoryName,
        phone = remoteAddress.phone,
        placeName = remoteAddress.placeName,
        placeUrl = remoteAddress.placeUrl,
        x = remoteAddress.x,
        y = remoteAddress.y
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