package com.goldcompany.koreabike.data.api

import com.goldcompany.koreabike.data.model.driving.RemoteNavigationPathResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverApiService {
    @GET("map-direction/v1/driving")
    suspend fun getPath(
        @Query("start") start: String,
        @Query("goal") goal: String,
        @Query("option") option: String = "tracomfort"
    ): RemoteNavigationPathResponse
}