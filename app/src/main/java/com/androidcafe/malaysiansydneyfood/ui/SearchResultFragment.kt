package com.androidcafe.malaysiansydneyfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.androidcafe.malaysiansydneyfood.databinding.SearchResultFragmentBinding
import com.androidcafe.malaysiansydneyfood.local.FoodDatabase
import com.androidcafe.malaysiansydneyfood.repository.FoodRepository
import com.androidcafe.malaysiansydneyfood.viewmodel.SharedFragmentViewModel
import com.androidcafe.malaysiansydneyfood.viewmodel.SharedFragmentViewModelFactory
import kotlinx.coroutines.launch

class SearchResultFragment : Fragment() {

    private val viewModel: SharedFragmentViewModel by activityViewModels {
        val database = FoodDatabase.getInstance(requireContext())
        val repository = FoodRepository(database.foodDao())

        SharedFragmentViewModelFactory(requireActivity().application, repository)
    }

    //private val binding: SearchResultFragmentBinding by lazy {
    //    SearchResultFragmentBinding.inflate(layoutInflater)
    //}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = SearchResultFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = CardRecycleViewAdapter(viewModel)
        //doesn't work, no refresh
        //findNavController().currentDestination!!.label = "test"
        lifecycleScope.launch {
            viewModel.refreshSearchResultData()
            binding.invalidateAll()
        }

        return binding.root
    }
}