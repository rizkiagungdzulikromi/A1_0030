package com.example.ucp3.ui.viewmodel.AnggotaTim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp3.model.AnggotaTim
import com.example.ucp3.model.Tim
import com.example.ucp3.repository.AnggotaTimRepository
import com.example.ucp3.repository.TimRepository
import kotlinx.coroutines.launch

class AnggotaInsertViewModel (
    private val anggt: AnggotaTimRepository,
    private val tim: TimRepository,
): ViewModel(){
    var uiState by mutableStateOf(InsertAnggotaUiState())
        private set

    var timList by mutableStateOf(listOf<Tim>())
        private set

    fun updateInsertAnggtState(insertAnggotaUiEvent: InsertAnggotaUiEvent) {
        uiState = InsertAnggotaUiState(insertAnggotaUiEvent = insertAnggotaUiEvent)
    }
    init {
        loadData()
    }

    suspend fun insertAnggt() {
        viewModelScope.launch {
            try {
                anggt.insertAnggotaTim(uiState.insertAnggotaUiEvent.toAnggt())
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

fun loadData(){
        viewModelScope.launch {
            try {
                timList = tim.getAllTim().data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertAnggotaUiState(
    val insertAnggotaUiEvent: InsertAnggotaUiEvent = InsertAnggotaUiEvent()
)

data class InsertAnggotaUiEvent(
    val idAnggota: Int = 0,
    val idTim: Int = 0,
    val namaAnggota: String = "",
    val peran: String = "",

)

fun InsertAnggotaUiEvent.toAnggt(): AnggotaTim = AnggotaTim(
    idAnggota = idAnggota,
    idTim = idTim,
    namaAnggota = namaAnggota,
    peran = peran
)

fun AnggotaTim.toUiStateAnggt(): InsertAnggotaUiState = InsertAnggotaUiState(
    insertAnggotaUiEvent = tolnsertAnggotaUiEvent()
)

fun AnggotaTim.tolnsertAnggotaUiEvent(): InsertAnggotaUiEvent = InsertAnggotaUiEvent(
    idAnggota = idAnggota,
    idTim = idTim,
    namaAnggota = namaAnggota,
    peran = peran
)