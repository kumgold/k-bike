package com.goldcompany.koreabike.data.repository

import android.util.Log
import com.goldcompany.koreabike.data.mapper.mapperAddressEntityListToAddressList
import com.goldcompany.koreabike.data.mapper.mapperAddressToUserAddressEntity
import com.goldcompany.koreabike.data.mapper.mapperApiAddressToAddress
import com.goldcompany.koreabike.data.mapper.mapperApiRouteToNavigation
import com.goldcompany.koreabike.data.mapper.mapperUserAddressEntityToAddress
import com.goldcompany.koreabike.data.model.address.Address
import com.goldcompany.koreabike.data.model.address.AddressResponse
import com.goldcompany.koreabike.data.model.navigation.Navigation
import com.goldcompany.koreabike.data.repository.local.KBikeLocalDataSource
import com.goldcompany.koreabike.data.repository.remote.KBikeRemoteDataSource
import com.goldcompany.koreabike.data.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KBikeRepository @Inject constructor(
    private val localDataSource: KBikeLocalDataSource,
    private val remoteDataSource: KBikeRemoteDataSource
) {
    suspend fun searchAddress(
        address: String,
        page: Int
    ): Result<AddressResponse> {
        try {
            val response = remoteDataSource.searchAddress(address, page)
            
            return Result.Success(
                AddressResponse(
                    list = response.addressList.map {
                        mapperApiAddressToAddress(it)
                    },
                    isEnd = response.meta.isEnd
                )
            )
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    suspend fun searchCategoryPlaces(
        code: String,
        longitude: String,
        latitude: String
    ): List<Address> {
        return try {
            val response = remoteDataSource.searchCategoryPlaces(code, longitude, latitude)

            response.apiPlaces.map {
                mapperApiAddressToAddress(it)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getNavigationPath(start: String, end: String): Result<Navigation> {
        return try {
            val response = remoteDataSource.getNavigationPath(start, end)

            if (!response.remoteNavigationRoute.comfort.isNullOrEmpty()) {
                Result.Success(
                    mapperApiRouteToNavigation(response.remoteNavigationRoute.comfort)
                )
            } else {
                Log.e("Navigation", "response : $response")
                Result.Error(RuntimeException("Unknown Error :: Response data is null"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    fun getAllAddress(): Flow<Result<List<Address>>> {
        return localDataSource.getAllAddress().map {
            Result.Success(mapperAddressEntityListToAddressList(it))
        }
    }

    fun getAddress(): Flow<Result<Address?>> {
        return localDataSource.getAddress().map { entity ->
            Result.Success(entity?.let { mapperUserAddressEntityToAddress(it) })
        }
    }

    suspend fun updateCurrentAddressUnselected(id: String) {
        localDataSource.updateCurrentAddressUnselected(id)
    }

    suspend fun insertAddress(address: Address) {
        localDataSource.insertAddress(mapperAddressToUserAddressEntity(address))
    }

    suspend fun deleteAddress(address: Address) {
        val newAddress = mapperAddressToUserAddressEntity(address)
        newAddress.selected = true
        localDataSource.deleteAddress(newAddress)
    }
}
