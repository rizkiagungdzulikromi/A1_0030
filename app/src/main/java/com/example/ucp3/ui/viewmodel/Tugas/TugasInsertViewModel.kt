package com.example.ucp3.ui.viewmodel.Tugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp3.model.Proyek
import com.example.ucp3.model.Tim
import com.example.ucp3.model.Tugas
import com.example.ucp3.repository.ProyekRepository
import com.example.ucp3.repository.TimRepository
import com.example.ucp3.repository.TugasRepository
import kotlinx.coroutines.launch

class TugasInsertViewModel (
    private val tugas: TugasRepository,
    private val tim: TimRepository,
    private val pryk: ProyekRepository,

    ): ViewModel(){

    var timList by mutableStateOf(listOf<Tim>())
        private set

    var proyekList by mutableStateOf(listOf<Proyek>())
        private set

    var uiState by mutableStateOf(InsertTugasUiState())
        private set

    fun updateInsertTugasState(insertTugasUiEvent: InsertTugasUiEvent) {
        uiState = InsertTugasUiState(insertTugasUiEvent = insertTugasUiEvent)
    }

    init {
        loadData()
    }

    suspend fun insertTugas() {
        viewModelScope.launch {
            try {
                tugas.insertTugas(uiState.insertTugasUiEvent.toTugas())
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun loadData(){
        viewModelScope.launch {
            try {
                timList = tim.getAllTim().data
                proyekList = pryk.getAllProyek().data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


data class InsertTugasUiState(
    val insertTugasUiEvent: InsertTugasUiEvent = InsertTugasUiEvent()
)

data class InsertTugasUiEvent(
    val idTugas: Int = 0,
    val idProyek: Int = 0,
    val idTim: Int = 0,
    val namaTugas: String = "",
    val deskripsiTugas: String = "",
    val prioritas: String = "",
    val statusTugas: String = ""


    )

fun InsertTugasUiEvent.toTugas(): Tugas = Tugas(
    idTugas = idTugas,
    idProyek = idProyek,
    idTim =idTim,
    namaTugas = namaTugas,
    deskripsiTugas = deskripsiTugas,
    prioritas = prioritas,
    statusTugas = statusTugas
)

fun Tugas.toUiStateTgs(): InsertTugasUiState = InsertTugasUiState(
    insertTugasUiEvent = tolnsertTugasUiEvent()
)

fun Tugas.tolnsertTugasUiEvent(): InsertTugasUiEvent = InsertTugasUiEvent(
    idTugas = idTugas,
    idProyek = idProyek,
    idTim = idTim,
    namaTugas = namaTugas,
    deskripsiTugas = deskripsiTugas,
    prioritas = prioritas,
    statusTugas = statusTugas
)