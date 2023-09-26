package com.goldcompany.apps.koreabike.ui.navigationdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldcompany.koreabike.domain.model.Result
import com.goldcompany.koreabike.domain.usecase.GetNavigationPathUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationDetailViewModel @Inject constructor(
    private val getNavigationPathUseCase: GetNavigationPathUseCase
) : ViewModel() {
    fun getNavigationPath(
        startCoordinate: String,
        endCoordinate: String
    ) {
        viewModelScope.launch {
            val result = getNavigationPathUseCase(startCoordinate, endCoordinate)

            if (result is Result.Success) {
                Log.d("navigation", "${result.data}")
            }
        }
    }
}