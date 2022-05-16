package com.item.shopping.ui.main.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.item.shopping.R
import com.item.shopping.databinding.FragmentFavoriteBinding
import com.item.shopping.domain.model.mapToGoods
import com.item.shopping.ui.main.SharedViewModel
import com.item.shopping.util.autoCleared
import com.item.shopping.util.customview.PagingLoadStateAdapter
import com.item.shopping.util.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding by autoCleared()
    private val viewModel: FavoriteViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val favoriteAdapter: FavoriteAdapter by lazy { FavoriteAdapter() }

    private var prevFavoriteItemCnt = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
            /**
             * Refresh
             */
            swipeFavorite.setOnRefreshListener {
                favoriteAdapter.refresh()
            }

            with(rcFavorite) {
                setHasFixedSize(true)
                adapter = favoriteAdapter.apply {
                    addLoadStateListener {
                        if(it.append is LoadState.NotLoading) {
                            swipeFavorite.isRefreshing = false
                            layoutEmptyFavorite.visibility = if(snapshot().size <= 0) View.VISIBLE else View.GONE
                        }
                    }

                    withLoadStateFooter(
                        footer = PagingLoadStateAdapter(this)
                    )

                    setPostInterface { favorite, favoriteItemBinding ->
                        favoriteItemBinding.btnFavorite.setOnClickListener {
                            sharedViewModel.updateFavorite(favorite.mapToGoods())
                        }
                    }
                }
            }
        }

        initObservers()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            prevFavoriteItemCnt = favoriteAdapter.snapshot().size
        }
        else {
            if(prevFavoriteItemCnt != favoriteAdapter.snapshot().size) {
                binding.rcFavorite.scrollToPosition(0)
            }
        }
    }

    private fun initObservers() {

        sharedViewModel.updateFavoriteLiveData.observe(viewLifecycleOwner) {
            when(it){
                is Resource.Success -> {
                    favoriteAdapter.refresh()
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), getString(R.string.err_failed_set_favorite), Toast.LENGTH_SHORT).show()
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.getFavorites().collectLatest { pagingData ->
                        withContext(Dispatchers.Main) {
                            favoriteAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
                        }
                    }
                }
            }
        }
    }
}