package com.example.ucp3.ui.view.Tim

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
import com.example.ucp3.model.Tim
import com.example.ucp3.ui.viewmodel.Tim.DetailTimUiState
import com.example.ucp3.ui.viewmodel.Tim.TimDetailViewModel

object DestinasiTimDetail: DestinasiNavigasi {
    override val route = "detail_Tim"
    override val titleRes = "Detail Data Tim"
    const val IDTIM = "idTim"
    val routesWithArg = "$route/{$IDTIM}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimDetailScreen(
    navigateBack: () -> Unit,
    navigateToTimUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TimDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiTimDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getTimById()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTimUpdate,
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
        DetailTimStatus(
            modifier = Modifier.padding(innerPadding),
            detailTimUiState = viewModel.timDetailState,
            retryAction = { viewModel.getTimById() }
        )
    }
}

@Composable
fun DetailTimStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailTimUiState: DetailTimUiState
) {
    when (detailTimUiState) {
        is DetailTimUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailTimUiState.Success -> {
            if (detailTimUiState.tim.idTim == 0) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.") }
            } else {
                ItemDetailTim(
                    tim = detailTimUiState.tim, modifier = modifier.fillMaxWidth()
                )
            }
        }
        is DetailTimUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailTim(
    modifier: Modifier = Modifier,
    tim: Tim
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailTim(judul = "ID Tim", isinya = tim.idTim.toString())
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailTim(judul = "nama tim", isinya = tim.namaTim)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailTim(judul = "deskripsi tim", isinya = tim.deskripsiTim)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun ComponentDetailTim(
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