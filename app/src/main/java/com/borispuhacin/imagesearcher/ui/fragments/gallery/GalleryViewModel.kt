package com.borispuhacin.imagesearcher.ui.fragments.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.borispuhacin.imagesearcher.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: UnsplashRepository
) : ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "android"
    }

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val photos = currentQuery.switchMap { query ->
        repository.getSearchResults(query).cachedIn(viewModelScope)
    }

    fun searchPhoto(query: String) {
        currentQuery.value = query
    }
}