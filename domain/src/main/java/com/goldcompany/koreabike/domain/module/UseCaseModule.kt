package com.goldcompany.koreabike.domain.module

import com.goldcompany.koreabike.domain.repository.KBikeRepository
import com.goldcompany.koreabike.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {
    @Singleton
    @Provides
    fun provideSearchAddressUseCase(
        repository: KBikeRepository
    ): SearchAddressUseCase = SearchAddressUseCase(repository)

    @Singleton
    @Provides
    fun provideSearchNearbyPlacesUseCase(
        repository: KBikeRepository
    ): SearchCategoryPlacesUseCase = SearchCategoryPlacesUseCase(repository)

    @Singleton
    @Provides
    fun provideGetAllHistoryAddressUseCase(
        repository: KBikeRepository
    ): GetAllHistoryAddressUseCase = GetAllHistoryAddressUseCase(repository)

    @Singleton
    @Provides
    fun provideGetCurrentAddressUseCase(
        repository: KBikeRepository
    ): GetCurrentAddressUseCase = GetCurrentAddressUseCase(repository)

    @Singleton
    @Provides
    fun provideCurrentAddressUnselectedUseCase(
        repository: KBikeRepository
    ): UpdateCurrentAddressUnselectedUseCase = UpdateCurrentAddressUnselectedUseCase(repository)

    @Singleton
    @Provides
    fun provideInsertAddressUseCase(
        repository: KBikeRepository
    ): InsertAddressUseCase = InsertAddressUseCase(repository)

    @Singleton
    @Provides
    fun provideDeleteAddressUseCase(
        repository: KBikeRepository
    ): DeleteAddressUseCase = DeleteAddressUseCase(repository)
}