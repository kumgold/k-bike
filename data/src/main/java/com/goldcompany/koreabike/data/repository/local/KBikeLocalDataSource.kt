package com.goldcompany.koreabike.data.repository.local

import com.goldcompany.koreabike.data.db.AddressDAO
import com.goldcompany.koreabike.data.db.AddressEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Singleton

@Singleton
interface KBikeLocalDataSource {
    fun getAllAddress(): Flow<List<AddressEntity>>

    fun getAddress(): Flow<AddressEntity?>

    suspend fun updateCurrentAddressUnselected(id: String)

    suspend fun insertAddress(address: AddressEntity)

    suspend fun deleteAddress(address: AddressEntity)
}

class KBikeLocalDataSourceImpl(private val addressDAO: AddressDAO): KBikeLocalDataSource {
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override fun getAllAddress(): Flow<List<AddressEntity>> {
        return addressDAO.getAll()
    }

    override fun getAddress(): Flow<AddressEntity?> {
        return addressDAO.getAddress()
    }

    override suspend fun updateCurrentAddressUnselected(id: String) = withContext(ioDispatcher) {
        addressDAO.updateCurrentAddressUnselected(id)
    }

    override suspend fun insertAddress(address: AddressEntity) {
        addressDAO.insert(address)
    }

    override suspend fun deleteAddress(address: AddressEntity) {
        addressDAO.delete(address)
    }

}