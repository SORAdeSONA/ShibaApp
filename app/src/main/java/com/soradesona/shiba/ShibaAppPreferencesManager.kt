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

    fun getDownloadType() : Boolean = shibaPreferences.getBoolean(DOWNLOAD_TYPE, false)

    fun setDownloadType(value: Boolean){
        shibaPreferences.edit().putBoolean(DOWNLOAD_TYPE, value).apply()
    }

    companion object {
        private const val IMAGES_TO_DOWNLOAD_KEY = "images_to_download"
        private const val DOWNLOAD_TYPE = "download_type"
    }
}