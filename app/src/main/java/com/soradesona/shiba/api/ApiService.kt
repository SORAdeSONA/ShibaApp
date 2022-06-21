package com.soradesona.shiba.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/shibes?urls=true&httpsUrls=true")
    suspend fun getShibaList(
        @Query("count") count : String
    ): Response<List<String>>

    //api/shibes?count=100&urls=true&httpsUrls=true

}