package com.goldcompany.koreabike.domain.usecase

import com.goldcompany.koreabike.domain.model.address.Address
import com.goldcompany.koreabike.domain.repository.KBikeRepository
import javax.inject.Inject

class DeleteAddressUseCase @Inject constructor(private val repository: KBikeRepository) {
    suspend operator fun invoke(address: Address) {
        repository.deleteAddress(address)
    }
}