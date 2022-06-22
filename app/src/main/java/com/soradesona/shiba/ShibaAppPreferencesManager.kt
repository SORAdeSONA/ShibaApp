package com.soradesona.shiba

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class ShibaAppPreferencesManager(context: Context) {
    var shibaPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    // Settings prefs
    fun getImagesToDownloadCount() : Int = shibaPreferences.getInt(IMAGES_TO_DOWNLOAD_KEY, 100)

    companion object {
        private const val IMAGES_TO_DOWNLOAD_KEY = "images_to_download"
    }
}