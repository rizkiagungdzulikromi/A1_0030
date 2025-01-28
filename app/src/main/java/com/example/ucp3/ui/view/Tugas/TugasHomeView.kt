package com.example.ucp3.ui.view.Tugas

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
import com.example.ucp3.model.Tugas
import com.example.ucp3.ui.CustomWidget.CoustumeTopAppBar
import com.example.ucp3.ui.PenyediaViewModel
import com.example.ucp3.ui.navigation.DestinasiNavigasi
import com.example.ucp3.ui.viewmodel.Tugas.HomeTugasUiState
import com.example.ucp3.ui.viewmodel.Tugas.TugasHomeViewModel


object DestinasiTugasHome: DestinasiNavigasi {
    override val route = "hometugas"
    override val titleRes = "Home Tugas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TugasHomeScreen(
    navigateHomeBack: () -> Unit,
    navigateToTugasEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: TugasHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiTugasHome.titleRes,
                canNavigateBack = true,
                navigateUp = navigateHomeBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getTgs()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTugasEntry,
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
            homeTugasUiState = viewModel.tgsUiState,
            retryAction = { viewModel.getTgs() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteTgs(it.idTugas)
                viewModel.getTgs() // Refresh list after deletion
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeTugasUiState: HomeTugasUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tugas) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeTugasUiState) {
        is HomeTugasUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeTugasUiState.Success -> {
            if (homeTugasUiState.tugas.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada data Tugas")
                }
            } else {
                TugasLayout(
                    tugas = homeTugasUiState.tugas,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idTugas.toString()) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeTugasUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun TugasLayout(
    tugas: List<Tugas>,
    modifier: Modifier = Modifier,
    onDetailClick: (Tugas) -> Unit,
    onDeleteClick: (Tugas) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tugas) { tugasItem ->
            TugasCard(
                tugas = tugasItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(tugasItem) },
                onDeleteClick = { onDeleteClick(tugasItem) }
            )
        }
    }
}

@Composable
fun TugasCard(
    tugas: Tugas,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tugas) -> Unit = {}
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
                    text = tugas.namaTugas,
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
                    text = tugas.idTugas.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = tugas.prioritas,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick(tugas)
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
