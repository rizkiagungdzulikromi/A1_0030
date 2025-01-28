package com.example.ucp3.ui.view.Tugas

import com.example.ucp3.ui.CustomWidget.CoustumeTopAppBar
import com.example.ucp3.ui.PenyediaViewModel
import com.example.ucp3.ui.navigation.DestinasiNavigasi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp3.model.Tugas
import com.example.ucp3.ui.viewmodel.Tugas.DetailTugasUiState
import com.example.ucp3.ui.viewmodel.Tugas.TugasDetailViewModel

object DestinasiTugasDetail: DestinasiNavigasi {
    override val route = "detail_Tugas"
    override val titleRes = "Detail Data Tugas"
    const val IDTUGAS = "idTugas"
    val routesWithArg = "$route/{$IDTUGAS}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TugasDetailScreen(
    navigateBack: () -> Unit,
    navigateToTugasUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TugasDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiTugasDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getTugasById()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTugasUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Kontak"
                )
            }
        }
    ) { innerPadding ->
        DetailTugasStatus(
            modifier = Modifier.padding(innerPadding),
            detailTugasUiState = viewModel.tugasDetailState,
            retryAction = { viewModel.getTugasById() }
        )
    }
}

@Composable
fun DetailTugasStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailTugasUiState: DetailTugasUiState
) {
    when (detailTugasUiState) {
        is DetailTugasUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailTugasUiState.Success -> {
            if (detailTugasUiState.tugas.idTugas == 0) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.") }
            } else {
                ItemDetailTim(
                    tugas = detailTugasUiState.tugas, modifier = modifier.fillMaxWidth()
                )
            }
        }
        is DetailTugasUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailTim(
    modifier: Modifier = Modifier,
    tugas: Tugas
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailTugas(judul = "ID Tugas", isinya = tugas.idTugas.toString())
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailTugas(judul = "ID Proyek", isinya = tugas.idProyek.toString())
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailTugas(judul = "ID Proyek", isinya = tugas.idTim.toString())
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailTugas(judul = "nama tugas", isinya = tugas.namaTugas)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailTugas(judul = "tanggal berakhir", isinya = tugas.deskripsiTugas)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailTugas(judul = "prioritas", isinya = tugas.prioritas)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailTugas(judul = "status tugas", isinya = tugas.statusTugas)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun ComponentDetailTugas(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = judul,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = isinya,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}