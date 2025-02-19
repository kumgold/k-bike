package com.goldcompany.koreabike.domain.usecase

import com.goldcompany.koreabike.domain.model.Result
import com.goldcompany.koreabike.domain.model.address.AddressResponse
import com.goldcompany.koreabike.domain.model.succeeded
import com.goldcompany.koreabike.domain.repository.KBikeRepository
import javax.inject.Inject

class SearchAddressUseCase @Inject constructor(private val repository: KBikeRepository) {
    suspend operator fun invoke(address: String, page: Int): Result<AddressResponse> {
        val result = repository.searchAddress(address, page)

        return if (result.succeeded) {
            result
        } else {
            val e = (result as Result.Error)
            Result.Error(e.exception)
        }
    }
}