package com.example.ucp3.ui.viewmodel.Tugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.ucp3.model.Tugas
import com.example.ucp3.repository.TugasRepository
import kotlinx.coroutines.launch
import okio.IOException


sealed class HomeTugasUiState {
    data class Success(val tugas: List<Tugas>) : HomeTugasUiState()
    object Error : HomeTugasUiState()
    object Loading : HomeTugasUiState()
}


class TugasHomeViewModel (private val tgs: TugasRepository): ViewModel() {
    var tgsUiState: HomeTugasUiState by mutableStateOf(HomeTugasUiState.Loading)
        private set

    init {
        getTgs()
    }

    fun getTgs() {
        viewModelScope.launch {
            tgsUiState = HomeTugasUiState.Loading
            tgsUiState = try {
                HomeTugasUiState.Success(tgs.getAllTugas().data)
            } catch (e: IOException) {
                HomeTugasUiState.Error
            } catch (e: HttpException) {
                HomeTugasUiState.Error
            }
        }
    }

    fun deleteTgs(idTugas: Int) {
        viewModelScope.launch {
            try {
                tgs.deleteTugas(idTugas)
            } catch (e: IOException) {
                HomeTugasUiState.Error
            } catch (e: HttpException) {
                HomeTugasUiState.Error
            }
        }
    }
}