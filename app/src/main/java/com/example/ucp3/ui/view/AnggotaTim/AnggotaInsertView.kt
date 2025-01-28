package com.example.ucp3.ui.view.AnggotaTim

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp3.model.Tim
import com.example.ucp3.ui.CustomWidget.CoustumeTopAppBar
import com.example.ucp3.ui.PenyediaViewModel
import com.example.ucp3.ui.navigation.DestinasiNavigasi
import com.example.ucp3.ui.viewmodel.AnggotaTim.AnggotaInsertViewModel
import com.example.ucp3.ui.viewmodel.AnggotaTim.InsertAnggotaUiEvent
import com.example.ucp3.ui.viewmodel.AnggotaTim.InsertAnggotaUiState
import kotlinx.coroutines.launch

object DestinasiAnggotaEntry : DestinasiNavigasi {
    override val route = "entry_Anggota"
    override val titleRes = "Entry Anggota"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnggotaEntryScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AnggotaInsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiAnggotaEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertAnggotaUiState = viewModel.uiState,
            timList = viewModel.timList,
            onAnggotaValueChange = viewModel::updateInsertAnggtState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertAnggt()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertAnggotaUiState: InsertAnggotaUiState,
    timList: List<Tim>,
    onAnggotaValueChange: (InsertAnggotaUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertAnggotaUiState = insertAnggotaUiState,
            timList = timList,
            onValueChange = onAnggotaValueChange,
            modifier = Modifier.fillMaxWidth(),

        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertAnggotaUiState: InsertAnggotaUiState,
    timList: List<Tim>,
    onValueChange: (InsertAnggotaUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertAnggotaUiState.insertAnggotaUiEvent.namaAnggota,
            onValueChange = { onValueChange(insertAnggotaUiState.insertAnggotaUiEvent.copy(namaAnggota = it)) },
            label = { Text("Nama anggota") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertAnggotaUiState.insertAnggotaUiEvent.peran,
            onValueChange = { onValueChange(insertAnggotaUiState.insertAnggotaUiEvent.copy(peran = it)) },
            label = { Text("Peran") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        DynamicDropdownTextField(
            label = "ID Tim",
            selectedValue = insertAnggotaUiState.insertAnggotaUiEvent.idTim.toString(),
            listItems = timList.map { it.idTim.toString() },
            onValueChanged = {
                val selectedTim = timList.find { tim -> tim.idTim.toString() == it }
                onValueChange(
                    insertAnggotaUiState.insertAnggotaUiEvent.copy(idTim = selectedTim?.idTim ?: 0)
                )
            }
        )
        if (enabled) {
            Divider(
                thickness = 8.dp,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicDropdownTextField(
    label: String,
    selectedValue: String,
    listItems: List<String>,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = selectedValue,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listItems.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        expanded = false
                        onValueChanged(item)
                    }
                )
            }
        }
    }
}
