package com.example.android.marsrealestate.repository

import android.util.Log
import com.example.android.marsrealestate.model.Anime
import com.example.android.marsrealestate.network.NetworkMapper
import com.example.android.marsrealestate.network.NetworkService
import com.example.android.marsrealestate.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MainRepository

constructor(
        private val networkService: NetworkService,
        private val networkMapper: NetworkMapper) {

    companion object {
        private const val TAG = "MainRepository"
    }

    suspend fun getAnimes(filter: String): Flow<DataState<List<Anime>>> = flow {
        Log.d(TAG, "getAnimes")

        //Return loading.
        emit(DataState.Loading)

        try {
            //Get list from API.
            val animesNetEntity = networkService.getAnimeListByRate(filter)

            //Convert network model TO domain model.
            val animes = networkMapper.mapFromEntityList(animesNetEntity)

            Log.d(TAG, "getAnimes: size " + animes.size)

            //Return result.
            emit(DataState.Success(animes))
        } catch (e : Exception) {
            e.printStackTrace()
            //Return error.
            emit(DataState.Failed(e))
        }
    }

}