package com.example.ucp3.ui.viewmodel.Proyek

import com.example.ucp3.model.Proyek
import com.example.ucp3.repository.ProyekRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp3.ui.view.Proyek.DestinasiDetail
import kotlinx.coroutines.launch

sealed class DetailUiState {
    data class Success(val proyek: Proyek) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class ProyekDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val pryk: ProyekRepository
) : ViewModel() {

    var proyekDetailState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    private val _idProyek: Int = checkNotNull(savedStateHandle[DestinasiDetail.IDPROYEK])

    init {
        getProyekById()
    }

    fun getProyekById() {
        viewModelScope.launch {
            proyekDetailState = DetailUiState.Loading
            proyekDetailState = try {
                val proyek = pryk.getProyekById(_idProyek)
                DetailUiState.Success(proyek)
            } catch (e: Exception) {
                DetailUiState.Error
            } catch (e: Exception) {
                DetailUiState.Error
            }
        }
    }
}