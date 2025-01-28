package com.example.ucp3.repository

import com.example.ucp3.model.AllTimResponse
import com.example.ucp3.model.Tim
import com.example.ucp3.service.TimService
import okio.IOException

interface TimRepository {

    suspend fun insertTim(tim: Tim)

    suspend fun getAllTim(): AllTimResponse

    suspend fun updateTim(idTim: Int, tim: Tim)

    suspend fun deleteTim(idTim: Int)

    suspend fun getTimById(idTim: Int): Tim
}

class NetworkTimRepository(
    private val timApiService: TimService
) : TimRepository {
    override suspend fun insertTim(tim: Tim) {
        timApiService.insertTim(tim)
    }

    override suspend fun getAllTim(): AllTimResponse =
        timApiService.getAllTim()

    override suspend fun updateTim(idTim: Int, tim: Tim) {
        timApiService.updateTim(idTim, tim)
    }

    override suspend fun deleteTim(idTim: Int ) {
        try {
            val response = timApiService.deleteTim(idTim)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete tim. HTTP Status code: ${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }

        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTimById(idTim: Int): Tim {
        return timApiService.getTimById(idTim).data
    }
}
