package com.goldcompany.apps.koreabike.ui.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldcompany.apps.koreabike.R
import com.goldcompany.koreabike.domain.model.address.Address
import com.goldcompany.koreabike.domain.model.navigation.Navigation
import com.goldcompany.koreabike.domain.usecase.GetNavigationPathUseCase
import com.goldcompany.koreabike.domain.usecase.SearchAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NavAddress(
    val addressName: String,
    val coordinate: String
)

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val searchAddressUseCase: SearchAddressUseCase,
    private val getNavigationPathUseCase: GetNavigationPathUseCase
) : ViewModel() {

    private val _isStart = MutableLiveData<Boolean>()

    fun setIsStart(isStart: Boolean) {
        _isStart.value = isStart
    }

    private val _startAddress = MutableLiveData<NavAddress>()
    val startAddress: LiveData<NavAddress> = _startAddress

    private val _endAddress = MutableLiveData<NavAddress>()
    val endAddress: LiveData<NavAddress> = _endAddress

    private val _addressList = MutableLiveData<List<Address>>()
    val addressList: LiveData<List<Address>> = _addressList

    private val _navigation = MutableLiveData<Navigation>()
    val navigation: LiveData<Navigation> = _navigation

    private val _resultMessage = MutableLiveData<Int>()
    val resultMessage: LiveData<Int> = _resultMessage

    fun setNavAddress(address: NavAddress) {
        if (_isStart.value == true) _startAddress.value = address
        else _endAddress.value = address
    }

    fun searchAddress(address: String, page: Int) {
        viewModelScope.launch {
            val response = searchAddressUseCase(address, page)
//            _addressList.postValue(response)
        }
    }

    fun getNavigationPath() {
        viewModelScope.launch {
            val start = _startAddress.value?.coordinate ?: ""
            val end = _endAddress.value?.coordinate ?: ""
            val result = getNavigationPathUseCase(start, end)
            _navigation.postValue(result)
        }
    }

    fun isAddressCorrect(): Boolean {
        val startCoordinate = startAddress.value?.coordinate ?: ""
        val endCoordinate = endAddress.value?.coordinate ?: ""

        if (startCoordinate.isEmpty() || endCoordinate.isEmpty()) {
            _resultMessage.postValue(R.string.error_code)
            return false
        } else if (startCoordinate == endCoordinate) {
            _resultMessage.postValue(R.string.error_code)
            return false
        }
        return true
    }
}