package com.example.ucp3.ui.view.Proyek

import com.example.ucp3.ui.CustomWidget.CoustumeTopAppBar
import com.example.ucp3.ui.PenyediaViewModel
import com.example.ucp3.ui.navigation.DestinasiNavigasi
import com.example.ucp3.ui.viewmodel.Proyek.ProyekDetailViewModel
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
import com.example.ucp3.model.Proyek
import com.example.ucp3.ui.view.OnError
import com.example.ucp3.ui.view.OnLoading
import com.example.ucp3.ui.viewmodel.Proyek.DetailUiState

object DestinasiDetail: DestinasiNavigasi {
    override val route = "detail_Proyek"
    override val titleRes = "Detail Data proyek"
    const val IDPROYEK = "idProyek"
    val routesWithArg = "$route/{$IDPROYEK}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProyekDetailScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProyekDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getProyekById()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemUpdate,
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
        DetailStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.proyekDetailState,
            retryAction = { viewModel.getProyekById() }
        )
    }
}

@Composable
fun DetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState
) {
    when (detailUiState) {
        is DetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailUiState.Success -> {
            if (detailUiState.proyek.idProyek == 0) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.") }
            } else {
                ItemDetailPryk(
                    proyek = detailUiState.proyek, modifier = modifier.fillMaxWidth()
                )
            }
        }
        is DetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailPryk(
    modifier: Modifier = Modifier,
    proyek: Proyek
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailPryk(judul = "ID PROYEK", isinya = proyek.idProyek.toString())
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPryk(judul = "Nama Proyek", isinya = proyek.namaProyek)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPryk(judul = "deskripsi proyek", isinya = proyek.deskripsiProyek)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPryk(judul = "tanggal mulai", isinya = proyek.tanggalMulai)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPryk(judul = "tanggal berakhir", isinya = proyek.tanggalBerakhir)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPryk(judul = "Angkatan", isinya = proyek.statusProyek)
        }
    }
}

@Composable
fun ComponentDetailPryk(
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