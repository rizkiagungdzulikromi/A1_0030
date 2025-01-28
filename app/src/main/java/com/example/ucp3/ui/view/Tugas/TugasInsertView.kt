package com.example.ucp3.ui.view.Tugas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import com.example.ucp3.model.Proyek
import com.example.ucp3.model.Tim
import com.example.ucp3.ui.CustomWidget.CoustumeTopAppBar
import com.example.ucp3.ui.PenyediaViewModel
import com.example.ucp3.ui.navigation.DestinasiNavigasi
import com.example.ucp3.ui.viewmodel.Tugas.InsertTugasUiEvent
import com.example.ucp3.ui.viewmodel.Tugas.InsertTugasUiState
import com.example.ucp3.ui.viewmodel.Tugas.TugasInsertViewModel
import kotlinx.coroutines.launch

object DestinasiTugasEntry : DestinasiNavigasi {
    override val route = "entry_Tugas"
    override val titleRes = "Entry Tugas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TugasEntryScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TugasInsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiTugasEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryBody(
            insertTugasUiState = viewModel.uiState,
            timList = viewModel.timList,
            proyekList = viewModel.proyekList,
            onTugasValueChange = viewModel::updateInsertTugasState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTugas()
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
    insertTugasUiState: InsertTugasUiState,
    timList: List<Tim>,
    proyekList: List<Proyek>,
    onTugasValueChange: (InsertTugasUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertTugasUiEvent = insertTugasUiState.insertTugasUiEvent,
            timList = timList,
            proyekList = proyekList,
            onValueChange = onTugasValueChange,
            modifier = Modifier.fillMaxWidth()
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
    insertTugasUiEvent: InsertTugasUiEvent,
    timList: List<Tim>,
    proyekList: List<Proyek>,
    onValueChange: (InsertTugasUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertTugasUiEvent.namaTugas,
            onValueChange = { onValueChange(insertTugasUiEvent.copy(namaTugas = it)) },
            label = { Text("Nama Tugas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        DynamicDropdownTextField(
            label = "ID Tim",
            selectedValue = insertTugasUiEvent.idTim.toString(),
            listItems = timList.map { it.idTim.toString() },
            onValueChanged = {
                val selectedTim = timList.find { tim -> tim.idTim.toString() == it }
                onValueChange(
                    insertTugasUiEvent.copy(idTim = selectedTim?.idTim ?: 0)
                )
            }
        )
        DynamicDropdownTextField(
            label = "ID proyek",
            selectedValue = insertTugasUiEvent.idProyek.toString(),
            listItems = proyekList.map { it.idProyek.toString() },
            onValueChanged = {
                val selectedProyek = proyekList.find { proyek -> proyek.idProyek.toString() == it }
                onValueChange(
                    insertTugasUiEvent.copy(idProyek = selectedProyek?.idProyek ?: 0)
                )
            }
        )
        OutlinedTextField(
            value = insertTugasUiEvent.deskripsiTugas,
            onValueChange = { onValueChange(insertTugasUiEvent.copy(deskripsiTugas = it)) },
            label = { Text("deskripsi tugas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        DynamicDropdownTextField(
            label = "Prioritas",
            selectedValue = insertTugasUiEvent.prioritas,
            listItems = listOf("Tinggi", "Sedang", "Rendah"),
            onValueChanged = { onValueChange(insertTugasUiEvent.copy(prioritas = it)) }
        )
        OutlinedTextField(
            value = insertTugasUiEvent.statusTugas,
            onValueChange = { onValueChange(insertTugasUiEvent.copy(statusTugas = it)) },
            label = { Text("status tugas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if(enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
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

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedValue,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
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
