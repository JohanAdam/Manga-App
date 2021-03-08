package com.nyan.android.animeme.network

import com.nyan.android.animeme.model.AnimesNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET ("top/anime/{page}")
    suspend fun getTopAnime(
            @Path("page") page: Int?): AnimesNetworkEntity

    @GET("search/anime")
    suspend fun getAnimeListByRate(
            @Query("rated") type: String?): AnimesNetworkEntity

}