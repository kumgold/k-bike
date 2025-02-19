package com.goldcompany.koreabike.data.repository.remote

import com.goldcompany.koreabike.data.api.KakaoApiService
import com.goldcompany.koreabike.data.api.NaverApiService
import com.goldcompany.koreabike.data.model.address.ApiAddressResponse
import com.goldcompany.koreabike.data.model.driving.ApiNavigationPathResponse
import com.goldcompany.koreabike.data.model.place.ApiPlaceMarkerResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KBikeRemoteDataSource @Inject constructor(
    private val kakaoApiService: KakaoApiService,
    private val naverApiService: NaverApiService
) {

    suspend fun searchAddress(address: String, page: Int): ApiAddressResponse {
        return kakaoApiService.searchAddress(
            address = address,
            page = page
        )
    }

    suspend fun searchCategoryPlaces(
        code: String,
        longitude: String,
        latitude: String
    ): ApiPlaceMarkerResponse {
        return kakaoApiService.searchCategoryPlaces(
            code = code,
            longitude = longitude,
            latitude = latitude,
            radius = 10000
        )
    }

    suspend fun getNavigationPath(start: String, end: String): ApiNavigationPathResponse {
        return naverApiService.getPath(
            start = start,
            goal = end
        )
    }
}