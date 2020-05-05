/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.jikan.moe/v3/"

//Moshi builder.
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private fun getClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.apply {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    }
    return  OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
}


//Retrofit builder.
private val retrofit = Retrofit.Builder()
        //For add coroutines support.
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(getClient())
        .baseUrl(BASE_URL)
        .build()

//To Expose the retrofit to the rest of the App.
object ApiObj {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

enum class ApiFilter(val value: String) {
    SHOW_RATED_G("g"),
    SHOW_RATED_PG("pg"),
    SHOW_RATED_PG13("pg13"),
    SHOW_RATED_R17("r17"),
    SHOW_RATED_R("r"),
    SHOW_RATED_RX("rx"),
}

interface ApiService {

    @GET("top/manga")
    fun getTopManga():
            Deferred<MangaPropertyTop>

    //    @GET("search/manga?genre=4")
    @GET("search/anime?rated=g")
    fun searchManga(
            @Query("rated") type: String):
            Deferred<MangaProperty>

}


