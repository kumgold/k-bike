package com.goldcompany.koreabike.data.module

import com.goldcompany.koreabike.data.api.KakaoApiService
import com.goldcompany.koreabike.data.api.NaverApiService
import com.goldcompany.koreabike.data.db.AddressDAO
import com.goldcompany.koreabike.data.repository.local.KBikeLocalDataSource
import com.goldcompany.koreabike.data.repository.local.KBikeLocalDataSourceImpl
import com.goldcompany.koreabike.data.repository.remote.KBikeRemoteDataSource
import com.goldcompany.koreabike.data.repository.remote.KBikeRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataSourceModule {
    @Singleton
    @Provides
    fun provideRemoteDataSource(
        kakaoApiService: KakaoApiService,
        naverApiService: NaverApiService
    ): KBikeRemoteDataSource = KBikeRemoteDataSourceImpl(kakaoApiService, naverApiService)

    @Singleton
    @Provides
    fun provideLocalDataSource(
        addressDAO: AddressDAO
    ) : KBikeLocalDataSource = KBikeLocalDataSourceImpl(addressDAO)
}