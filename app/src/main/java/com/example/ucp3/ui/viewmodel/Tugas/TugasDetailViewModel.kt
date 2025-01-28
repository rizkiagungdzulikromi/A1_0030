package com.example.ucp3.ui.viewmodel.Tugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp3.model.Tugas
import com.example.ucp3.repository.TugasRepository
import com.example.ucp3.ui.view.Tugas.DestinasiTugasDetail
import kotlinx.coroutines.launch

sealed class DetailTugasUiState {
    data class Success(val tugas: Tugas) : DetailTugasUiState()
    object Error : DetailTugasUiState()
    object Loading : DetailTugasUiState()
}

class TugasDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val tgs: TugasRepository
) : ViewModel() {

    var tugasDetailState: DetailTugasUiState by mutableStateOf(DetailTugasUiState.Loading)
        private set

    private val _idTugas: Int = checkNotNull(savedStateHandle[DestinasiTugasDetail.IDTUGAS])

    init {
        getTugasById()
    }

    fun getTugasById() {
        viewModelScope.launch {
            tugasDetailState = DetailTugasUiState.Loading
            tugasDetailState = try {
                val tim = tgs.getTugasById(_idTugas)
                DetailTugasUiState.Success(tim)
            } catch (e: Exception) {
                DetailTugasUiState.Error
            } catch (e: Exception) {
                DetailTugasUiState.Error
            }
        }
    }
}