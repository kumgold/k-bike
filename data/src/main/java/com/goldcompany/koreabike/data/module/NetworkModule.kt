package com.goldcompany.koreabike.data.module

import com.goldcompany.koreabike.data.BuildConfig
import com.goldcompany.koreabike.data.api.KakaoApiService
import com.goldcompany.koreabike.data.api.NaverApiService
import com.goldcompany.koreabike.data.interceptor.KakaoInterceptor
import com.goldcompany.koreabike.data.interceptor.NaverInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideKakaoApiService(): KakaoApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.KAKAO_BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(KakaoInterceptor())
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(KakaoApiService::class.java)

    @Singleton
    @Provides
    fun provideNaverApiService(): NaverApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.NAVER_BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(NaverInterceptor())
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NaverApiService::class.java)
}