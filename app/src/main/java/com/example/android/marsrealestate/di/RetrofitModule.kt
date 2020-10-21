package com.example.android.marsrealestate.di

import com.example.android.marsrealestate.network.NetworkService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule  {

    @Singleton
    @Provides
    fun providesGsonBuilder() : Gson {
        return GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
    }

    private fun getClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
                .baseUrl("https://api.jikan.moe/v3/")
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun providesNetworkService(retrofit: Retrofit.Builder): NetworkService {
        return retrofit
                .build()
                .create(NetworkService::class.java)
    }

}