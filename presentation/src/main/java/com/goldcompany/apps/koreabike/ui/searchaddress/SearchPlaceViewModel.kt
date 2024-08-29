package com.goldcompany.apps.koreabike.ui.searchaddress

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldcompany.apps.koreabike.util.UIState
import com.goldcompany.koreabike.domain.model.Result
import com.goldcompany.koreabike.domain.model.address.Address
import com.goldcompany.koreabike.domain.usecase.GetCurrentAddressUseCase
import com.goldcompany.koreabike.domain.usecase.InsertAddressUseCase
import com.goldcompany.koreabike.domain.usecase.SearchAddressUseCase
import com.goldcompany.koreabike.domain.usecase.UpdateCurrentAddressUnselectedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchAddressUiState(
    val uiState: UIState = UIState.INIT,
    val addresses: List<Address> = emptyList(),
    val page: Int = 1,
    val currentPlace: String = "",
    val isEnd: Boolean = false,
    val message: Int? = null
)

@HiltViewModel
class SearchAddressViewModel @Inject constructor(
    private val searchAddressUseCase: SearchAddressUseCase,
    private val getCurrentAddressUseCase: GetCurrentAddressUseCase,
    private val updateCurrentAddressUnselectedUseCase: UpdateCurrentAddressUnselectedUseCase,
    private val insertAddressUseCase: InsertAddressUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchAddressUiState())
    val uiState: StateFlow<SearchAddressUiState> = _uiState.asStateFlow()

    private val _currentAddress = MutableStateFlow<Address?>(null)

    init {
        viewModelScope.launch {
            getCurrentAddressUseCase().collectLatest {
                if (it is Result.Success) {
                    _currentAddress.value = it.data
                }
            }
        }
    }

    private val _searchAddressState: MutableState<String> =
        mutableStateOf(value = "")
    val searchAddressState = _searchAddressState

    fun setSearchAddressState(place: String) {
        _searchAddressState.value = place
    }

    fun setSearchAppBarStateClose() {
        if (_searchAddressState.value.isEmpty()) {
            _uiState.update {
                it.copy(
                    page = 1,
                    uiState = UIState.INIT,
                    addresses = emptyList()
                )
            }
        } else {
            _searchAddressState.value = ""
        }
    }

    fun setCurrentAddress(newAddress: Address) {
        viewModelScope.launch {
            _currentAddress.value?.let { updateCurrentAddressUnselectedUseCase(it.id) }
            insertAddressUseCase(newAddress)
        }
    }

    fun searchAddress(place: String? = null) {
        checkCurrentPlace(place)

        viewModelScope.launch {
            val currentPlace = place ?: _uiState.value.currentPlace
            val response = searchAddressUseCase(
                address = currentPlace,
                page = _uiState.value.page + 1
            )

            if (response is Result.Success) {
                val addressList = response.data.list
                _uiState.update {
                    it.copy(
                        uiState = UIState.DONE,
                        addresses = it.addresses + addressList,
                        page = it.page + 1,
                        currentPlace = currentPlace,
                        isEnd = response.data.isEnd
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        uiState = UIState.ERROR,
                        isEnd = false
                    )
                }
            }
        }
    }

    private fun checkCurrentPlace(place: String?) {
        if (place != null && _uiState.value.currentPlace != place) {
            _uiState.update {
                it.copy(
                    uiState = UIState.LOADING,
                    addresses = emptyList(),
                    page = 1,
                    isEnd = false
                )
            }
        } else {
            _uiState.update {
                it.copy(uiState = UIState.LOADING)
            }
        }

        if (_uiState.value.isEnd) {
            _uiState.update {
                it.copy(uiState = UIState.DONE)
            }
            return
        }
    }
}