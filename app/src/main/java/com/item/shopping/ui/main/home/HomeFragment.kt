package com.item.shopping.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.transition.MaterialFadeThrough
import com.item.shopping.databinding.FragmentHomeBinding
import com.item.shopping.util.autoCleared
import com.item.shopping.util.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class HomeFragment:Fragment() {

    private var binding: FragmentHomeBinding by autoCleared()
    private val viewModel: HomeViewModel by viewModels()
    private val bannerAdapter:BannerAdapter by lazy { BannerAdapter() }
    private val goodsAdapter:GoodsAdapter by lazy { GoodsAdapter() }


    private lateinit var autoScrollBannerJob: Job


    companion object {
        const val AUTO_SCROLL_BANNER_INTERVAL = 3000L
    }

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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            viewModel = this@HomeFragment.viewModel
            lifecycleOwner = viewLifecycleOwner


            with(vpBanner) {
                adapter = bannerAdapter
                offscreenPageLimit = 1
                registerOnPageChangeCallback(vpBannerScrollCallback)

                setCurrentItem(1, false)
                scrollJobCreate()
            }

            rcGoods.adapter = goodsAdapter
//            rcGoods.setHasFixedSize(true)
        }

        initStates()
    }

    private fun initStates() {
        viewModel.bannerLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    bannerAdapter.submitList(it.data.banners).apply {
                        binding.vpBanner.setCurrentItem(1, false)
                    }
                    goodsAdapter.submitList(it.data.goods)
                }
                is Resource.Failure -> {

                }
            }
        }
    }


    fun scrollJobCreate() {
        autoScrollBannerJob = viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            delay(AUTO_SCROLL_BANNER_INTERVAL)
            binding.vpBanner.setCurrentItem(binding.vpBanner.currentItem + 1, true)
        }
    }

    //무한스크롤 구현. 좌우 lastItem + FirstItem .
    private val vpBannerScrollCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            if (positionOffsetPixels != 0) {
                return
            }
            when (position) {
                0 -> binding.vpBanner.setCurrentItem(bannerAdapter.itemCount - 2, false)
                bannerAdapter.itemCount - 1 -> binding.vpBanner.setCurrentItem(1, false)
            }

        }
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)

            when (state) {
                ViewPager2.SCROLL_STATE_IDLE -> if(!autoScrollBannerJob.isActive) scrollJobCreate()
                ViewPager2.SCROLL_STATE_DRAGGING -> if(::autoScrollBannerJob.isInitialized) autoScrollBannerJob.cancel()
                ViewPager2.SCROLL_STATE_SETTLING -> {}
            }
        }
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            //BottomNavigation 전환 후의 현재 textView 텍스트 유지 포함.
            binding.vpBanner.post {
                if(position > 0 && position < bannerAdapter.itemCount - 1) {
                    binding.tvBannerIndex.text = "${(binding.vpBanner.currentItem - 1 % bannerAdapter.actualItemCount()).plus(1)}/${bannerAdapter.actualItemCount()}"
                }
            }
        }
    }
}