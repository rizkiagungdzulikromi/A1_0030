package com.example.ucp3.ui.viewmodel.Tim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp3.repository.TimRepository
import com.example.ucp3.ui.view.Tim.DestinasiTimUpdate
import kotlinx.coroutines.launch

class TimUpdateViewModel (
    savedStateHandle: SavedStateHandle,
    private val tim: TimRepository
): ViewModel(){
    var updateTimUiState by mutableStateOf(InsertTimUiState())
        private set

    private val _idTim: Int = checkNotNull(savedStateHandle[DestinasiTimUpdate.IDTIM])

    init {
        viewModelScope.launch {
            updateTimUiState = tim.getTimById(_idTim)
                .toUiStateTim()
        }
    }

    fun updateInsertTimState(insertTimUiEvent: InsertTimUiEvent){
        updateTimUiState = InsertTimUiState(insertTimUiEvent = insertTimUiEvent)
    }

    suspend fun updateTim(){
        viewModelScope.launch {
            try {
                tim.updateTim(_idTim, updateTimUiState.insertTimUiEvent.toTim())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}