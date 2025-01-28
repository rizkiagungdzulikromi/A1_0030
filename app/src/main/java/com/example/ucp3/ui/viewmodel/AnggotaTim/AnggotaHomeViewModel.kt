package com.example.ucp3.ui.viewmodel.AnggotaTim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.ucp3.model.AnggotaTim
import com.example.ucp3.repository.AnggotaTimRepository
import kotlinx.coroutines.launch
import okio.IOException


sealed class HomeAnggotaUiState {
    data class Success(val anggotaTim: List<AnggotaTim>) : HomeAnggotaUiState()
    object Error : HomeAnggotaUiState()
    object Loading : HomeAnggotaUiState()
}


class AnggotaHomeViewModel (private val anggt: AnggotaTimRepository): ViewModel() {
    var anggtUiState: HomeAnggotaUiState by mutableStateOf(HomeAnggotaUiState.Loading)
        private set

    init {
        getAnggt()
    }

    fun getAnggt() {
        viewModelScope.launch {
            anggtUiState = HomeAnggotaUiState.Loading
            anggtUiState = try {
                HomeAnggotaUiState.Success(anggt.getAnggotaTim().data)
            } catch (e: IOException) {
                HomeAnggotaUiState.Error
            } catch (e: HttpException) {
                HomeAnggotaUiState.Error
            }
        }
    }

    fun deleteAnggt(idAnggota: Int) {
        viewModelScope.launch {
            try {
                anggt.deleteAnggotaTim(idAnggota)
            } catch (e: IOException) {
                HomeAnggotaUiState.Error
            } catch (e: HttpException) {
                HomeAnggotaUiState.Error
            }
        }
    }
}