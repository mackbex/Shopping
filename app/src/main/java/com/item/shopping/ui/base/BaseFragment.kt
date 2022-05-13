package com.item.shopping.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


/**
 * binding 처리 및 구조 base Fragment (사용안함
 */
abstract class BaseFragment<V: ViewDataBinding>(
    private val inflate:(LayoutInflater, ViewGroup?, Boolean) -> V
):Fragment() {

    private var _binding: V? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initStartView()
        initObservers()
        initAfterBinding()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun initStartView()
    abstract fun initObservers()
    abstract fun initAfterBinding()
}

