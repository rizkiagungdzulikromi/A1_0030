package com.example.ucp3.ui.viewmodel.Proyek

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.ucp3.model.Proyek
import com.example.ucp3.repository.ProyekRepository
import kotlinx.coroutines.launch
import okio.IOException


sealed class HomeUiState {
    data class Success(val proyek: List<Proyek>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}


class ProyekHomeViewModel (private val pryk: ProyekRepository): ViewModel() {
    var prykUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getPryk()
    }

    fun getPryk() {
        viewModelScope.launch {
            prykUiState = HomeUiState.Loading
            prykUiState = try {
                HomeUiState.Success(pryk.getAllProyek().data)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deletePryk(idProyek: Int) {
        viewModelScope.launch {
            try {
                pryk.deleteProyek(idProyek)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}