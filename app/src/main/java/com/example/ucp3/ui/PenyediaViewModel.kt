package com.example.ucp3.ui


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp3.ProyekApplication
import com.example.ucp3.ui.view.AnggotaTim.AnggotaDetailScreen
import com.example.ucp3.ui.viewmodel.AnggotaTim.AnggotaDetailViewModel
import com.example.ucp3.ui.viewmodel.AnggotaTim.AnggotaHomeViewModel
import com.example.ucp3.ui.viewmodel.AnggotaTim.AnggotaInsertViewModel
import com.example.ucp3.ui.viewmodel.AnggotaTim.AnggotaUpdateViewModel
import com.example.ucp3.ui.viewmodel.Proyek.ProyekDetailViewModel
import com.example.ucp3.ui.viewmodel.Proyek.ProyekHomeViewModel
import com.example.ucp3.ui.viewmodel.Proyek.ProyekInsertViewModel
import com.example.ucp3.ui.viewmodel.Proyek.ProyekUpdateViewModel
import com.example.ucp3.ui.viewmodel.Tim.TimDetailViewModel
import com.example.ucp3.ui.viewmodel.Tim.TimHomeViewModel
import com.example.ucp3.ui.viewmodel.Tim.TimInsertViewModel
import com.example.ucp3.ui.viewmodel.Tim.TimUpdateViewModel
import com.example.ucp3.ui.viewmodel.Tugas.TugasDetailViewModel
import com.example.ucp3.ui.viewmodel.Tugas.TugasHomeViewModel
import com.example.ucp3.ui.viewmodel.Tugas.TugasInsertViewModel
import com.example.ucp3.ui.viewmodel.Tugas.TugasUpdateViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { ProyekHomeViewModel(aplikasiKontak().container.proyekRepository) }
        initializer { ProyekInsertViewModel(aplikasiKontak().container.proyekRepository) }
        initializer { ProyekDetailViewModel(createSavedStateHandle(), aplikasiKontak().container.proyekRepository) }
        initializer { ProyekUpdateViewModel(createSavedStateHandle(), aplikasiKontak().container.proyekRepository) }

        //Anggota Tim
        initializer { AnggotaHomeViewModel(aplikasiKontak().container.anggotaTimRepository) }
        initializer { AnggotaInsertViewModel(
            aplikasiKontak().container.anggotaTimRepository,
            aplikasiKontak().container.timRepository) }
        initializer { AnggotaDetailViewModel(createSavedStateHandle(), aplikasiKontak().container.anggotaTimRepository) }
        initializer { AnggotaUpdateViewModel(createSavedStateHandle(), aplikasiKontak().container.anggotaTimRepository,aplikasiKontak().container.timRepository) }

        //tugas
        initializer { TugasHomeViewModel(aplikasiKontak().container.tugasRepository) }
        initializer { TugasInsertViewModel(
            aplikasiKontak().container.tugasRepository,
            aplikasiKontak().container.timRepository,
            aplikasiKontak().container.proyekRepository) }
        initializer { TugasDetailViewModel(createSavedStateHandle(), aplikasiKontak().container.tugasRepository) }
        initializer { TugasUpdateViewModel(createSavedStateHandle(), aplikasiKontak().container.tugasRepository,aplikasiKontak().container.timRepository,aplikasiKontak().container.proyekRepository) }

        //tim
        initializer { TimHomeViewModel(aplikasiKontak().container.timRepository) }
        initializer { TimInsertViewModel(aplikasiKontak().container.timRepository) }
        initializer { TimDetailViewModel(createSavedStateHandle(), aplikasiKontak().container.timRepository) }
        initializer { TimUpdateViewModel(createSavedStateHandle(), aplikasiKontak().container.timRepository) }

    }
}

fun CreationExtras.aplikasiKontak(): ProyekApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ProyekApplication)