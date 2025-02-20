package com.goldcompany.apps.koreabike.ui.searchaddress

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.util.Async
import com.goldcompany.koreabike.data.repository.KBikeRepository
import com.goldcompany.koreabike.data.util.Result
import com.goldcompany.koreabike.data.model.address.Address
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
data class SearchAddressUiState(
    val addressList: List<Address> = emptyList(),
    val isEnd: Boolean = false,
    val isLoading: Boolean = false,
    val message: Int? = null
)

@HiltViewModel
class SearchPlaceViewModel @Inject constructor(
    private val bikeRepository: KBikeRepository
) : ViewModel() {

    private val _currentPlaceName = MutableStateFlow("")
    private val _page = MutableStateFlow(1)
    private val _searchAddressAsync = combine(_currentPlaceName, _page) { place, page ->
        searchAddress(place, page)
    }.map {
        Async.Success(it)
    }.catch<Async<List<Address>>> {
        emit(Async.Error(R.string.error_code))
    }
    private val _isEnd = MutableStateFlow(false)
    private val _isLoading = MutableStateFlow(false)
    private val _userMessage = MutableStateFlow<Int?>(null)

    val uiState = combine(
        _searchAddressAsync,
        _isEnd,
        _isLoading,
        _userMessage
    ) { addressAsync, isEnd, isLoading, message ->
        when (addressAsync) {
            Async.Loading -> {
                SearchAddressUiState(isLoading = true)
            }
            is Async.Success -> {
                SearchAddressUiState(
                    addressList = addressAsync.data,
                    isEnd = isEnd,
                    isLoading = false,
                    message = message
                )
            }
            is Async.Error -> {
                SearchAddressUiState(
                    isLoading = false,
                    message = message
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(2000),
        initialValue = SearchAddressUiState()
    )

    private val _addressList = MutableStateFlow<List<Address>>(emptyList())

    private suspend fun searchAddress(place: String, page: Int): List<Address> = viewModelScope.async(Dispatchers.IO) {
        if (place.isEmpty()) {
            emptyList()
        } else {
            val response = bikeRepository.searchAddress(
                address = place,
                page = page
            )

            if (response is Result.Success) {
                _addressList.value += response.data.list
                _isEnd.update { response.data.isEnd }

                _addressList.value
            } else {
                emptyList()
            }
        }
    }.await()

    fun searchPlace(placeName: String) {
        _addressList.value = emptyList()
        _page.value = 1
        _currentPlaceName.update { placeName }
    }

    fun getNextPage() {
        _page.update {
            _page.value + 1
        }
    }

    fun setCurrentAddress(address: Address) {
        viewModelScope.launch(Dispatchers.IO) {
            bikeRepository.insertAddress(address)
        }
    }
}
