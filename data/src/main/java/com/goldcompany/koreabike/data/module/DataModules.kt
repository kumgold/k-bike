package com.goldcompany.koreabike.data.module

import android.content.Context
import com.goldcompany.koreabike.data.api.KakaoApiService
import com.goldcompany.koreabike.data.api.NaverApiService
import com.goldcompany.koreabike.data.db.AddressDAO
import com.goldcompany.koreabike.data.db.KBikeDatabase
import com.goldcompany.koreabike.data.repository.KBikeRepository
import com.goldcompany.koreabike.data.repository.local.KBikeLocalDataSource
import com.goldcompany.koreabike.data.repository.remote.KBikeRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideKBikeRepository(
        remoteDataSource: KBikeRemoteDataSource,
        localDataSource: KBikeLocalDataSource
    ): KBikeRepository = KBikeRepository(localDataSource, remoteDataSource)
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideKBikeDatabase(
        @ApplicationContext context: Context
    ): KBikeDatabase = KBikeDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideAddressDao(
        kBikeDatabase: KBikeDatabase
    ): AddressDAO = kBikeDatabase.addressDAO()
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModules {
    @Singleton
    @Provides
    fun provideRemoteDataSource(
        kakaoApiService: KakaoApiService,
        naverApiService: NaverApiService
    ): KBikeRemoteDataSource = KBikeRemoteDataSource(kakaoApiService, naverApiService)

    @Singleton
    @Provides
    fun provideLocalDataSource(
        addressDAO: AddressDAO
    ) : KBikeLocalDataSource = KBikeLocalDataSource(addressDAO)
}