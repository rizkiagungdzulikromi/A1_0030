package com.example.ucp3.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Tim (
    @SerialName("id_tim")
    val idTim: Int,

    @SerialName("nama_tim")
    val namaTim: String,

    @SerialName("deskripsi_tim")
    val deskripsiTim: String
)

@Serializable
data class AllTimResponse(
    val status: Boolean,
    val message: String,
    val data: List<Tim>
)

@Serializable
data class TimDetailResponse(
    val status: Boolean,
    val message: String,
    val data:Tim
)


