package com.example.data.network

import com.example.data.network.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("{querypath}?show-fields=body,thumbnail,headline")
    suspend fun getItem(@Path(value = "querypath", encoded = true) queryPath: String): ApiResponse

    @GET("search?query-fields=body,headline&show-fields=thumbnail,headline")
    suspend fun getItems(@Query("q") query: String?,
                 @Query("section") section: String,
                 @Query("tag") tag: String?
    ): ApiResponse
}
