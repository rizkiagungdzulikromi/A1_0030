package com.example.ucp3.ui.viewmodel.Proyek

import com.example.ucp3.repository.ProyekRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp3.ui.view.Proyek.DestinasiUpdate
import kotlinx.coroutines.launch

class ProyekUpdateViewModel (
    savedStateHandle: SavedStateHandle,
    private val pryk: ProyekRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertUiState())
        private set

    private val _idProyek: Int = checkNotNull(savedStateHandle[DestinasiUpdate.IDPROYEK])

    init {
        viewModelScope.launch {
            updateUiState = pryk.getProyekById(_idProyek)
                .toUiStatePryk()
        }
    }

    fun updateInsertPrykState(insertUiEvent: InsertUiEvent){
        updateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updatePryk(){
        viewModelScope.launch {
            try {
                pryk.updateProyek(_idProyek, updateUiState.insertUiEvent.toPryk())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}