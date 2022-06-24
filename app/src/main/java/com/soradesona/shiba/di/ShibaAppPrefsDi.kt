package com.soradesona.shiba.di

import android.app.Application
import com.soradesona.shiba.ShibaAppPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ShibaAppPrefsDi {

    @Provides
    fun provideShibaAppPrefs(context: Application) : ShibaAppPreferencesManager {
        return ShibaAppPreferencesManager(context)
    }

}