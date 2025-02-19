package com.goldcompany.koreabike.data.repository.remote

import com.goldcompany.koreabike.data.api.KakaoApiService
import com.goldcompany.koreabike.data.api.NaverApiService
import com.goldcompany.koreabike.data.model.address.ApiAddressResponse
import com.goldcompany.koreabike.data.model.driving.ApiNavigationPathResponse
import com.goldcompany.koreabike.data.model.place.ApiPlaceMarkerResponse
import javax.inject.Singleton

@Singleton
interface KBikeRemoteDataSource {
    suspend fun searchAddress(address: String, page: Int): ApiAddressResponse

    suspend fun searchCategoryPlaces(
        code: String,
        longitude: String,
        latitude: String
    ): ApiPlaceMarkerResponse

    suspend fun getNavigationPath(start: String, end: String): ApiNavigationPathResponse
}

class KBikeRemoteDataSourceImpl(
    private val kakaoApiService: KakaoApiService,
    private val naverApiService: NaverApiService
): KBikeRemoteDataSource {

    override suspend fun searchAddress(address: String, page: Int): ApiAddressResponse {
        return kakaoApiService.searchAddress(
            address = address,
            page = page
        )
    }

    override suspend fun searchCategoryPlaces(
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

    override suspend fun getNavigationPath(start: String, end: String): ApiNavigationPathResponse {
        return naverApiService.getPath(
            start = start,
            goal = end
        )
    }
}