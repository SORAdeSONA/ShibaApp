package com.soradesona.shiba

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class ShibaAppPreferencesManager(context: Context) {
    private var shibaPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getImagesToDownloadCount() : Int = shibaPreferences.getInt(IMAGES_TO_DOWNLOAD_KEY, 100)

    fun setImagesToDownloadCount(count: Int){
        shibaPreferences.edit().putInt(IMAGES_TO_DOWNLOAD_KEY, count).apply()
    }

    companion object {
        private const val IMAGES_TO_DOWNLOAD_KEY = "images_to_download"
    }
}