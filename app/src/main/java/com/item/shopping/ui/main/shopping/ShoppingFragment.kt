package com.item.shopping.ui.main.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.transition.MaterialFadeThrough
import com.item.shopping.databinding.FragmentShoppingBinding
import com.item.shopping.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShoppingFragment:Fragment() {

    private var binding: FragmentShoppingBinding by autoCleared()
    private val viewModel: ShoppingViewModel by viewModels()
    private val bannerAdapter:BannerAdapter by lazy { BannerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {

            rcBanner.adapter = bannerAdapter.apply {

            }

        }

        initStates()
    }

    private fun initStates() {

        viewModel.bannerLiveData.observe(viewLifecycleOwner) {
            bannerAdapter.submitList(it)
        }
    }
}