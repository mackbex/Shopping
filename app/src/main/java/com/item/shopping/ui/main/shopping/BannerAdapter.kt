package com.item.shopping.ui.main.shopping

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
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