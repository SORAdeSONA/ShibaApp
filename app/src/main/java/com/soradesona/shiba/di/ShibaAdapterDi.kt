package com.soradesona.shiba.di

import com.soradesona.shiba.ShibaImageDownloader
import com.soradesona.shiba.adapter.ShibaAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ShibaAdapterDi {

    @Provides
    fun provideShibaAdapter(shibaImageDownloader: ShibaImageDownloader): ShibaAdapter {
        return ShibaAdapter(shibaImageDownloader)
    }

}