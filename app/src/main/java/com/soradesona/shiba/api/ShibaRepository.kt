package com.soradesona.shiba.api

import retrofit2.Response
import javax.inject.Inject

class ShibaRepository @Inject constructor(private val apiService: ApiService) : ShibaRepositoryInterface {

    override suspend fun getShibaList(imagesCount: String): Response<List<String>> {
        return apiService.getShibaList(imagesCount)
    }

}