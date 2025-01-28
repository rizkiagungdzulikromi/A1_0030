package com.example.ucp3.repository

import com.example.ucp3.model.AllProyekResponse
import com.example.ucp3.model.Proyek
import com.example.ucp3.service.ProyekService
import okio.IOException

interface ProyekRepository {

    suspend fun insertProyek(proyek: Proyek)

    suspend fun getAllProyek(): AllProyekResponse

    suspend fun updateProyek(idProyek: Int, proyek: Proyek)

    suspend fun deleteProyek(idProyek: Int)

    suspend fun getProyekById(idProyek: Int): Proyek
}

class NetworkProyekRepository(
    private val proyekApiService: ProyekService
) : ProyekRepository {
    override suspend fun insertProyek(proyek: Proyek) {
        proyekApiService.insertProyek(proyek)
    }

    override suspend fun getAllProyek(): AllProyekResponse =
        proyekApiService.getAllProyek()

    override suspend fun updateProyek(idProyek: Int, proyek: Proyek) {
        proyekApiService.updateProyek(idProyek, proyek)
    }

    override suspend fun deleteProyek(idProyek: Int) {
        try {
            val response = proyekApiService.deleteProyek(idProyek)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete proyek. HTTP Status code: ${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }

        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getProyekById(idProyek: Int): Proyek{
        return proyekApiService.getProyekById(idProyek).data
    }
}
