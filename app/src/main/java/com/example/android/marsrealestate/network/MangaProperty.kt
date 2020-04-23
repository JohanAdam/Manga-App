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

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class MangaProperty(
        //Search manga.
        @Json(name = "results")
        val resultList: List<MangaItemProperty>
)

data class MangaPropertyTop(
//Top manga.
        @Json(name = "top")
        val mangaList: List<MangaItemProperty>)

@Parcelize
data class MangaItemProperty(
        val mal_id: Int?,
        val rank: Int?,
        val title: String?,
        val url: String?,
        val type: String?,
        val volumes: String?,
        val start_date: String?,
        val end_date: String?,
        val members: Int?,
        val score: Double?,
        val image_url: String?) : Parcelable {

        val isCompleted
                get() = end_date != null
}
