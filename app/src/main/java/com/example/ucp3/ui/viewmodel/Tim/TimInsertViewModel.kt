package com.example.ucp3.ui.viewmodel.Tim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp3.model.Tim
import com.example.ucp3.repository.TimRepository
import kotlinx.coroutines.launch

class TimInsertViewModel (private val tim: TimRepository): ViewModel(){
    var uiState by mutableStateOf(InsertTimUiState())
        private set

    fun updateInsertTimState(insertTimUiEvent: InsertTimUiEvent) {
        uiState = InsertTimUiState(insertTimUiEvent = insertTimUiEvent)
    }

    suspend fun insertTim() {
        viewModelScope.launch {
            try {
                tim.insertTim(uiState.insertTimUiEvent.toTim())
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertTimUiState(
    val insertTimUiEvent: InsertTimUiEvent = InsertTimUiEvent()
)

data class InsertTimUiEvent(
    val idTim: Int = 0,
    val namaTim: String = "",
    val deskripsiTim: String = "",


    )

fun InsertTimUiEvent.toTim(): Tim = Tim(
    idTim = idTim,
    namaTim = namaTim,
    deskripsiTim = deskripsiTim
)

fun Tim.toUiStateTim(): InsertTimUiState = InsertTimUiState(
    insertTimUiEvent = tolnsertTimUiEvent()
)

fun Tim.tolnsertTimUiEvent(): InsertTimUiEvent = InsertTimUiEvent(
    idTim = idTim,
    namaTim = namaTim,
    deskripsiTim = deskripsiTim
)