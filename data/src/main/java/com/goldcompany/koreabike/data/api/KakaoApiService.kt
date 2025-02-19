package com.goldcompany.koreabike.data.api

import com.goldcompany.koreabike.data.model.address.RemoteAddressResponse
import com.goldcompany.koreabike.data.model.place.RemotePlaceMarkerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoApiService {
    @GET("v2/local/search/keyword.json")
    suspend fun searchAddress(
        @Query("query") address: String,
        @Query("page") page: Int
    ): RemoteAddressResponse

    @GET("v2/local/search/category.json")
    suspend fun searchCategoryPlaces(
        @Query("category_group_code") code: String,
        @Query("x") longitude: String,
        @Query("y") latitude: String,
        @Query("radius") radius: Int
    ): RemotePlaceMarkerResponse
}