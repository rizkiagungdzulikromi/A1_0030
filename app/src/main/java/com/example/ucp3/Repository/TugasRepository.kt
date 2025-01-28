package com.example.ucp3.repository

import com.example.ucp3.model.AllTugasResponse
import com.example.ucp3.model.Tugas
import com.example.ucp3.model.TugasDetailResponse
import com.example.ucp3.service.TugasService
import okio.IOException

interface TugasRepository {

    suspend fun insertTugas(tugas: Tugas)

    suspend fun getAllTugas(): AllTugasResponse

    suspend fun updateTugas(idTugas: Int, tugas: Tugas)

    suspend fun deleteTugas(idTugas: Int)

    suspend fun getTugasById(idTugas: Int): Tugas
}

class NetworkTugasRepository(
    private val tugasApiService: TugasService
) : TugasRepository {
    override suspend fun insertTugas(tugas: Tugas) {
        tugasApiService.insertTugas(tugas)
    }

    override suspend fun getAllTugas(): AllTugasResponse =
        tugasApiService.getAllTugas()

    override suspend fun updateTugas(idTugas: Int, tugas: Tugas) {
        tugasApiService.updateTugas(idTugas, tugas)
    }

    override suspend fun deleteTugas(idTugas: Int) {
        try {
            val response = tugasApiService.deleteTugas(idTugas)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete tugas. HTTP Status code: ${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }

        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTugasById(idTugas: Int): Tugas {
        return tugasApiService.getTugasById(idTugas).data
    }
}
