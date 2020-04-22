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

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.ApiObj
import com.example.android.marsrealestate.network.MangaItemProperty
import com.example.android.marsrealestate.network.MangaProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the request status String
    val response: LiveData<String>
        get() = _response

    //Variable for coroutines job & a CoroutineScope using the Main Dispatcher.
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _result = MutableLiveData<MangaItemProperty>()
    val result: LiveData<MangaItemProperty>
        get() = _result

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getLatestTopMangaList()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getLatestTopMangaList() {
        //Call the retrofit.

        //Call using coroutines.
        coroutineScope.launch {
            var getMangaListDeferred = ApiObj.retrofitService.getTopManga()
            //The list will be return to this variable when ready.
            try {
                var response = getMangaListDeferred.await()
                _response.value = "Total loaded : ${response.mangaList.size}"

                if (response.mangaList.size > 0) {
                    _result.value = response.mangaList[0]
                }

            } catch (e: Exception) {
                _response.value = "Failure : ${e.message}"

                _status.value = "Failure : ${e.message}"
            }
        }

//        ApiObj.retrofitService.getTopManga().enqueue(object : Callback<MangaProperty> {
//            override fun onFailure(call: Call<MangaProperty>, t: Throwable) {
//                t.printStackTrace()
//                _response.value = "Failure : " + t.message
//            }
//
//            override fun onResponse(call: Call<MangaProperty>, response: Response<MangaProperty>) {
//                _response.value = "Total loaded : ${response.body()?.mangaList?.size}"
//            }
//
//        })
    }

    override fun onCleared() {
        super.onCleared()
        //Cancel the api call then viewmodel destroy.
        viewModelJob.cancel()
    }
}
