package com.item.shopping.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

/**
 * binding 처리 및 구조 base Activity (사용안함
 */
abstract class BaseActivity<V : ViewDataBinding>(
    private val bindingFactory:(LayoutInflater) -> V
) : AppCompatActivity() {

    private lateinit var binding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    abstract fun initStartView()
    abstract fun initDataBinding()
    abstract fun initAfterBinding()

}