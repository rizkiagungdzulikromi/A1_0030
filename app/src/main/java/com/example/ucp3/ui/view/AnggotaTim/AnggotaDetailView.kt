package com.example.ucp3.ui.view.AnggotaTim

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
import com.example.ucp3.model.AnggotaTim
import com.example.ucp3.ui.viewmodel.AnggotaTim.AnggotaDetailViewModel
import com.example.ucp3.ui.viewmodel.AnggotaTim.DetailAnggotaUiState

object DestinasiAnggotaDetail: DestinasiNavigasi {
    override val route = "detail_Anggota"
    override val titleRes = "Detail Data Anggota"
    const val IDANGGOTA = "idAnggota"
    val routesWithArg = "$route/{$IDANGGOTA}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnggotaDetailScreen(
    navigateBack: () -> Unit,
    navigateToAnggotaUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AnggotaDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiAnggotaDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getAnggotaTimbyid()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAnggotaUpdate,
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
        DetailAnggotaStatus(
            modifier = Modifier.padding(innerPadding),
            detailAnggotaUiState = viewModel.anggotaDetailState,
            retryAction = { viewModel.getAnggotaTimbyid() }
        )
    }
}

@Composable
fun DetailAnggotaStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailAnggotaUiState: DetailAnggotaUiState
) {
    when (detailAnggotaUiState) {
        is DetailAnggotaUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailAnggotaUiState.Success -> {
            if (detailAnggotaUiState.anggotaTim.idAnggota == 0) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.") }
            } else {
                ItemDetailAnggt(
                    anggotaTim = detailAnggotaUiState.anggotaTim, modifier = modifier.fillMaxWidth()
                )
            }
        }
        is DetailAnggotaUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailAnggt(
    modifier: Modifier = Modifier,
    anggotaTim: AnggotaTim
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailAnggt(judul = "ID Anggota", isinya = anggotaTim.idAnggota.toString())
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailAnggt(judul = "ID TIM", isinya = anggotaTim.idTim.toString())
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailAnggt(judul = "nama anggota", isinya = anggotaTim.namaAnggota)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailAnggt(judul = "peran", isinya = anggotaTim.peran)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun ComponentDetailAnggt(
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