package com.item.shopping.ui.main.favorite

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
import androidx.recyclerview.widget.RecyclerView
import com.item.shopping.R
import com.item.shopping.databinding.FragmentFavoriteBinding
import com.item.shopping.util.autoCleared
import com.item.shopping.util.customview.PagingLoadStateAdapter
import com.item.shopping.util.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding by autoCleared()
    private val viewModel: FavoriteViewModel by viewModels()

    private val favoriteAdapter: FavoriteAdapter by lazy { FavoriteAdapter() }


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
                    stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                    addLoadStateListener {
                        if(it.append is LoadState.NotLoading) {
                            swipeFavorite.isRefreshing = false
                            layoutEmptyFavorite.visibility = if(snapshot().size > 0) View.GONE else View.VISIBLE
                        }
                    }

                    withLoadStateFooter(
                        footer = PagingLoadStateAdapter(this)
                    )

                    setPostInterface { favorite, favoriteItemBinding ->
                        favoriteItemBinding.btnFavorite.setOnClickListener {
                            viewModel.updateFavorite(favorite)
                        }
                    }
                }
            }
        }

        initObservers()
    }

    private fun initObservers() {

        viewModel.updateFavoriteLiveData.observe(viewLifecycleOwner) {
            when(it){
                is Resource.Success -> {
                    favoriteAdapter.refresh()
//                    val itemIdx = favoriteAdapter.snapshot().indexOfFirst { snapshot ->
//                        snapshot?.id == it.data.id
//                    }
//                    if(itemIdx > -1) {
//                        favoriteAdapter.notifyItemRemoved(itemIdx)
//                    }
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
                            favoriteAdapter.submitData(pagingData)
                        }
                    }
                }
            }
        }
    }
}