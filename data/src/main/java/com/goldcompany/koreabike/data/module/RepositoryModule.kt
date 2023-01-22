package com.goldcompany.koreabike.data.module

import com.goldcompany.koreabike.data.repository.KBikeRepositoryImpl
import com.goldcompany.koreabike.data.repository.local.KBikeLocalDataSource
import com.goldcompany.koreabike.data.repository.remote.KBikeRemoteDataSource
import com.goldcompany.koreabike.domain.repository.KBikeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideKBikeRepository(
        remoteDataSource: KBikeRemoteDataSource,
        localDataSource: KBikeLocalDataSource
    ): KBikeRepository = KBikeRepositoryImpl(localDataSource, remoteDataSource)
}