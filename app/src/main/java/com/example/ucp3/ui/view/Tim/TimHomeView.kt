package com.example.ucp3.ui.view.Tim

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
import com.example.ucp3.model.Tim
import com.example.ucp3.ui.CustomWidget.CoustumeTopAppBar
import com.example.ucp3.ui.PenyediaViewModel
import com.example.ucp3.ui.navigation.DestinasiNavigasi
import com.example.ucp3.ui.viewmodel.Tim.HomeTimUiState

import com.example.ucp3.ui.viewmodel.Tim.TimHomeViewModel


object DestinasiTimHome: DestinasiNavigasi {
    override val route = "home_Tim"
    override val titleRes = "Home Tim"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimHomeScreen(
    navigateHomeBack: () -> Unit,
    navigateToTimEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: TimHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiTimHome.titleRes,
                canNavigateBack = true,
                navigateUp = navigateHomeBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getTm()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTimEntry,
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
            homeTimUiState = viewModel.tmUiState,
            retryAction = { viewModel.getTm() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteTm(it.idTim)
                viewModel.getTm() // Refresh list after deletion
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeTimUiState: HomeTimUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tim) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeTimUiState) {
        is HomeTimUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeTimUiState.Success -> {
            if (homeTimUiState.tim.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada data Proyek")
                }
            } else {
                TimLayout(
                    tim = homeTimUiState.tim,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idTim.toString()) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeTimUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun TimLayout(
    tim: List<Tim>,
    modifier: Modifier = Modifier,
    onDetailClick: (Tim) -> Unit,
    onDeleteClick: (Tim) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tim) { timItem ->
            TimCard(
                tim = timItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(timItem) },
                onDeleteClick = { onDeleteClick(timItem) }
            )
        }
    }
}

@Composable
fun TimCard(
    tim: Tim,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tim) -> Unit = {}
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
                    text = tim.namaTim,
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
                    text = tim.idTim.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = tim.deskripsiTim,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick(tim)
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
