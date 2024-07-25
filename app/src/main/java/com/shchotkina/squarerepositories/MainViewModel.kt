package com.shchotkina.squarerepositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shchotkina.squarerepositories.model.RepositoryItem
import com.shchotkina.squarerepositories.model.ResponseResult
import com.shchotkina.squarerepositories.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: AppRepository): ViewModel() {
    private val _uiState = MutableStateFlow(ListUiState(isLoading = true))
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    init {
        getSquareRepository()
    }

    fun getSquareRepository() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = repository.getSquareRepositories()
            when (result) {
                is ResponseResult.Success -> _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    repositories = result.data,
                    error = null
                )
                is ResponseResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    repositories = emptyList(),
                    error = result.exception
                )
            }
        }
    }
}

data class ListUiState(
    val isLoading: Boolean = false,
    val repositories: List<RepositoryItem> = emptyList(),
    val error: Throwable? = null
)