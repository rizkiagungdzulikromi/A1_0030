package com.example.ucp3.ui.view.AnggotaTim

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp3.model.Tim
import com.example.ucp3.ui.CustomWidget.CoustumeTopAppBar
import com.example.ucp3.ui.PenyediaViewModel
import com.example.ucp3.ui.navigation.DestinasiNavigasi
import com.example.ucp3.ui.viewmodel.AnggotaTim.AnggotaUpdateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiAnggotaUpdate: DestinasiNavigasi {
    override val route = "update_Anggota"
    override val titleRes = "Update Anggota"
    const val IDANGGOTA = "idAnggota"
    val routesWithArg = "$route/{$IDANGGOTA}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnggotaUpdateScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: AnggotaUpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiAnggotaUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding ->
        EntryBody(
            modifier = Modifier.padding(padding),
            insertAnggotaUiState = viewModel.updateAnggotaUiState,
            onAnggotaValueChange = viewModel::updateInsertAnggtState,
            timList = viewModel.timList,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateAnggt()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}