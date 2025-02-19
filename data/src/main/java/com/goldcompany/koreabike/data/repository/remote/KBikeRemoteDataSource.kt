package com.goldcompany.koreabike.data.repository.remote

import com.goldcompany.koreabike.data.api.KakaoApiService
import com.goldcompany.koreabike.data.api.NaverApiService
import com.goldcompany.koreabike.data.model.address.RemoteAddressResponse
import com.goldcompany.koreabike.data.model.driving.RemoteNavigationPathResponse
import com.goldcompany.koreabike.data.model.place.RemotePlaceMarkerResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KBikeRemoteDataSource @Inject constructor(
    private val kakaoApiService: KakaoApiService,
    private val naverApiService: NaverApiService
) {

    suspend fun searchAddress(address: String, page: Int): RemoteAddressResponse {
        return kakaoApiService.searchAddress(
            address = address,
            page = page
        )
    }

    suspend fun searchCategoryPlaces(
        code: String,
        longitude: String,
        latitude: String
    ): RemotePlaceMarkerResponse {
        return kakaoApiService.searchCategoryPlaces(
            code = code,
            longitude = longitude,
            latitude = latitude,
            radius = 10000
        )
    }

    suspend fun getNavigationPath(start: String, end: String): RemoteNavigationPathResponse {
        return naverApiService.getPath(
            start = start,
            goal = end
        )
    }
}