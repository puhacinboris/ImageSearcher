package com.borispuhacin.imagesearcher.di

import com.borispuhacin.imagesearcher.network.api.UnsplashApi
import com.borispuhacin.imagesearcher.repository.UnsplashRepository
import com.borispuhacin.imagesearcher.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesUnsplashApi() : UnsplashApi =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory()).build()
            ))
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(UnsplashApi::class.java)

    @Provides
    @Singleton
    fun providesRepository(unsplashApi: UnsplashApi) = UnsplashRepository(unsplashApi)
}