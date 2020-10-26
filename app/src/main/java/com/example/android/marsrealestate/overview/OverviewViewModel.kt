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
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.android.marsrealestate.model.Anime
import com.example.android.marsrealestate.network.ApiFilter
import com.example.android.marsrealestate.repository.MainRepository
import com.example.android.marsrealestate.utils.DataState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

enum class ApiStatus {
    LOADING,
    ERROR,
    DONE
}

sealed class MainStateEvent {
    object GetAnimesEvents: MainStateEvent()
}

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel
    @ViewModelInject
    constructor(
            private val mainRepository: MainRepository,
            @Assisted private val savedStateHandle: SavedStateHandle
    ): ViewModel() {

    companion object {
        private const val TAG = "OverviewViewModel"
    }

    private val _dataState: MutableLiveData<DataState<List<Anime>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<Anime>>>
        get() = _dataState

    //Event for detail page.
    private val _navigateToSelectedProperty = MutableLiveData<Anime>()
    val navigateToSelectedProperty: LiveData<Anime>
        get() = _navigateToSelectedProperty

    /**
     * Call getAnimeListByRated() on init so we can display status immediately.
     */
    init {
        getAnimes(ApiFilter.SHOW_RATED_G)
    }

    fun getAnimes(filter: ApiFilter) {
        Log.d(TAG, "getAnimes " + filter.value)
        setStateEvent(filter, MainStateEvent.GetAnimesEvents)
    }

    /**
     * Events that happen in this view models.
     */
    private fun setStateEvent(filter: ApiFilter, mainStateEvent: MainStateEvent) {
        Log.d(TAG, "setStateEvent")

        viewModelScope.launch {
            when(mainStateEvent) {
                is MainStateEvent.GetAnimesEvents -> {
                    Log.d(TAG, "setStateEvent GetAnimesEvents")
                    mainRepository.getTopAnimes()
                            .onEach { dataState ->
                                _dataState.value = dataState
                            }
                            .launchIn(viewModelScope)
                }
            }
        }
    }

    fun updateFilter(filter: ApiFilter) {
        Log.e("e","updateFilter")
        getAnimes(filter)
    }

    fun openPropertyDetailPage(anime: Anime) {
        _navigateToSelectedProperty.value = anime
    }

    fun openPropertyDetailPageCompleted() {
        _navigateToSelectedProperty.value = null
    }

    override fun onCleared() {
        super.onCleared()
        //Cancel the api call then viewmodel destroy.
        viewModelScope.cancel()
//        viewModelJob.cancel()
    }
}
