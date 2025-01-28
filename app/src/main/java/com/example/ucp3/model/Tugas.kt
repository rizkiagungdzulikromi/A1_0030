package com.example.ucp3.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tugas (
    @SerialName("id_tugas")
    val idTugas: Int,

    @SerialName("id_proyek")
    val idProyek: Int,

    @SerialName("id_tim")
    val idTim: Int,

    @SerialName("nama_tugas")
    val namaTugas: String,

    @SerialName("deskripsi_tugas")
    val deskripsiTugas: String,

    val prioritas: String,

    @SerialName("status_tugas")
    val statusTugas: String
)

@Serializable
data class AllTugasResponse(
    val status: Boolean,
    val message: String,
    val data: List<Tugas>
)

@Serializable
data class TugasDetailResponse(
    val status: Boolean,
    val message: String,
    val data:Tugas
)