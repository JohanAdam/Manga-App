package com.example.android.marsrealestate.network

import com.example.android.marsrealestate.model.AnimesNetworkEntity
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

enum class ApiFilter(val value: String) {
    SHOW_RATED_G("g"),
    SHOW_RATED_PG("pg"),
    SHOW_RATED_PG13("pg13"),
    SHOW_RATED_R17("r17"),
    SHOW_RATED_R("r"),
    SHOW_RATED_RX("rx"),
}

interface NetworkService {

    @GET("search/anime")
    suspend fun getAnimeListByRate(
            @Query("rated") type: String?): AnimesNetworkEntity

}