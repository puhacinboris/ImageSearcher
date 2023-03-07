package com.borispuhacin.imagesearcher.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashPhoto(
    val id: String,
    val description: String?,
    val user: User,
    val urls: Urls

) : Parcelable {
    @Parcelize
    data class Urls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    ) : Parcelable

    @Parcelize
    data class User(
        val username: String,
        val name: String,
        val profile_image: ProfileImg
    ) : Parcelable {
        /** attributionUrl is required by Unsplash */
        val attributionUrl get() = "http://unsplash.com/$username?utm_source=ImageSearcher&utm_medium=referral"

        @Parcelize
        data class ProfileImg(
            val small: String,
            val medium: String,
            val large: String,
        ) : Parcelable
    }
}
