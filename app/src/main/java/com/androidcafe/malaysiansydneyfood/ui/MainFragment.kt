package com.androidcafe.malaysiansydneyfood.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.androidcafe.malaysiansydneyfood.R
import com.androidcafe.malaysiansydneyfood.databinding.MainFragmentBinding
import com.androidcafe.malaysiansydneyfood.local.FoodDatabase
import com.androidcafe.malaysiansydneyfood.repository.FoodRepository
import com.androidcafe.malaysiansydneyfood.viewmodel.SharedFragmentViewModel
import com.androidcafe.malaysiansydneyfood.viewmodel.SharedFragmentViewModelFactory

class MainFragment : Fragment() {

    //activityViewModels is used so that view model can be shared across fragments
    private val viewModel: SharedFragmentViewModel by activityViewModels {
        val database = FoodDatabase.getInstance(requireContext())
        val repository = FoodRepository(database.dao)

        SharedFragmentViewModelFactory(requireActivity().application, repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = MainFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = CardRecycleViewAdapter(viewModel)
        binding.recyclerView.adapter = adapter

        /* Note: already handled by bindRecyclerView() binding adapter
        viewModel.allCardDataList.observe(viewLifecycleOwner, { cardDataList ->
            adapter.submitList(cardDataList)
        })*/

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.main_fragment_search_menu, menu)

        (menu.findItem(R.id.action_search).actionView as SearchView).apply {

            queryHint = "Type here to search"
            setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    viewModel.setSearchInfo(query, false)

                    findNavController().navigate(R.id.action_main_fragment_to_search_result_fragment)

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
    }
}