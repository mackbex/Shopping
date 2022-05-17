package com.item.shopping.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.item.shopping.R
import com.item.shopping.databinding.ItemHomeGoodsBinding
import com.item.shopping.domain.model.Goods

/**
 * 메인 상품 어뎁터
 */
class GoodsAdapter: PagingDataAdapter<Goods, GoodsAdapter.ViewHolder>(ItemDiffCallback()) {

    private var listener: ((goods:Goods, binding:ItemHomeGoodsBinding) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_home_goods,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    fun setPostInterface(listener: ((goods:Goods, binding:ItemHomeGoodsBinding) -> Unit)?) {
        this.listener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_home_goods
    }


    inner class ViewHolder(private val binding:ItemHomeGoodsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(goods:Goods) {
            binding.setVariable(BR.goods, goods)
            listener?.invoke(goods, binding)
            binding.executePendingBindings()
        }
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<Goods>() {
        override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean {
            return oldItem.id == newItem.id && oldItem.isFavorite == newItem.isFavorite
        }

        override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean {
            return oldItem == newItem
        }
    }
}