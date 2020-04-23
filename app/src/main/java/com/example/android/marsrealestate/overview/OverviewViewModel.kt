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

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.ApiFilter
import com.example.android.marsrealestate.network.ApiObj
import com.example.android.marsrealestate.network.MangaItemProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

enum class ApiStatus {
    LOADING,
    ERROR,
    DONE
}

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    //Variable for coroutines job & a CoroutineScope using the Main Dispatcher.
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //Status of the API.
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    //The Manga List.
    private val _result = MutableLiveData<List<MangaItemProperty>>()
    val result: LiveData<List<MangaItemProperty>>
        get() = _result

    //Event for detail page.
    private val _navigateToSelectedProperty = MutableLiveData<MangaItemProperty>()
    val navigateToSelectedProperty: LiveData<MangaItemProperty>
        get() = _navigateToSelectedProperty

    /**
     * Call getAnimeListByRated() on init so we can display status immediately.
     */
    init {
        getAnimeListByRated(ApiFilter.SHOW_RATED_G)
    }

    /**
     * Sets the value of the status LiveData to the API status.
     */
    private fun getAnimeListByRated(filter: ApiFilter) {
        //Call using coroutines.
        coroutineScope.launch {
            var getMangaListDeferred = ApiObj.retrofitService.searchManga(filter.value)
            //The list will be return to this variable when ready.
            try {
                _status.value = ApiStatus.LOADING

                var response = getMangaListDeferred.await()

                _status.value = ApiStatus.DONE
                _result.value = response.resultList

            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = ApiStatus.ERROR
                _result.value = ArrayList()
            }
        }
    }

    fun updateFilter(filter: ApiFilter) {
        Log.e("e","updateFilter")
        getAnimeListByRated(filter)
    }

    fun openPropertyDetailPage(mangaItemProperty: MangaItemProperty) {
        _navigateToSelectedProperty.value = mangaItemProperty
    }

    fun openPropertyDetailPageCompleted() {
        _navigateToSelectedProperty.value = null
    }

    override fun onCleared() {
        super.onCleared()
        //Cancel the api call then viewmodel destroy.
        viewModelJob.cancel()
    }
}
