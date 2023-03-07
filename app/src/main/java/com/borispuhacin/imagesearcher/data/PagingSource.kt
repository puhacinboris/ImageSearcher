package com.borispuhacin.imagesearcher.data

import com.borispuhacin.imagesearcher.network.api.UnsplashApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.io.IOException
import retrofit2.HttpException
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1

class PagingSource @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val query: String
) : PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = unsplashApi.searchPhotos(query, position, params.loadSize)

            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}