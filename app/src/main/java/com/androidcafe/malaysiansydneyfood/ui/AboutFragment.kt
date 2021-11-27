package com.androidcafe.malaysiansydneyfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.androidcafe.malaysiansydneyfood.databinding.AboutFragmentBinding
import com.androidcafe.malaysiansydneyfood.local.FoodDatabase
import com.androidcafe.malaysiansydneyfood.repository.FoodRepository
import com.androidcafe.malaysiansydneyfood.viewmodel.SharedFragmentViewModel
import com.androidcafe.malaysiansydneyfood.viewmodel.SharedFragmentViewModelFactory

class AboutFragment : Fragment() {

    private val viewModel: SharedFragmentViewModel by activityViewModels {
        val database = FoodDatabase.getInstance(requireContext())
        val repository = FoodRepository(database.foodDao())

        SharedFragmentViewModelFactory(requireActivity().application, repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = AboutFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel

        return binding.root
    }
}