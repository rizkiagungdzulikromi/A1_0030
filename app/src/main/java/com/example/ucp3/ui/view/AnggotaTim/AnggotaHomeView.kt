package com.example.ucp3.ui.view.AnggotaTim

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp3.R
import com.example.ucp3.model.AnggotaTim
import com.example.ucp3.ui.CustomWidget.CoustumeTopAppBar
import com.example.ucp3.ui.PenyediaViewModel
import com.example.ucp3.ui.navigation.DestinasiNavigasi
import com.example.ucp3.ui.viewmodel.AnggotaTim.AnggotaHomeViewModel
import com.example.ucp3.ui.viewmodel.AnggotaTim.HomeAnggotaUiState



object DestinasiAnggotaHome: DestinasiNavigasi {
    override val route = "home_Anggota"
    override val titleRes = "Home Anggota"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnggotaHomeScreen(
    navigateHomeBack: () -> Unit,
    navigateToAnggotaEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: AnggotaHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiAnggotaHome.titleRes,
                canNavigateBack = true,
                navigateUp = navigateHomeBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getAnggt()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAnggotaEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Anggota"
                )
            }
        }
    ) { innerPadding ->
        HomeStatus(
            homeAnggotaUiState = viewModel.anggtUiState,
            retryAction = { viewModel.getAnggt() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteAnggt(it.idAnggota)
                viewModel.getAnggt() // Refresh list after deletion
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeAnggotaUiState: HomeAnggotaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (AnggotaTim) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeAnggotaUiState) {
        is HomeAnggotaUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeAnggotaUiState.Success -> {
            if (homeAnggotaUiState.anggotaTim.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada data anggota")
                }
            } else {
                AnggotaLayout(
                    anggotaTim = homeAnggotaUiState.anggotaTim,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idAnggota.toString()) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeAnggotaUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(id = R.drawable.loading),
        contentDescription = "Loading"
    )
}

@Composable
fun OnError(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error),
            contentDescription = "Error"
        )
        Text(
            text = "Failed to load projects",
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text("Retry")
        }
    }
}

@Composable
fun AnggotaLayout(
    anggotaTim: List<AnggotaTim>,
    modifier: Modifier = Modifier,
    onDetailClick: (AnggotaTim) -> Unit,
    onDeleteClick: (AnggotaTim) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(anggotaTim) { anggotaItem ->
            AnggotaCard(
                anggotaTim = anggotaItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(anggotaItem) },
                onDeleteClick = { onDeleteClick(anggotaItem) }
            )
        }
    }
}

@Composable
fun AnggotaCard(
    anggotaTim: AnggotaTim,
    modifier: Modifier = Modifier,
    onDeleteClick: (AnggotaTim) -> Unit = {}
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = anggotaTim.namaAnggota,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { deleteConfirmationRequired = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
                Text(
                    text = anggotaTim.idAnggota.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = anggotaTim.peran,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick(anggotaTim)
            },
            onDeleteCancel = {
                deleteConfirmationRequired = false
            }, modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Delete Data") },
        text = { Text("Are you sure you want to delete this project?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Yes")
            }
        }
    )
}
