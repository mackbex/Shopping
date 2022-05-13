package com.item.shopping

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class Application: MultiDexApplication() {

    override fun attachBaseContext(context: Context?) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }
}
