package com.example.ucp3.ui.viewmodel.AnggotaTim


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp3.model.AnggotaTim
import com.example.ucp3.repository.AnggotaTimRepository
import com.example.ucp3.ui.view.AnggotaTim.DestinasiAnggotaDetail
import kotlinx.coroutines.launch

sealed class DetailAnggotaUiState {
    data class Success(val anggotaTim: AnggotaTim) : DetailAnggotaUiState()
    object Error : DetailAnggotaUiState()
    object Loading : DetailAnggotaUiState()
}

class AnggotaDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val anggt: AnggotaTimRepository
) : ViewModel() {

    var anggotaDetailState: DetailAnggotaUiState by mutableStateOf(DetailAnggotaUiState.Loading)
        private set

    private val _idAnggota: Int = checkNotNull(savedStateHandle[DestinasiAnggotaDetail.IDANGGOTA])

    init {
        getAnggotaTimbyid()
    }

    fun getAnggotaTimbyid() {
        viewModelScope.launch {
            anggotaDetailState = DetailAnggotaUiState.Loading
            anggotaDetailState = try {
                val anggotaTim = anggt.getAnggotaTimById(_idAnggota)
                DetailAnggotaUiState.Success(anggotaTim)
            } catch (e: Exception) {
                DetailAnggotaUiState.Error
            } catch (e: Exception) {
                DetailAnggotaUiState.Error
            }
        }
    }
}