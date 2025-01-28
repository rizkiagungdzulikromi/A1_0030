package com.example.ucp3.ui.viewmodel.Tugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp3.model.Proyek
import com.example.ucp3.model.Tim
import com.example.ucp3.repository.ProyekRepository
import com.example.ucp3.repository.TimRepository
import com.example.ucp3.repository.TugasRepository
import com.example.ucp3.ui.view.Tugas.DestinasiTugasUpdate
import kotlinx.coroutines.launch

class TugasUpdateViewModel (
    savedStateHandle: SavedStateHandle,
    private val tugas: TugasRepository,
    private val tim: TimRepository,
    private val pryk: ProyekRepository
): ViewModel(){
    var updateTugasUiState by mutableStateOf(InsertTugasUiState())
        private set

    var timList by mutableStateOf(listOf<Tim>())
        private set

    var proyekList by mutableStateOf(listOf<Proyek>())
        private set

        private val _idTugas: Int = checkNotNull(savedStateHandle[DestinasiTugasUpdate.IDTUGAS])

    init {
        viewModelScope.launch {
            updateTugasUiState = tugas.getTugasById(_idTugas)
                .toUiStateTgs()
        }
    }

    fun updateInsertTugasState(insertTugasUiEvent: InsertTugasUiEvent){
        updateTugasUiState = InsertTugasUiState(insertTugasUiEvent = insertTugasUiEvent)
    }

    suspend fun updateTim(){
        viewModelScope.launch {
            try {
                tugas.updateTugas(_idTugas, updateTugasUiState.insertTugasUiEvent.toTugas())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}