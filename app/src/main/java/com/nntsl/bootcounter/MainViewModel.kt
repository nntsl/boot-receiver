package com.nntsl.bootcounter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nntsl.bootcounter.core.domain.model.BootEvent
import com.nntsl.bootcounter.core.domain.usecase.GetBootEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getSavedBootEventsUseCase: GetBootEventsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LatestBootEventsUiState.Success(emptyList()))
    val uiState: StateFlow<LatestBootEventsUiState> = _uiState

    init {
        viewModelScope.launch {
            getSavedBootEventsUseCase()
                .collect { events ->
                    _uiState.value = LatestBootEventsUiState.Success(events)
                }
        }
    }
}

sealed interface LatestBootEventsUiState {
    data class Success(val events: List<BootEvent>) : LatestBootEventsUiState
    data class Error(val exception: Throwable) : LatestBootEventsUiState
}
