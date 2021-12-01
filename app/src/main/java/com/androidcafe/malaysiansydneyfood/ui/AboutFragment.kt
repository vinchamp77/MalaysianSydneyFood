package com.androidcafe.malaysiansydneyfood.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.androidcafe.malaysiansydneyfood.R
import com.androidcafe.malaysiansydneyfood.databinding.AboutFragmentBinding
import com.androidcafe.malaysiansydneyfood.local.FoodDatabase
import com.androidcafe.malaysiansydneyfood.repository.FoodRepository
import com.androidcafe.malaysiansydneyfood.viewmodel.SharedFragmentViewModel
import com.androidcafe.malaysiansydneyfood.viewmodel.SharedFragmentViewModelFactory
import com.google.android.material.snackbar.Snackbar

class AboutFragment : Fragment() {

    private val viewModel: SharedFragmentViewModel by activityViewModels {
        val database = FoodDatabase.getInstance(requireContext())
        val repository = FoodRepository(database.dao)

        SharedFragmentViewModelFactory(requireActivity().application, repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = AboutFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel

        binding.websiteButton.setOnClickListener {
            startActivityUrlIntent(getString(R.string.website_url))
        }

        binding.developedByTextView.movementMethod = LinkMovementMethod.getInstance()

        return binding.root
    }

    private fun startActivityUrlIntent(urlStr: String) {
        val uri: Uri = Uri.parse(urlStr)
        val intent = Intent(Intent.ACTION_VIEW, uri)

        intent.setPackage("com.android.chrome")
        try {
            startActivity(intent)

        } catch (e: ActivityNotFoundException) {

            intent.setPackage(null)
            try {
                startActivity(intent)

            } catch (e: ActivityNotFoundException) {
                val snack = Snackbar.make(
                    requireView(),
                    getString(R.string.no_web_browser_found_msg),
                    Snackbar.LENGTH_LONG)
                snack.show()
            }
        }
    }
}