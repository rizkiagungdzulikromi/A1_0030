package com.example.ucp3.service

import com.example.ucp3.model.AllAnggotaTimResponse
import com.example.ucp3.model.AnggotaDetailResponse
import com.example.ucp3.model.AnggotaTim
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AnggotaTimService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",

        )

    @POST("anggotatim/store")
    suspend fun insertAnggotaTim(@Body anggotaTim: AnggotaTim)

    @GET("anggotatim/")
    suspend fun getAllAnggotaTim(): AllAnggotaTimResponse

    @GET("anggotatim/{id_anggota}")
    suspend fun getAnggotaTimById(@Path("id_anggota") idAnggota:Int): AnggotaDetailResponse

    @PUT("anggotatim/{id_anggota}")
    suspend fun updateAnggotaTim(@Path("id_anggota")idAnggota: Int, @Body anggotaTim: AnggotaTim)

    @DELETE("anggotatim/{id_anggota}")
    suspend fun deleteAnggotaTim(@Path("id_anggota")idAnggota: Int) : retrofit2.Response<Void>
}