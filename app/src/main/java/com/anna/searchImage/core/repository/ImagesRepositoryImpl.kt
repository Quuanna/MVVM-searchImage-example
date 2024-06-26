package com.anna.searchImage.core.repository

import com.anna.searchImage.data.model.response.SearchImageResponseData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart


class ImagesRepositoryImpl(
    private val imageDataRemoteSource: ImageDataSource,
    private val imageDataLocalSource: ImageDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    // Local Data
) : ImagesRepository {
    override suspend fun searchImage(
        onStart: (() -> Unit)?,
        onCompletion: (() -> Unit)?,
        onError: ((String) -> Unit)?,
        keywords: String
    ): Flow<SearchImageResponseData> = flow {
        imageDataRemoteSource.searchImage(
            onError = { searchImageLocal(keywords) { onError?.invoke(it) } }, keywords
        ).collect {
            emit(it)
        }
    }.onStart { onStart?.invoke() }.onCompletion { onCompletion?.invoke() }.flowOn(ioDispatcher)

    private fun searchImageLocal(keywords: String, onError: (String) -> Unit) {
        imageDataLocalSource.searchImage(onError = { onError(it) }, keywords = keywords)
    }
}