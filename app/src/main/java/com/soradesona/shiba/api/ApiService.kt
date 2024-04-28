package com.soradesona.shiba.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/{type}?urls=true&httpsUrls=true")
    suspend fun getList(
        @Path("type") type : String,
        @Query("count") count : String
    ): Response<List<String>>
}