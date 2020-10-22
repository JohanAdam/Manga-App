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

package com.example.android.marsrealestate.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.marsrealestate.bindAdapterToRecyclerView
import com.example.android.marsrealestate.bindImage
import com.example.android.marsrealestate.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * This [Fragment] will show the detailed information about a selected piece of Mars real estate.
 */
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
//        val application = requireNotNull(activity).application

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        //Retrieve data sent to this page.
        val dataReceived = DetailFragmentArgs.fromBundle(requireArguments()).selectedManga
        viewModel.init(dataReceived)
        //Bind the view model created to xml view model.
//        binding.viewModel = viewModel

        viewModel.selectedManga.observe(viewLifecycleOwner, Observer {

            //Change image.
            bindImage(main_photo_image, it.image_url)

            //Change title.
            property_type_text.text = it.title

            //Change completed status.
            price_value_text.text = viewModel.getStatus()

        })

        return binding.root
    }
}