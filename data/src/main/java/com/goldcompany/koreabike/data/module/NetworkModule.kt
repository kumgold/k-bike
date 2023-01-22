package com.goldcompany.koreabike.data.module

import com.goldcompany.koreabike.data.Constants
import com.goldcompany.koreabike.data.api.KakaoApiService
import com.goldcompany.koreabike.data.api.NaverApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideKakaoApiService(): KakaoApiService = Retrofit.Builder()
        .baseUrl(Constants.KAKAO_BASE_URL)
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(KakaoApiService::class.java)

    @Singleton
    @Provides
    fun provideNaverApiService(): NaverApiService = Retrofit.Builder()
        .baseUrl(Constants.NAVER_BASE_URL)
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NaverApiService::class.java)
}