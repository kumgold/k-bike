package com.goldcompany.koreabike.domain.usecase

import com.goldcompany.koreabike.domain.model.address.Address
import com.goldcompany.koreabike.domain.repository.KBikeRepository
import javax.inject.Inject

class SearchCategoryPlacesUseCase @Inject constructor(private val repository: KBikeRepository) {
    suspend operator fun invoke(code: String, longitude: String, latitude: String): List<Address> =
        repository.searchCategoryPlaces(code, longitude, latitude)
}