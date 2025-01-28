package com.example.ucp3.service


import com.example.ucp3.model.AllTugasResponse
import com.example.ucp3.model.Tugas
import com.example.ucp3.model.TugasDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TugasService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",

        )

    @POST("tugas/store")
    suspend fun insertTugas(@Body tugas: Tugas)

    @GET("tugas/.")
    suspend fun getAllTugas(): AllTugasResponse

    @GET("tugas/{id_tugas}")
    suspend fun getTugasById(@Path("id_tugas") idTugas:Int): TugasDetailResponse

    @PUT("tugas/{id_tugas}")
    suspend fun updateTugas(@Path("id_tugas")idTugas: Int, @Body tugas: Tugas)

    @DELETE("tugas/{id_tugas}")
    suspend fun deleteTugas(@Path("id_tugas")idTugas: Int) : retrofit2.Response<Void>
}