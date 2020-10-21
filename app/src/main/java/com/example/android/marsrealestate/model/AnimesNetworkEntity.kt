package com.example.android.marsrealestate.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AnimesNetworkEntity(
        @SerializedName("results")
        @Expose
        var result: List<AnimeNetworkEntity>
)

data class AnimeNetworkEntity(

        @SerializedName("mal_id")
        @Expose
        var id: Int?,

        @SerializedName("url")
        @Expose
        var url: String?,

        @SerializedName("image_url")
        @Expose
        var imageUrl: String?,

        @SerializedName("title")
        @Expose
        var title: String?,

        @SerializedName("airing")
        @Expose
        var airing: String?,

        @SerializedName("synopsis")
        @Expose
        var synopsis: String?,

        @SerializedName("type")
        @Expose
        var type: String?,

        @SerializedName("episode")
        @Expose
        var episode: Int?,

        @SerializedName("rated")
        @Expose
        var rated: String?,

        @SerializedName("end_date")
        @Expose
        var endDate: String?

)