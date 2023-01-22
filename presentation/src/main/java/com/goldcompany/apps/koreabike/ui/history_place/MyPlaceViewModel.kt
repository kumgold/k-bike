package com.goldcompany.apps.koreabike.ui.history_place

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

    private val _isLoading = MutableStateFlow(false)
    private val _message: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _itemsAsync = getAllHistoryAddressUseCase().map {
            getAddressList(it)
        }.map { Async.Success(it) }
        .onStart<Async<List<Address>>> { emit(Async.Loading) }

    val uiState: StateFlow<HistoryPlaceUiState> = combine(
        _isLoading, _message, _itemsAsync
    ) { isLoading, message, itemsAsync ->
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

    private fun getAddressList(address: Result<List<Address>>): List<Address> {
        return if (address.succeeded && address is Result.Success) {
            address.data
        } else {
            setSnackBarMessage(R.string.error_code)
            emptyList()
        }
    }

    private fun setSnackBarMessage(message: Int) {
        _message.value = message
    }

    fun shownMessage() {
        _message.value = null
    }

    fun setCurrentAddress(newAddress: Address) {
//        viewModelScope.launch {
//            val address = getCurrentAddressUseCase()
//            if (address is Result.Success) {
//                address.data?.let { updateCurrentAddressUnselectedUseCase(it.id) }
//            }
//            insertAddressUseCase(newAddress)
//        }
    }

    fun deleteAddress(address: Address) {
        _isLoading.value = true
        viewModelScope.launch {
            deleteAddressUseCase(address)
            _isLoading.value = false
        }
    }
}