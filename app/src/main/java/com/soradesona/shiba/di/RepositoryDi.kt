package com.soradesona.shiba.di

import com.soradesona.shiba.api.ApiService
import com.soradesona.shiba.api.ShibaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryDi {

    @Provides
    fun provideDataRepository(apiService: ApiService): ShibaRepository {
        return ShibaRepository(apiService)
    }
}