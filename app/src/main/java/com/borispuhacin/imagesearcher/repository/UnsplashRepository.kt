package com.borispuhacin.imagesearcher.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.borispuhacin.imagesearcher.network.api.UnsplashApi
import com.borispuhacin.imagesearcher.data.PagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApi) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PagingSource(unsplashApi, query) }
        ).liveData
}