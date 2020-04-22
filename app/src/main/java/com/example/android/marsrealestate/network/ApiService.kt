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
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

//private const val BASE_URL = "https://mars.udacity.com/"
private const val BASE_URL = "https://api.jikan.moe/v3/"

//Moshi builder.
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

//Retrofit builder.
private val retrofit = Retrofit.Builder()
        //For add coroutines support.
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

interface ApiService {

    @GET("top/manga")
    fun getTopManga():
            Deferred<MangaProperty>

}

//To Expose the retrofit to the rest of the App.
object ApiObj {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}


