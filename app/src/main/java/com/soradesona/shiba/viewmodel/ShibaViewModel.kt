package com.soradesona.shiba.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soradesona.shiba.ShibaAppPreferencesManager
import com.soradesona.shiba.api.ShibaRepository
import com.soradesona.shiba.status.StatusEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShibaViewModel @Inject constructor(
    private val shibaRepository: ShibaRepository,
    private val shibaPreferencesManager: ShibaAppPreferencesManager
    ) : ViewModel() {

    private var prefsImagesCount : String = shibaPreferencesManager.getImagesToDownloadCount().toString()

    private val _loadingStatus = MutableLiveData<StatusEnum>()
    val loadingStatus: LiveData<StatusEnum> get() = _loadingStatus

    private val _shibaResponse = MutableLiveData<List<String>>()
    val shibaResponse: LiveData<List<String>> get() = _shibaResponse

    init {
        loadShibaList()
    }

    fun loadShibaList() {
        viewModelScope.launch {
            _loadingStatus.value = StatusEnum.LOADING
            try {
                val response = shibaRepository.getShibaList(prefsImagesCount)
                if (response.isSuccessful && response.body() != null) {
                    _loadingStatus.value = StatusEnum.SUCCESS
                    _shibaResponse.value = response.body()
                }else _loadingStatus.value = StatusEnum.ERROR
            } catch (e: Exception) {
                println(e)
                _loadingStatus.value = StatusEnum.ERROR
            }
        }

    }
}