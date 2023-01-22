package com.goldcompany.koreabike.data.module

import android.content.Context
import com.goldcompany.koreabike.data.db.AddressDAO
import com.goldcompany.koreabike.data.db.KBikeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
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