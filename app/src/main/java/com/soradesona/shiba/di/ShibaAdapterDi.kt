package com.soradesona.shiba.di

import android.app.Application
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
    fun provideShibaImageDownloader(context: Application) : ShibaImageDownloader {
        return ShibaImageDownloader(context)
    }

    @Provides
    fun provideShibaAdapter(shibaImageDownloader: ShibaImageDownloader): ShibaAdapter {
        return ShibaAdapter(shibaImageDownloader)
    }

}