package com.goldcompany.koreabike.domain.usecase

import com.goldcompany.koreabike.domain.repository.KBikeRepository
import javax.inject.Inject

class UpdateCurrentAddressUnselectedUseCase @Inject constructor(private val repository: KBikeRepository) {
    suspend operator fun invoke(id: String) {
        repository.updateCurrentAddressUnselected(id)
    }
}