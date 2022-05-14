package com.item.shopping.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.transition.MaterialFadeThrough
import com.item.shopping.R
import com.item.shopping.databinding.FragmentHomeBinding
import com.item.shopping.ui.main.home.banner.BannerAdapter
import com.item.shopping.util.autoCleared
import com.item.shopping.util.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class HomeFragment:Fragment() {

    private var binding: FragmentHomeBinding by autoCleared()
    private val viewModel: HomeViewModel by viewModels()
    private val headerAdapter:HeaderAdapter by lazy { HeaderAdapter() }
    private val goodsAdapter:GoodsAdapter by lazy { GoodsAdapter() }
//    private val bannerAdapter:BannerAdapter by lazy { BannerAdapter() }
    private val concatAdapter:ConcatAdapter by lazy {
        ConcatAdapter(
            ConcatAdapter.Config.Builder()
                .setIsolateViewTypes(false)
                .build(),
            headerAdapter,
            goodsAdapter)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            viewModel = this@HomeFragment.viewModel
            lifecycleOwner = viewLifecycleOwner

            /**
             * 스와이프
             */
            swipeHome.setOnRefreshListener {
                this@HomeFragment.viewModel.getMainItems()
                swipeHome.isRefreshing = false
            }

            with(rcMain) {
                adapter = concatAdapter
                (layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (concatAdapter.getItemViewType(position)) {
                            R.layout.item_main_goods -> 1
                            R.layout.item_main_header -> 2
                            else -> 1
                        }
                    }
                }
            }
            rcMain.adapter = concatAdapter
//            rcGoods.setHasFixedSize(true)
        }

        initStates()
    }

    private fun initStates() {
        viewModel.bannerLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    headerAdapter.setItem(it.data.banners, this@HomeFragment)
                    goodsAdapter.submitList(it.data.goods)
                }
                is Resource.Failure -> {

                }
            }
        }
    }
}