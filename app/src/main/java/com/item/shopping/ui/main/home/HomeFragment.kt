package com.item.shopping.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.item.shopping.R
import com.item.shopping.databinding.FragmentHomeBinding
import com.item.shopping.util.autoCleared
import com.item.shopping.util.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment:Fragment() {

    private var binding: FragmentHomeBinding by autoCleared()
    private val viewModel: HomeViewModel by viewModels()

    private val headerAdapter:HeaderAdapter by lazy { HeaderAdapter() }
    private val goodsAdapter:GoodsAdapter by lazy { GoodsAdapter() }
    private val concatAdapter:ConcatAdapter by lazy {
        ConcatAdapter(
            ConcatAdapter.Config.Builder()
                .setIsolateViewTypes(false)
                .build(),
            headerAdapter,
            goodsAdapter
        )
    }

    private lateinit var goodsPagingJob:Job

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
            vm = this@HomeFragment.viewModel
            lifecycleOwner = viewLifecycleOwner

            /**
             * Refresh
             */
            swipeHome.setOnRefreshListener {
                this@HomeFragment.viewModel.getMainItems()
                goodsAdapter.refresh()
            }

            with(rcMain) {
                adapter = concatAdapter

                goodsAdapter.setPostInterface { goods, goodsBinding ->
                    goodsBinding.btnFavorite.setOnClickListener {
                        this@HomeFragment.viewModel.updateFavorite(goods)
                    }
                }

                /**
                 * to do : Paging 도중 로딩 실패 시를 담당하는 어뎁터 추가.
                 * 현재 concat으로 banner, goods를 표시하고 있기 때문에,
                 * goods pagingAdapter에 withLoadStateFooter를 넣을 경우
                 * NestedConcat이기 때문에 itemViewType이 정상적으로 작동하지 않음.
                 * 따라서, addLoadStateListener를 통해 로딩 상태를 확인 하고,
                 * 관련 상태 UI 어뎁터를 메인 ConcatAdapter에 추가해야 함.
                 */
                goodsAdapter.addLoadStateListener {
                    if (it.append is LoadState.NotLoading) {
                        swipeHome.isRefreshing = false
                    }
                    if(it.append is LoadState.Error || it.prepend is LoadState.Error) {
                        Toast.makeText(requireContext(), getString(R.string.err_failed_load_data), Toast.LENGTH_SHORT).show()
                    }
                }

                //footer
                (layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (concatAdapter.getItemViewType(position)) {
                            R.layout.item_home_goods -> 1
                            R.layout.item_home_header -> 2
                            else -> 1
                        }
                    }
                }
            }

        }

        initObservers()
    }

    private fun initObservers() {
        viewModel.updateFavoriteLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    val itemIdx = goodsAdapter.snapshot().indexOfFirst { snapshot ->
                        snapshot?.id == it.data.id
                    }
                    if(itemIdx > -1) {
                        goodsAdapter.snapshot().items[itemIdx].isFavorite = it.data.isFavorite
                        goodsAdapter.notifyItemChanged(itemIdx)
                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), getString(R.string.err_failed_set_favorite), Toast.LENGTH_SHORT).show()
                }
            }

            binding.swipeHome.isRefreshing = false
        }

        viewModel.bannerLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    headerAdapter.setItem(it.data.banners, viewLifecycleOwner)

                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), getString(R.string.err_failed_load_data), Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.getGoods().collectLatest { pagingData ->
                        withContext(Dispatchers.Main) {
                            goodsAdapter.submitData(pagingData)
                        }
                    }
                }
            }
        }
    }
}