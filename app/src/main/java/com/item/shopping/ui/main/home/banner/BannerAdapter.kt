package com.item.shopping.ui.main.home.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.item.shopping.R
import com.item.shopping.databinding.ItemHomeHeaderBannerBinding
import com.item.shopping.domain.model.Banner

/**
 * 메인화면 배너 어뎁터.
 */
class BannerAdapter: ListAdapter<Banner, BannerAdapter.ViewHolder>(ItemDiffCallback()) {

    private var actualSize = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_home_header_banner,
                parent,
                false
            )
        )
    }

    /**
     * 무한 스크롤 구현. 실제 리스트를 가지고 좌우에 마지막 엘레먼트 + 원래 리스트 + 첫 엘레먼트
     * 를 추가함.
     */
    override fun submitList(list: List<Banner>?) {
        val infiniteList = list?.let {
            actualSize = it.size
            listOf(it.last()) + it + listOf(it.first())
        } ?: run {
            list
        }
        super.submitList(infiniteList)
    }

    /**
     * 실제 배너 아이템 카운트
     */
    fun actualItemCount():Int = actualSize

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position % actualItemCount()))
    }

    inner class ViewHolder(private val binding:ItemHomeHeaderBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(banners:Banner) {
            binding.setVariable(BR.banner, banners)
            binding.executePendingBindings()
        }
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem == newItem
        }
    }
}