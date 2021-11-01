package com.example.rickandmorty.di

import com.example.rickandmorty.data.BASEURL
import com.example.rickandmorty.data.RickAndMortyRepositoryImpl
import com.example.rickandmorty.data.network.RickAndMortyService
import com.example.rickandmorty.domain.RickAndMortyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(apiService: RickAndMortyService): RickAndMortyRepository =
        RickAndMortyRepositoryImpl(apiService)

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): RickAndMortyService =
        retrofit.create(RickAndMortyService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}