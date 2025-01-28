package com.example.ucp3.repository

import com.example.ucp3.model.AllAnggotaTimResponse
import com.example.ucp3.model.AnggotaTim
import com.example.ucp3.service.AnggotaTimService
import okio.IOException

interface AnggotaTimRepository {

    suspend fun insertAnggotaTim(anggotaTim: AnggotaTim)

    suspend fun getAnggotaTim(): AllAnggotaTimResponse

    suspend fun updateAnggotaTim(idAnggota: Int, anggotaTim: AnggotaTim)

    suspend fun deleteAnggotaTim(idAnggota: Int)

    suspend fun getAnggotaTimById(idAnggota: Int): AnggotaTim
}

class NetworkAnggotaTimRepository(
    private val anggotaTimApiService: AnggotaTimService
) : AnggotaTimRepository {
    override suspend fun insertAnggotaTim(anggotaTim: AnggotaTim) {
        anggotaTimApiService.insertAnggotaTim(anggotaTim)
    }

    override suspend fun getAnggotaTim(): AllAnggotaTimResponse =
        anggotaTimApiService.getAllAnggotaTim()

    override suspend fun updateAnggotaTim(idAnggota: Int, anggotaTim: AnggotaTim) {
        anggotaTimApiService.updateAnggotaTim(idAnggota, anggotaTim)
    }

    override suspend fun deleteAnggotaTim(idAnggota: Int) {
        try {
            val response = anggotaTimApiService.deleteAnggotaTim(idAnggota)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete anggota tim. HTTP Status code: ${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }

        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAnggotaTimById(idAnggota: Int): AnggotaTim {
        return anggotaTimApiService.getAnggotaTimById(idAnggota).data
    }
}
