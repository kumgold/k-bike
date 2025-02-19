package com.goldcompany.koreabike.data.repository.local

import com.goldcompany.koreabike.data.db.AddressDAO
import com.goldcompany.koreabike.data.db.AddressEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KBikeLocalDataSource @Inject constructor(private val addressDAO: AddressDAO) {

    fun getAllAddress(): Flow<List<AddressEntity>> {
        return addressDAO.getAll()
    }

    fun getAddress(): Flow<AddressEntity?> {
        return addressDAO.getAddress()
    }

    suspend fun updateCurrentAddressUnselected(id: String) {
        addressDAO.updateCurrentAddressUnselected(id)
    }

    suspend fun insertAddress(address: AddressEntity) {
        addressDAO.insert(address)
    }

    suspend fun deleteAddress(address: AddressEntity) {
        addressDAO.delete(address)
    }

}