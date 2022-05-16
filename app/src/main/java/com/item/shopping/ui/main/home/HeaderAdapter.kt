package com.item.shopping.ui.main.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.item.shopping.R
import com.item.shopping.databinding.ItemHomeHeaderBinding
import com.item.shopping.domain.model.Banner
import com.item.shopping.ui.main.home.banner.BannerAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class HeaderAdapter: RecyclerView.Adapter<HeaderAdapter.ViewHolder>() {

    private var banners:List<Banner> = listOf()
    private var listener: ((banners: List<Banner>, binding: ItemHomeHeaderBinding) -> Unit)? = null
    private val bannerAdapter by lazy { BannerAdapter() }
    private var viewPageStates: HashMap<Int, Int> = HashMap()


    private lateinit var autoScrollBannerJob:Job
    private lateinit var lifecycleOwner: LifecycleOwner

    companion object {
        //자동스크롤 타이머
        const val AUTO_SCROLL_BANNER_INTERVAL = 5000L
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_home_header,
                parent,
                false
            )
        )
    }

    /**
     * 헤더 특성상 배너 리스트를 전체로 받기 때문에, notifyDataSetChanged 호출
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setItem(list: List<Banner>, lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
        lifecycleOwner.lifecycle.removeObserver(observe)
        lifecycleOwner.lifecycle.addObserver(observe)
        banners = list
        notifyDataSetChanged()
    }

    /**
     * 자동스크롤 collect 관리를 위한 옵저버
     */
    private val observe = LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_STOP -> {
                    if(::autoScrollBannerJob.isInitialized)
                        autoScrollBannerJob.cancel()
                }
            }
        }

    fun setPostInterface(listener: ((banners:List<Banner>, binding:ItemHomeHeaderBinding) -> Unit)?) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(banners, position)
    }

    /**
     * 스크롤이 하단으로 내려갈 시 자동스크롤 job 취소.
     */
    override fun onViewRecycled(holder: ViewHolder) {
        autoScrollBannerJob.cancel()
        super.onViewRecycled(holder)
    }

    inner class ViewHolder(private val binding:ItemHomeHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(banners:List<Banner>, holderPosition:Int) {

            with(binding.vpBanner) {
                offscreenPageLimit = 1
                adapter = bannerAdapter
                bannerAdapter.submitList(banners)
                //무한스크롤 설정
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        if (positionOffsetPixels != 0) { return }
                        when (position) {
                            0 -> binding.vpBanner.setCurrentItem(bannerAdapter.itemCount - 2, false)
                            bannerAdapter.itemCount - 1 -> binding.vpBanner.setCurrentItem(1, false)
                        }
                    }

                    //Job 이용한 자동스크롤 설정
                    override fun onPageScrollStateChanged(state: Int) {
                        super.onPageScrollStateChanged(state)
                        when (state) {
                            ViewPager2.SCROLL_STATE_IDLE ->  if (!autoScrollBannerJob.isActive) scrollJobCreate(binding)
                            ViewPager2.SCROLL_STATE_DRAGGING -> if (::autoScrollBannerJob.isInitialized) autoScrollBannerJob.cancel()
                            ViewPager2.SCROLL_STATE_SETTLING -> { }
                        }
                    }

                    @SuppressLint("SetTextI18n")
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        //화면 재개 시의 마지막 페이지 인덱스 저장
                        viewPageStates[holderPosition] = binding.vpBanner.currentItem
                        //BottomNavigation 전환 후의 현재 textView 텍스트 유지 포함.
                        binding.vpBanner.post {
                            if (position > 0 && position < bannerAdapter.itemCount - 1) {
                                binding.tvBannerIndex.text = "${(binding.vpBanner.currentItem - 1 % bannerAdapter.actualItemCount()).plus(1)}/${bannerAdapter.actualItemCount()}"
                            }
                        }
                    }
                })

                //시작 페이지 설정.
                val targetIndex = viewPageStates[holderPosition] ?: run { 1 }
                setCurrentItem(targetIndex, false)
            }

            binding.setVariable(BR.banner, banners)
            listener?.invoke(banners, binding)
            binding.executePendingBindings()
            scrollJobCreate(binding)
        }
    }

    //Lifecycle 이용한 자동스크롤.
    fun scrollJobCreate(binding: ItemHomeHeaderBinding) {
        autoScrollBannerJob = lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                delay(AUTO_SCROLL_BANNER_INTERVAL)
                ensureActive()
                binding.vpBanner.setCurrentItem(binding.vpBanner.currentItem + 1, true)
                autoScrollBannerJob.cancel()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_home_header
    }

    override fun getItemCount(): Int = if(banners.isEmpty())  0 else 1

}