package com.goldcompany.apps.koreabike.ui.bike_map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.util.Async
import com.goldcompany.apps.koreabike.util.LoadingState
import com.goldcompany.koreabike.domain.model.Result
import com.goldcompany.koreabike.domain.model.address.Address
import com.goldcompany.koreabike.domain.usecase.GetCurrentAddressUseCase
import com.goldcompany.koreabike.domain.usecase.SearchAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BikeMapUiState(
    val isLoading: LoadingState = LoadingState.INIT,
    val address: Address? = null,
)

data class BikeMapBottomSheetUiState(
    val isLoading: Boolean = false,
    val message: Int? = null,
    val currentPlace: Address? = null,
    val searchUrl: String? = null
)

private const val SEARCH_URL = "https://search.naver.com/search.naver?query="

@HiltViewModel
class BikeMapViewModel @Inject constructor(
    private val searchAddressUseCase: SearchAddressUseCase,
    private val getCurrentAddressUseCase: GetCurrentAddressUseCase
) : ViewModel() {
    private val _message: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _addressAsync = getCurrentAddressUseCase().map {
        getCurrentAddress(it)
    }.map { Async.Success(it) }
    .onStart<Async<Address?>> { emit(Async.Loading) }

    val uiState: StateFlow<BikeMapUiState> = combine(
        _message, _addressAsync
    ) { _, addressAsync ->
        when (addressAsync) {
            Async.Loading -> {
                BikeMapUiState(isLoading = LoadingState.LOADING)
            }
            is Async.Success -> {
                BikeMapUiState(
                    isLoading = LoadingState.DONE,
                    address = addressAsync.data
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(2000),
        initialValue = BikeMapUiState(isLoading = LoadingState.INIT)
    )

    private fun getCurrentAddress(address: Result<Address?>): Address? {
        return if (address is Result.Success) {
            address.data
        } else {
            null
        }
    }

    private val _bottomSheetUiState = MutableStateFlow(BikeMapBottomSheetUiState())
    val bottomSheetUiState: StateFlow<BikeMapBottomSheetUiState> = _bottomSheetUiState

    fun searchPlace(place: String) {
        _bottomSheetUiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            delay(500)
            val response = searchAddressUseCase(
                address = place,
                page = 1
            )

            if (response is Result.Success) {
                val data = response.data.list
                if (data.isNotEmpty()) {
                    _bottomSheetUiState.update {
                        it.copy(
                            isLoading = false,
                            currentPlace = data[0],
                            searchUrl = null
                        )
                    }
                } else {
                    _bottomSheetUiState.update {
                        it.copy(
                            isLoading = false,
                            currentPlace = null,
                            searchUrl = SEARCH_URL + place
                        )
                    }
                }
            } else {
                _bottomSheetUiState.update {
                    it.copy(
                        isLoading = false,
                        message = R.string.error_code,
                        searchUrl = null
                    )
                }
            }
        }
    }
}