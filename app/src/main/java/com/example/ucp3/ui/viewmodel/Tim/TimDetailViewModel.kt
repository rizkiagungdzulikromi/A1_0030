package com.example.ucp3.ui.viewmodel.Tim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp3.model.Tim
import com.example.ucp3.repository.TimRepository
import com.example.ucp3.ui.view.Tim.DestinasiTimDetail
import kotlinx.coroutines.launch

sealed class DetailTimUiState {
    data class Success(val tim: Tim) : DetailTimUiState()
    object Error : DetailTimUiState()
    object Loading : DetailTimUiState()
}

class TimDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val tim: TimRepository
) : ViewModel() {

    var timDetailState: DetailTimUiState by mutableStateOf(DetailTimUiState.Loading)
        private set

    private val _idTim: String = checkNotNull(savedStateHandle[DestinasiTimDetail.IDTIM])

    init {
        getTimById()
    }

    fun getTimById() {
        viewModelScope.launch {
            timDetailState = DetailTimUiState.Loading
            timDetailState = try {
                val tim = tim.getTimById(_idTim.toInt())
                DetailTimUiState.Success(tim)
            } catch (e: Exception) {
                DetailTimUiState.Error
            } catch (e: Exception) {
                DetailTimUiState.Error
            }
        }
    }
}