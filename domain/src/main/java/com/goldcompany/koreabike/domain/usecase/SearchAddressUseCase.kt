package com.goldcompany.koreabike.domain.usecase

import com.goldcompany.koreabike.domain.model.Result
import com.goldcompany.koreabike.domain.model.address.AddressResponse
import com.goldcompany.koreabike.domain.repository.KBikeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchAddressUseCase @Inject constructor(private val repository: KBikeRepository) {
    suspend operator fun invoke(address: String, page: Int):
            Result<AddressResponse> = withContext(Dispatchers.IO) {
        return@withContext repository.searchAddress(address, page)
    }
}