package com.borispuhacin.imagesearcher.network.api

import com.borispuhacin.imagesearcher.BuildConfig
import com.borispuhacin.imagesearcher.network.response.UnsplashResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"

        /** Here goes ACCESS KEY which is in gradle.properties file */
        const val CLIENT_ID = BuildConfig.UNSPLASH_ACCESS_KEY
    }

    @Headers(
        "Accept-Version: v1",
        "Authorization: Client-ID $CLIENT_ID"
    )
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): UnsplashResponse
}