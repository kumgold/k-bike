package com.goldcompany.apps.koreabike.ui.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldcompany.apps.koreabike.util.LoadingState
import com.goldcompany.koreabike.domain.model.Result
import com.goldcompany.koreabike.domain.model.address.Address
import com.goldcompany.koreabike.domain.usecase.GetNavigationPathUseCase
import com.goldcompany.koreabike.domain.usecase.SearchAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NavAddress(
    val addressName: String = "",
    val coordinate: String = ""
)

data class NavigationUiState(
    val loadingState: LoadingState = LoadingState.INIT,
    val addresses: List<Address> = emptyList(),
    val page: Int = 1,
    val isEnd: Boolean = false
)

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val searchAddressUseCase: SearchAddressUseCase,
    private val getNavigationPathUseCase: GetNavigationPathUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NavigationUiState())
    val uiState: StateFlow<NavigationUiState> = _uiState.asStateFlow()

    private val _searchStartAddress: MutableState<String> = mutableStateOf(value = "")
    val searchStartAddress: State<String> = _searchStartAddress

    fun setSearchStartAddress(name: String) {
        _searchStartAddress.value = name
    }

    private val _searchEndAddress: MutableState<String> = mutableStateOf(value = "")
    val searchEndAddress: State<String> = _searchEndAddress

    fun setSearchEndAddress(name: String) {
        _searchEndAddress.value = name
    }

    private val _startAddress: MutableState<NavAddress> = mutableStateOf(
        value = NavAddress()
    )
    val startAddress: State<NavAddress> = _startAddress

    private val _endAddress: MutableState<NavAddress> = mutableStateOf(
        value = NavAddress()
    )
    val endAddress: State<NavAddress> = _endAddress

    private val _isStart: MutableState<Boolean> = mutableStateOf(value = true)

    fun setIsStartAddressFlag(isStart: Boolean) {
        _isStart.value = isStart
    }

//    private val _navigation = MutableLiveData<Navigation>()
//    val navigation: LiveData<Navigation> = _navigation

//    private val _resultMessage = MutableLiveData<Int>()
//    val resultMessage: LiveData<Int> = _resultMessage

    fun setNavAddress(address: Address) {
        val navAddress = NavAddress(
            addressName = address.addressName,
            coordinate = "${address.y} ${address.x}"
        )

        if (_isStart.value) {
            _searchStartAddress.value = address.placeName
            _startAddress.value = navAddress
        } else {
            _searchEndAddress.value = address.placeName
            _endAddress.value = navAddress
        }
    }

    fun searchAddress(address: String, page: Int = 1) {
        loading()

        viewModelScope.launch {
            when (val response = searchAddressUseCase(address, page)) {
                is Result.Success -> {
                    val list = response.data.list

                    _uiState.update {
                        it.copy(
                            loadingState = LoadingState.DONE,
                            addresses = list
                        )
                    }
                }
                else -> {
                    _uiState.update {
                        it.copy(
                            loadingState = LoadingState.ERROR,
                            addresses = emptyList(),
                            page = 0,
                            isEnd = false
                        )
                    }
                }
            }
        }
    }

    private fun loading() {
        _uiState.update {
            it.copy(
                loadingState = LoadingState.LOADING
            )
        }
    }

//    fun getNavigationPath() {
//        viewModelScope.launch {
//            val start = _startAddress.value?.coordinate ?: ""
//            val end = _endAddress.value?.coordinate ?: ""
//            val result = getNavigationPathUseCase(start, end)
//            _navigation.postValue(result)
//        }
//    }
//
//    fun isAddressCorrect(): Boolean {
//        val startCoordinate = startAddress.value?.coordinate ?: ""
//        val endCoordinate = endAddress.value?.coordinate ?: ""
//
//        if (startCoordinate.isEmpty() || endCoordinate.isEmpty()) {
//            _resultMessage.postValue(R.string.error_code)
//            return false
//        } else if (startCoordinate == endCoordinate) {
//            _resultMessage.postValue(R.string.error_code)
//            return false
//        }
//        return true
//    }
}