package com.soradesona.shiba.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.soradesona.shiba.ShibaAppPreferencesManager
import com.soradesona.shiba.api.ShibaRepository
import com.soradesona.shiba.paging.MainPagingSource
import com.soradesona.shiba.status.StatusEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ShibaViewModel @Inject constructor(
    private val shibaRepository: ShibaRepository,
    private val shibaPreferencesManager: ShibaAppPreferencesManager
) : ViewModel() {

    private val _listResponse = MutableLiveData<List<String>>()
    val listResponse: LiveData<List<String>> get() = _listResponse

    val loadingStatusMain = MutableLiveData<StatusEnum?>()

    var imageCount = shibaPreferencesManager.getImagesToDownloadCount().toString()

    var pagingTypeToDownload = ""

    var loadListPaging: Flow<PagingData<String>> = createPager()

    private fun createPager(): Flow<PagingData<String>> {
        return Pager(PagingConfig(pageSize = 40, initialLoadSize = 40)) {
            MainPagingSource(shibaRepository, pagingTypeToDownload, loadingStatusMain)
        }.flow.cachedIn(viewModelScope)
    }

    fun refreshData() {
        loadListPaging = createPager()
    }

    fun loadList(listType: String) {
        viewModelScope.launch {
            loadingStatusMain.value = StatusEnum.LOADING

            withContext(Dispatchers.IO) {
                val response = shibaRepository.getList(
                    type = listType,
                    imagesCount = shibaPreferencesManager.getImagesToDownloadCount().toString()
                )
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        loadingStatusMain.value = StatusEnum.SUCCESS
                        _listResponse.value = response.body()
                        println(response.body())
                    } else loadingStatusMain.value = StatusEnum.ERROR
                }

            }
        }
    }


    fun setImagesCountToLoad(count: Int) {
        shibaPreferencesManager.setImagesToDownloadCount(count)
    }

    fun setCurrentDownloadType(value: Boolean) {
        return shibaPreferencesManager.setDownloadType(value)
    }

    fun getCurrentDownloadType() : Boolean{
        return shibaPreferencesManager.getDownloadType()
    }

}
