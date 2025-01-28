package com.example.ucp3.ui.viewmodel.Proyek

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp3.model.Proyek
import com.example.ucp3.repository.ProyekRepository
import kotlinx.coroutines.launch

class ProyekInsertViewModel (private val pryk: ProyekRepository): ViewModel(){
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertPrykState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertPryk() {
        viewModelScope.launch {
            try {
                pryk.insertProyek(uiState.insertUiEvent.toPryk())
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val idProyek: Int = 0,
    val namaProyek: String = "",
    val deskripsiProyek: String = "",
    val tanggalMulai: String = "",
    val tanggalBerakhir: String = "",
    val statusProyek: String = ""
)

fun InsertUiEvent.toPryk(): Proyek = Proyek(
    idProyek = idProyek,
    namaProyek = namaProyek,
    deskripsiProyek = deskripsiProyek,
    tanggalMulai = tanggalMulai,
    tanggalBerakhir = tanggalBerakhir,
    statusProyek = statusProyek
)

fun Proyek.toUiStatePryk(): InsertUiState = InsertUiState(
    insertUiEvent = tolnsertUiEvent()
)

fun Proyek.tolnsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idProyek = idProyek,
    namaProyek = namaProyek,
    deskripsiProyek = deskripsiProyek,
    tanggalMulai = tanggalMulai,
    tanggalBerakhir = tanggalBerakhir,
    statusProyek = statusProyek
)