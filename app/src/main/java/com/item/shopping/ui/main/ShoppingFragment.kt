package com.item.shopping.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialFadeThrough
import com.item.shopping.databinding.FragmentShoppingBinding
import com.item.shopping.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingFragment:Fragment() {

    private var binding: FragmentShoppingBinding by autoCleared()

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
}