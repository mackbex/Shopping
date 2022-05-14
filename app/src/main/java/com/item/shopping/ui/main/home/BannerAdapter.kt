package com.item.shopping.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.item.shopping.R
import com.item.shopping.databinding.ItemBannerBinding
import com.item.shopping.domain.model.Banner

class BannerAdapter: ListAdapter<Banner, BannerAdapter.ViewHolder>(ItemDiffCallback()) {

    private var listener: ((banner:Banner, binding:ViewDataBinding) -> Unit)? = null
    private var actualSize = 0



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_banner,
                parent,
                false
            )
        )
    }

    override fun submitList(list: List<Banner>?) {
        val infiniteList = list?.let {
            actualSize = it.size
            listOf(it.last()) + it + listOf(it.first())
        } ?: run {
            list
        }
        super.submitList(infiniteList)
    }


    fun actualItemCount():Int = actualSize

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position % actualItemCount()))
    }

    fun setPostInterface(listener: ((banner:Banner, binding:ViewDataBinding) -> Unit)?) {
        this.listener = listener
    }

    inner class ViewHolder(private val binding:ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(banner:Banner) {
            binding.setVariable(BR.banner, banner)
            listener?.invoke(banner, binding)
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