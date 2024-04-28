package com.soradesona.shiba.api

import retrofit2.Response

interface ShibaRepositoryInterface {

    suspend fun getList(type: String, imagesCount: String): Response<List<String>>

}