package com.borispuhacin.imagesearcher.network.api

import com.borispuhacin.imagesearcher.BuildConfig
import com.borispuhacin.imagesearcher.network.response.UnsplashResponse
import com.borispuhacin.imagesearcher.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    @Headers(
        "Accept-Version: v1",
        "Authorization: Client-ID ${Constants.CLIENT_ID}"
    )
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): UnsplashResponse
}