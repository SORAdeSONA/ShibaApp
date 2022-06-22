package com.soradesona.shiba.api

import retrofit2.Response

interface ShibaRepositoryInterface {

    suspend fun getShibaList(imagesCount: String): Response<List<String>>

}