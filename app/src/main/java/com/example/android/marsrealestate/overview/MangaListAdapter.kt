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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MangaItemProperty

class MangaListAdapter : ListAdapter<MangaItemProperty, MangaListAdapter.MangaListViewHolder>(DiffCallback) {

    companion object DiffCallback :DiffUtil.ItemCallback<MangaItemProperty>() {
        override fun areItemsTheSame(oldItem: MangaItemProperty, newItem: MangaItemProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MangaItemProperty, newItem: MangaItemProperty): Boolean {
            return oldItem.mal_id == newItem.mal_id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaListAdapter.MangaListViewHolder {
        return MangaListViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MangaListAdapter.MangaListViewHolder, position: Int) {
        val mangaItemProperty = getItem(position)
        holder.bind(mangaItemProperty)
    }

    class MangaListViewHolder(private var binding: GridViewItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(mangaItemProperty: MangaItemProperty) {
            binding.data  = mangaItemProperty
            binding.executePendingBindings()
        }
    }
}