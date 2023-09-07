package com.goldcompany.koreabike.data.repository

import com.goldcompany.koreabike.data.mapper.mapperAddressEntityListToAddressList
import com.goldcompany.koreabike.data.mapper.mapperAddressToUserAddressEntity
import com.goldcompany.koreabike.data.mapper.mapperApiAddressToAddress
import com.goldcompany.koreabike.data.mapper.mapperApiRouteToNavigation
import com.goldcompany.koreabike.data.mapper.mapperUserAddressEntityToAddress
import com.goldcompany.koreabike.data.repository.local.KBikeLocalDataSource
import com.goldcompany.koreabike.data.repository.remote.KBikeRemoteDataSource
import com.goldcompany.koreabike.domain.model.Result
import com.goldcompany.koreabike.domain.model.address.Address
import com.goldcompany.koreabike.domain.model.address.AddressResponse
import com.goldcompany.koreabike.domain.model.navigation.Navigation
import com.goldcompany.koreabike.domain.repository.KBikeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class KBikeRepositoryImpl(
    private val localDataSource: KBikeLocalDataSource,
    private val remoteDataSource: KBikeRemoteDataSource
) : KBikeRepository {
    override suspend fun searchAddress(
        address: String,
        page: Int
    ): Result<AddressResponse> = withContext(Dispatchers.IO) {
        try {
            val response = remoteDataSource.searchAddress(address, page)
            return@withContext Result.Success(
                AddressResponse(
                    list = response.addressList.map {
                        mapperApiAddressToAddress(it)
                    },
                    isEnd = response.meta.isEnd
                )
            )
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override suspend fun searchCategoryPlaces(
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

    override suspend fun getNavigationPath(start: String, end: String): Navigation {
        val response = remoteDataSource.getNavigationPath(start, end).apiNavigationRoute
        return mapperApiRouteToNavigation(response.comfort)
    }

    override fun getAllAddress(): Flow<Result<List<Address>>> {
        return localDataSource.getAllAddress().map {
            Result.Success(mapperAddressEntityListToAddressList(it))
        }
    }

    override fun getAddress(): Flow<Result<Address?>> {
        return localDataSource.getAddress().map { entity ->
            Result.Success(entity?.let { it -> mapperUserAddressEntityToAddress(it) })
        }
    }

    override suspend fun updateCurrentAddressUnselected(id: String) {
        localDataSource.updateCurrentAddressUnselected(id)
    }

    override suspend fun insertAddress(address: Address) {
        localDataSource.insertAddress(mapperAddressToUserAddressEntity(address))
    }

    override suspend fun deleteAddress(address: Address) {
        val newAddress = mapperAddressToUserAddressEntity(address)
        newAddress.selected = true
        localDataSource.deleteAddress(newAddress)
    }
}