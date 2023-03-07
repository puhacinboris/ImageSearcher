package com.borispuhacin.imagesearcher.utils

import com.borispuhacin.imagesearcher.BuildConfig

interface Constants {
    companion object {
        const val BASE_URL = "https://api.unsplash.com/"

        /** Here goes ACCESS KEY which is in gradle.properties file */
        const val CLIENT_ID = BuildConfig.UNSPLASH_ACCESS_KEY

        const val DEFAULT_QUERY = "android"
    }
}