package com.item.shopping.util.customview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.item.shopping.R
import com.item.shopping.databinding.LayoutNetworkPagingStateBinding

/**
 * Paging 프로그래스바 및 재시도 표시용 어댑터
 */
class PagingLoadStateAdapter<T:Any, HOLDER:RecyclerView.ViewHolder>(
    private val adapter: PagingDataAdapter<T,HOLDER>
): LoadStateAdapter<PagingLoadStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder =
         ViewHolder(
            LayoutNetworkPagingStateBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.layout_network_paging_state, parent, false)
            )
        ){ adapter.retry() }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) = holder.bind(loadState)

    class ViewHolder(
        private val binding: LayoutNetworkPagingStateBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState:LoadState) {
            binding.btnRetry.isVisible = loadState is LoadState.Error
            binding.tvStateError.isVisible = loadState is LoadState.Error
            binding.progress.isVisible = loadState is LoadState.Loading

            binding.btnRetry.setOnClickListener {
                retryCallback.invoke()
            }
        }
    }
}