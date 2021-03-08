package com.nyan.android.animeme.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Anime(
        var id: Int?,
        var url: String?,
        var image_url: String?,
        var title: String?,
        var airing: String?,
        val synopsis: String?,
        val type: String?,
        val episode: Int?,
        val rated: String?,
        val end_date: String?) : Parcelable {

    val isCompleted
        get() = end_date != null

}