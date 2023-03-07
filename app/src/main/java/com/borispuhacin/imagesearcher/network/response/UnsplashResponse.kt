package com.borispuhacin.imagesearcher.network.response

import com.borispuhacin.imagesearcher.data.UnsplashPhoto

data class UnsplashResponse(
    val results: List<UnsplashPhoto>
)
