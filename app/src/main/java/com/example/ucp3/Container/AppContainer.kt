package com.example.ucp3.Container

import com.example.ucp3.repository.AnggotaTimRepository
import com.example.ucp3.repository.NetworkAnggotaTimRepository
import com.example.ucp3.repository.NetworkProyekRepository
import com.example.ucp3.repository.NetworkTimRepository
import com.example.ucp3.repository.NetworkTugasRepository
import com.example.ucp3.repository.ProyekRepository
import com.example.ucp3.repository.TimRepository
import com.example.ucp3.repository.TugasRepository
import com.example.ucp3.service.AnggotaTimService
import com.example.ucp3.service.ProyekService
import com.example.ucp3.service.TimService
import com.example.ucp3.service.TugasService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val proyekRepository: ProyekRepository
    val anggotaTimRepository: AnggotaTimRepository
    val timRepository: TimRepository
    val tugasRepository: TugasRepository
}

class KontakContainer : AppContainer {

    private val baseUrl = "http://10.0.2.2:7000/api/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(baseUrl)
    .build()

    //proyek
    private val proyekService: ProyekService by lazy {
        retrofit.create(ProyekService::class.java)
    }
    override val proyekRepository: ProyekRepository by lazy {
        NetworkProyekRepository(proyekService)
    }

    //anggota
    private val anggotaTimService: AnggotaTimService by lazy {
        retrofit.create(AnggotaTimService::class.java)
    }
    override val anggotaTimRepository: AnggotaTimRepository by lazy {
        NetworkAnggotaTimRepository(anggotaTimService)
    }

    //tim
    private val timService: TimService by lazy {
        retrofit.create(TimService::class.java)
    }
    override val timRepository: TimRepository by lazy {
        NetworkTimRepository(timService)
    }

    //tugas
    private val tugasService: TugasService by lazy {
        retrofit.create(TugasService::class.java)
    }
    override val tugasRepository: TugasRepository by lazy {
        NetworkTugasRepository(tugasService)
    }


}