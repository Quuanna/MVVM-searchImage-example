package com.anna.searchImage.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.anna.searchImage.core.repository.ImagesRepository
import com.anna.searchImage.data.model.response.SearchImageResponseData
import com.anna.searchImage.ui.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val imagesRepository: ImagesRepository) : BaseViewModel() {

    // response
    val getResponseImagesList: LiveData<SearchImageResponseData>
        get() = setImagesList

    // 搜不到資料
    val isSearchNotFound: LiveData<Boolean>
        get() = setIsSearchNotFound


    // private LiveData
    private val setImagesList = MutableLiveData<SearchImageResponseData>()
    private val setIsSearchNotFound = MutableLiveData<Boolean>()


    fun searchNotFound(isNotFoundData: Boolean) {
        setIsSearchNotFound.postValue(isNotFoundData)
    }

    // 執行異步操作來獲取圖片
    fun callApiResponseData(keyword: String) {
        viewModelScope.launch(handlerException) {
            imagesRepository.searchImage(
                onStart = { showLoading(true) },
                onCompletion = { showLoading(false) },
                onError = { showServiceMessageError(it) },
                keyword
            ).collect {
                if (it.dataList.isEmpty()) {
                    searchNotFound(true)
                } else {
                    setImagesList.postValue(it)
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class TasksViewModelFactory (
    private val tasksRepository: ImagesRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (MainViewModel(tasksRepository) as T)
}


