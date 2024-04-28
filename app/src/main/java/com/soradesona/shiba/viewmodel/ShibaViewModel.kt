package com.soradesona.shiba.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soradesona.shiba.ShibaAppPreferencesManager
import com.soradesona.shiba.api.ShibaRepository
import com.soradesona.shiba.status.StatusEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ShibaViewModel @Inject constructor(
    private val shibaRepository: ShibaRepository,
    private val shibaPreferencesManager: ShibaAppPreferencesManager
) : ViewModel() {

    var imageCount = shibaPreferencesManager.getImagesToDownloadCount().toString()

    private val _loadingStatus = MutableLiveData<StatusEnum>()
    val loadingStatus: LiveData<StatusEnum> get() = _loadingStatus

    private val _listResponse = MutableLiveData<List<String>>()
    val listResponse: LiveData<List<String>> get() = _listResponse

    fun loadList(listType: String) {
        viewModelScope.launch {
            _loadingStatus.value = StatusEnum.LOADING

            withContext(Dispatchers.IO) {
                val response = shibaRepository.getList(
                    type = listType,
                    imagesCount = shibaPreferencesManager.getImagesToDownloadCount().toString()
                )
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        _loadingStatus.value = StatusEnum.SUCCESS
                        _listResponse.value = response.body()
                        println(response.body())
                    } else _loadingStatus.value = StatusEnum.ERROR
                }

            }
        }
    }

    fun setImagesCountToLoad(count: Int) {
        shibaPreferencesManager.setImagesToDownloadCount(count)
    }

}
