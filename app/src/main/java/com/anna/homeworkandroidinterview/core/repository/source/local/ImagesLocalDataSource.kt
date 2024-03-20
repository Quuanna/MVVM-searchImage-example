package com.anna.homeworkandroidinterview.core.repository.source.local

import com.anna.homeworkandroidinterview.core.repository.ImageDataSource
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ImagesLocalDataSource : ImageDataSource {
    override fun searchImage(
        onError: (String) -> Unit,
        keywords: String
    ): Flow<SearchImageResponseData> {
        return flow {
            // TODO　實作　DB Source
            emit(SearchImageResponseData(0, 0, arrayListOf()))
        }
    }

}