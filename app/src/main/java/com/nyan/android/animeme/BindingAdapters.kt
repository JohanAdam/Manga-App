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

package com.nyan.android.animeme

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nyan.android.animeme.model.Anime
import com.nyan.android.animeme.overview.AnimeListAdapter

/**
 * @param imgView To tell only ImageView can use this method.
 * @param imgUrl Url of the img to show.
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        //Convert String to Uri and append https.
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}

/**
 * Binding adapter for RecyclerView.
 * Attach adapter with a list direct to the recycler view.
 */
@BindingAdapter("attachListData")
fun bindAdapterToRecyclerView(recyclerView: RecyclerView, data: List<Anime>?) {
    //Attach new adapter to rv.
    val adapter = recyclerView.adapter as AnimeListAdapter
    //submit list of data to the adapter.
    adapter.submitList(data)
}

/**
 * Binding adapter to image view status. To show current status of API in UI.
 */
//@BindingAdapter("bindApiStatus")
//fun bindAPIStatus(imageView: ImageView, status: DataState?) {
//    when (status) {
//        ApiStatus.LOADING -> {
//            imageView.visibility = View.VISIBLE
//            imageView.setImageResource(R.drawable.loading_animation)
//            imageView.bringToFront()
//        }
//        ApiStatus.ERROR -> {
//            imageView.visibility = View.VISIBLE
//            imageView.setImageResource(R.drawable.ic_connection_error)
//        }
//        ApiStatus.DONE -> {
//            imageView.visibility = View.GONE
//        }
//    }
//}

