package com.goldcompany.apps.koreabike.ui.historyplace

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.util.Async
import com.goldcompany.koreabike.domain.model.Result
import com.goldcompany.koreabike.domain.model.address.Address
import com.goldcompany.koreabike.domain.model.succeeded
import com.goldcompany.koreabike.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HistoryPlaceUiState(
    val isLoading: Boolean = true,
    val message: Int? = null,
    val items: List<Address> = emptyList()
)

@HiltViewModel
class HistoryPlaceViewModel @Inject constructor(
    private val getCurrentAddressUseCase: GetCurrentAddressUseCase,
    private val getAllHistoryAddressUseCase: GetAllHistoryAddressUseCase,
    private val updateCurrentAddressUnselectedUseCase: UpdateCurrentAddressUnselectedUseCase,
    private val insertAddressUseCase: InsertAddressUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase
) : ViewModel() {

    private val _message: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _itemsAsync = getAllHistoryAddressUseCase().map {
            getAddressList(it)
        }.map { Async.Success(it) }
        .onStart<Async<List<Address>>> { emit(Async.Loading) }

    val uiState: StateFlow<HistoryPlaceUiState> = combine(
        _message, _itemsAsync
    ) { message, itemsAsync ->
        when (itemsAsync) {
            Async.Loading -> {
                HistoryPlaceUiState(isLoading = true)
            }
            is Async.Success -> {
                HistoryPlaceUiState(
                    isLoading = false,
                    message = message,
                    items = itemsAsync.data
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = HistoryPlaceUiState(isLoading = true)
    )

    init {
        getCurrentAddress()
    }

    private val currentAddress = MutableLiveData<Address?>(null)

    private fun getCurrentAddress() {
        viewModelScope.launch {
            getCurrentAddressUseCase().collectLatest { address ->
                if (address is Result.Success) {
                    currentAddress.value = address.data
                }
            }
        }
    }

    private fun getAddressList(address: Result<List<Address>>): List<Address> {
        return if (address.succeeded && address is Result.Success) {
            address.data
        } else {
            setSnackBarMessage(R.string.error_code)
            emptyList()
        }
    }

    private fun setSnackBarMessage(@StringRes message: Int) {
        _message.value = message
    }

    fun setCurrentAddress(newAddress: Address) {
        viewModelScope.launch {
            if (currentAddress.value != null) {
                updateCurrentAddressUnselectedUseCase(currentAddress.value!!.id)
            }
            insertAddressUseCase(newAddress)
        }
    }

    fun deleteAddress(address: Address) {
        viewModelScope.launch {
            deleteAddressUseCase(address)
        }
    }
}