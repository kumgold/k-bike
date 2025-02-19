package com.goldcompany.apps.koreabike.ui.historyplace

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.util.Async
import com.goldcompany.koreabike.data.model.address.Address
import com.goldcompany.koreabike.data.repository.KBikeRepository
import com.goldcompany.koreabike.data.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HistoryPlaceUiState(
    val isLoading: Boolean = true,
    val message: Int? = null,
    val items: List<Address> = emptyList()
)

@HiltViewModel
class HistoryPlaceViewModel @Inject constructor(
    private val bikeRepository: KBikeRepository
) : ViewModel() {

    private val _message: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _itemsAsync = bikeRepository.getAllAddress().map {
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
            is Async.Error -> {
                HistoryPlaceUiState(
                    isLoading = false,
                    message = message
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
            bikeRepository.getAddress().collectLatest { address ->
                if (address is Result.Success) {
                    currentAddress.value = address.data
                }
            }
        }
    }

    private fun getAddressList(address: Result<List<Address>>): List<Address> {
        return if (address is Result.Success) {
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
                bikeRepository.updateCurrentAddressUnselected(currentAddress.value!!.id)
            }
            bikeRepository.insertAddress(newAddress)
        }
    }

    fun deleteAddress(address: Address) {
        viewModelScope.launch {
            bikeRepository.deleteAddress(address)
        }
    }
}