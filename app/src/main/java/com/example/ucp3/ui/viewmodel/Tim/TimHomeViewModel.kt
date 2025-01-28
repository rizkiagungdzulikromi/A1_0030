package com.example.ucp3.ui.viewmodel.Tim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.ucp3.model.Tim
import com.example.ucp3.repository.TimRepository
import kotlinx.coroutines.launch
import okio.IOException


sealed class HomeTimUiState {
    data class Success(val tim: List<Tim>) : HomeTimUiState()
    object Error : HomeTimUiState()
    object Loading : HomeTimUiState()
}


class TimHomeViewModel (private val tm: TimRepository): ViewModel() {
    var tmUiState: HomeTimUiState by mutableStateOf(HomeTimUiState.Loading)
        private set

    init {
        getTm()
    }

    fun getTm() {
        viewModelScope.launch {
            tmUiState = HomeTimUiState.Loading
            tmUiState = try {
                HomeTimUiState.Success(tm.getAllTim().data)
            } catch (e: IOException) {
                HomeTimUiState.Error
            } catch (e: HttpException) {
                HomeTimUiState.Error
            }
        }
    }

    fun deleteTm(idTim: Int) {
        viewModelScope.launch {
            try {
                tm.deleteTim(idTim)
            } catch (e: IOException) {
                HomeTimUiState.Error
            } catch (e: HttpException) {
                HomeTimUiState.Error
            }
        }
    }
}