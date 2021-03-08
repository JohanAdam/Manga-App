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

package com.nyan.android.animeme.overview

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nyan.android.animeme.R
import com.nyan.android.animeme.databinding.FragmentOverviewBinding
import com.nyan.android.animeme.model.Anime
import com.nyan.android.animeme.network.ApiFilter
import com.nyan.android.animeme.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_overview.*

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    companion object {
        private const val TAG = "OverviewFragment"
    }

    /**
     * Lazily initialize our [OverviewViewModel].
     */
//    private val viewModel: OverviewViewModel by lazy {
//        ViewModelProviders.of(this).get(OverviewViewModel::class.java)
//    }
    private val viewModel: OverviewViewModel by viewModels()

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //The xml file name is created in auto generated class.
        //ex : fragment_overview > FragmentOverviewBinding.class
        val binding = FragmentOverviewBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
//        binding.setLifecycleOwner(this)

        // Giving the binding access to the OverviewViewModel
//        binding.viewModel = viewModel

        //Pointing the recycler view the adapter.
        binding.recyclerView.adapter = AnimeListAdapter(AnimeListAdapter.OnClickListener {
            Log.d("d","Click click")
            viewModel.openPropertyDetailPage(it)
        })

        //Set an observer to listen for go to new page.
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            it?.let {
                //Navigate to detail page.
                this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
                //Mark event as completed in view model.
                viewModel.openPropertyDetailPageCompleted()
            }
        })

        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Anime>> -> {
                    Log.d(TAG, "Success :" )
                    displayProgressBar(false)
                    displayData(dataState.data)
                }
                is DataState.Failed -> {
                    showError()
                    dataState.exception.printStackTrace()
                    Log.e(TAG, "Error :" + dataState.exception.message)
                }
                is DataState.Loading -> {
                    Log.d(TAG, "Loading..")
                    displayProgressBar(true)
                }
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun displayData(animes: List<Anime>) {
        val adapter = recycler_view.adapter as AnimeListAdapter
        //submit list of data to the adapter.
        adapter.submitList(animes)
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        if (isDisplayed) {
            (recycler_view.adapter as AnimeListAdapter).submitList(null)

            iv_status.visibility = View.VISIBLE
            iv_status.setImageResource(R.drawable.loading_animation)
            iv_status.bringToFront()
        } else {
            iv_status.visibility = View.GONE
        }
    }

    private fun showError() {
        iv_status.visibility = View.VISIBLE
        iv_status.setImageResource(R.drawable.ic_connection_error)
        iv_status.bringToFront()
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
                when(item.itemId) {
                    R.id.show_pg -> ApiFilter.SHOW_RATED_PG
                    R.id.show_pg13 -> ApiFilter.SHOW_RATED_PG13
                    R.id.show_r -> ApiFilter.SHOW_RATED_R17
                    R.id.show_r_plus -> ApiFilter.SHOW_RATED_R
                    R.id.show_rx -> ApiFilter.SHOW_RATED_RX
                    else -> ApiFilter.SHOW_RATED_G
                }
        )
        return true
    }

}
