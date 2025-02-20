package com.goldcompany.apps.koreabike.ui.navigationdetail

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldcompany.apps.koreabike.util.UIState
import com.goldcompany.koreabike.data.repository.KBikeRepository
import com.goldcompany.koreabike.data.util.Result
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
data class NavigationDetailUiState(
    val path: List<LatLng> = emptyList(),
    val distance: Int = 0,
    val duration: Int = 0,
    val uiState: UIState = UIState.INIT
)

@HiltViewModel
class NavigationDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val bikeRepository: KBikeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NavigationDetailUiState())
    val uiState: StateFlow<NavigationDetailUiState> = _uiState

    private val startCoordinate: String = checkNotNull(savedStateHandle["startCoordinate"])
    private val endCoordinate: String = checkNotNull(savedStateHandle["endCoordinate"])

    init {
        getNavigationPath()
    }

    private fun getNavigationPath() {
        loading()

        viewModelScope.launch(Dispatchers.IO) {
            val result = bikeRepository.getNavigationPath(startCoordinate, endCoordinate)

            if (result is Result.Success) {
                val list = mutableListOf<LatLng>()
                result.data.trackList[0].path.forEach {
                    list.add(LatLng(it[1], it[0]))
                }

                _uiState.update {
                    it.copy(
                        path = list,
                        distance = result.data.trackList[0].distance,
                        duration = result.data.trackList[0].duration,
                        uiState = UIState.DONE
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        path = emptyList(),
                        uiState = UIState.ERROR
                    )
                }
            }
        }
    }

    private fun loading() {
        _uiState.update {
            it.copy(
                uiState = UIState.LOADING
            )
        }
    }
}
