package com.goldcompany.koreabike.data.api

import com.goldcompany.koreabike.data.model.driving.ApiNavigationPathResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverApiService {
    @GET("map-direction/v1/driving")
    suspend fun getPath(
        @Header("X-NCP-APIGW-API-KEY-ID") apiKeyId: String,
        @Header("X-NCP-APIGW-API-KEY") apiKey: String,
        @Query("start") start: String,
        @Query("goal") goal: String,
        @Query("option") option: String = "tracomfort"
    ): ApiNavigationPathResponse
}