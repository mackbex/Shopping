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


/**
 * Favorite Fragment
 */
@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding by autoCleared()
    private val viewModel: FavoriteViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val favoriteAdapter: FavoriteAdapter by lazy { FavoriteAdapter() }

    //Favorite 리스트 비교용 해시코드
    private var prevFavoriteListHashCode = 0

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
                    //Paging에서 상품 로드 완료시 갯수를 체크하여 0개면 상품이 없다고 표시
                    addLoadStateListener {
                        if(
                            (it.append is LoadState.NotLoading || it.append is LoadState.Error)
                            || (it.prepend is LoadState.NotLoading || it.prepend is LoadState.Error)
                        ) {
                            binding.tvEmptyFavorite.visibility = if (favoriteAdapter.snapshot().size <= 0) View.VISIBLE else View.GONE
                            if (binding.swipeFavorite.isRefreshing) binding.swipeFavorite.isRefreshing = false
                        }
                    }
                    // 로드 실패 시 하단 Retry버튼 표시 및 로딩중일 시 Progressbar용 하단 추가어뎁터
                    withLoadStateFooter(
                        footer = PagingLoadStateAdapter(this)
                    )

                    //상품 이미지의 좋아요 클릭시의 이벤트리스너
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

    /**
     * Fragment가 화면에 표시될 때의 이벤트 정리.
     * Ex: 상품 화면에서 좋아요 수정이 일어나면, Favorite 화면은 최상단으로 이동.
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            prevFavoriteListHashCode = favoriteAdapter.snapshot().hashCode()
        }
        else {
            if(prevFavoriteListHashCode != favoriteAdapter.snapshot().hashCode()) {
                binding.rcFavorite.post {
                    binding.rcFavorite.scrollToPosition(0)
                }
            }
        }
    }

    /**
     * Observers 적용
     */
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

        //페이징 collect
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