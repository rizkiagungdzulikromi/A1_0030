package com.example.ucp3.ui.viewmodel.AnggotaTim


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp3.model.Tim
import com.example.ucp3.repository.AnggotaTimRepository
import com.example.ucp3.repository.TimRepository
import com.example.ucp3.ui.view.AnggotaTim.DestinasiAnggotaUpdate
import kotlinx.coroutines.launch

class AnggotaUpdateViewModel (
    savedStateHandle: SavedStateHandle,
    private val anggt: AnggotaTimRepository,
    private val tim: TimRepository
): ViewModel(){
    var updateAnggotaUiState by mutableStateOf(InsertAnggotaUiState())
        private set

    var timList by mutableStateOf(listOf<Tim>())
        private set

    private val _idAnggota: Int = checkNotNull(savedStateHandle[DestinasiAnggotaUpdate.IDANGGOTA])

    init {
        viewModelScope.launch {
            updateAnggotaUiState = anggt.getAnggotaTimById(_idAnggota)
                .toUiStateAnggt()
        }
    }

    fun updateInsertAnggtState(insertAnggotaUiEvent: InsertAnggotaUiEvent){
        updateAnggotaUiState = InsertAnggotaUiState(insertAnggotaUiEvent = insertAnggotaUiEvent)
    }

    suspend fun updateAnggt(){
        viewModelScope.launch {
            try {
                anggt.updateAnggotaTim(_idAnggota, updateAnggotaUiState.insertAnggotaUiEvent.toAnggt())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}