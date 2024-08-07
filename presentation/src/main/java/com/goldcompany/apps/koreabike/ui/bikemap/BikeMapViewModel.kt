package com.goldcompany.apps.koreabike.ui.bikemap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.util.Async
import com.goldcompany.apps.koreabike.util.UIState
import com.goldcompany.koreabike.domain.model.Result
import com.goldcompany.koreabike.domain.model.address.Address
import com.goldcompany.koreabike.domain.usecase.GetCurrentAddressUseCase
import com.goldcompany.koreabike.domain.usecase.SearchAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BikeMapUiState(
    val isLoading: UIState = UIState.INIT,
    val address: Address? = null,
)

data class BikeMapBottomSheetUiState(
    val isLoading: Boolean = false,
    val message: Int? = null,
    val currentPlace: Address? = null,
    val searchUrl: String? = null
)

@HiltViewModel
class BikeMapViewModel @Inject constructor(
    private val searchAddressUseCase: SearchAddressUseCase,
    getCurrentAddressUseCase: GetCurrentAddressUseCase
) : ViewModel() {
    companion object {
        private const val NAVER_SEARCH_URL = "https://search.naver.com/search.naver?query="
    }

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
                BikeMapUiState(isLoading = UIState.LOADING)
            }
            is Async.Success -> {
                BikeMapUiState(
                    isLoading = UIState.DONE,
                    address = addressAsync.data
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(2000),
        initialValue = BikeMapUiState(isLoading = UIState.INIT)
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
                            searchUrl = NAVER_SEARCH_URL + place
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