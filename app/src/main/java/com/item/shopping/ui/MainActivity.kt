package com.item.shopping.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.plusAssign
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.item.shopping.R
import com.item.shopping.databinding.ActivityMainBinding
import com.item.shopping.util.KeepStateNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    /**
     * Navogiation Component + BottomNavigationView 조합.
     * 기본적으로 BottomNavigationView를 Fragment로 구현할 시, Fragment가 전환 될 때마다 갱신되기 때문에,
     * Paging 재호출로 인한 Remote 리소스 낭비, 스크롤 초기화 문제 해결위해
     * 커스텀 Navigator를 통해 Fragment를 Hide, show 방식으로 호출함.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.navBottom.setupWithNavController(navController)

    }
}