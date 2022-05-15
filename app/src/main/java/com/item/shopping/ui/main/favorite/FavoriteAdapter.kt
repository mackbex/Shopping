package com.item.shopping.ui.main.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.item.shopping.R
import com.item.shopping.databinding.ItemFavoriteGoodsBinding
import com.item.shopping.domain.model.Favorite

class FavoriteAdapter: PagingDataAdapter<Favorite, FavoriteAdapter.ViewHolder>(ItemDiffCallback()) {

    private var listener: ((goods: Favorite, binding: ItemFavoriteGoodsBinding) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_favorite_goods,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    fun setPostInterface(listener: ((favorite: Favorite, binding: ItemFavoriteGoodsBinding) -> Unit)?) {
        this.listener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_home_goods
    }


    inner class ViewHolder(private val binding: ItemFavoriteGoodsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            binding.setVariable(BR.favorite, favorite)
            listener?.invoke(favorite, binding)
            binding.executePendingBindings()
        }
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem == newItem
        }
    }
}