package com.example.ucp3.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AnggotaTim (
    @SerialName("id_anggota")
    val idAnggota: Int,

    @SerialName("id_tim")
    val idTim: Int,

    @SerialName("nama_anggota")
    val namaAnggota: String,

    val peran: String
)

@Serializable
data class AllAnggotaTimResponse(
    val status: Boolean,
    val message: String,
    val data: List<AnggotaTim>
)

@Serializable
data class AnggotaDetailResponse(
    val status: Boolean,
    val message: String,
    val data:AnggotaTim
)