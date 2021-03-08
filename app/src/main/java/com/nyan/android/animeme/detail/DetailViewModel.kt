/*
 *  Copyright 2018, The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.nyan.android.animeme.detail

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nyan.android.animeme.R
import com.nyan.android.animeme.model.Anime

/**
 * The [ViewModel] that is associated with the [DetailFragment].
 */

class DetailViewModel
    @ViewModelInject
    constructor(
            private val app: Application,
            @Assisted private val savedStateHandle: SavedStateHandle
    ): ViewModel() {

    private val _selectedManga = MutableLiveData<Anime>()
    val selectedManga: LiveData<Anime>
        get() = _selectedManga

    //Transform the data before expose to xml.
    val displayMangaComplete = Transformations.map(selectedManga) {
        app.applicationContext.getString(R.string.title_status,
                when (it.isCompleted) {
                    true -> app.applicationContext.getString(R.string.title_complete)
                    false -> app.applicationContext.getString(R.string.title_inprogress)
                })
    }

    fun init(dataReceived: Anime) {
        _selectedManga.value = dataReceived
    }

    fun getStatus(): String {
        return app.applicationContext.getString(R.string.title_status,
                when (selectedManga.value?.isCompleted) {
                    true -> app.applicationContext.getString(R.string.title_complete)
                    false -> app.applicationContext.getString(R.string.title_inprogress)
                    else -> "null"
                })
    }

    override fun onCleared() {
        super.onCleared()
    }

}
