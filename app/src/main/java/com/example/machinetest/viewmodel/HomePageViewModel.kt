package com.example.machinetest.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.machinetest.api.RESTResult
import com.example.machinetest.model.HomePageContent
import com.example.machinetest.repository.HomePageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomePageViewModel(private val repository: HomePageRepository) : ViewModel() {

    val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState


    init {
        _uiState.value = UiState.Loading
        getHomePageData()
    }

    private fun getHomePageData() {
        viewModelScope.launch(Dispatchers.IO) {
           val response = repository.getHomePageData()
            when (val response =
                repository.getHomePageData()) {
                is RESTResult.Success -> {
                    response.value?.let {
                        _uiState.value = UiState.Loaded(value = response.value)

                    }

                }
                else -> Unit
            }
            Log.e("Response>>", response.toString())
        }
    }
}


class HomePageViewModelFactory(private val repository: HomePageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomePageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomePageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

sealed class UiState {
    object Empty : UiState()
    object Loading : UiState()
    data class Loaded(val value: List<HomePageContent>?) : UiState()
    data class Error(val message: String) : UiState()
}

