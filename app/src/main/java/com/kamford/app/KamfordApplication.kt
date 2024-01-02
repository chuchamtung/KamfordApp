package com.kamford.app

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.kamford.app.di.NetworkModule
import com.kamford.app.di.RoomModule

class KamfordApplication : Application(), ImageLoaderFactory{

    override fun onCreate() {
        super.onCreate()
        NetworkModule.provide(this)
        RoomModule.provide(this)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .respectCacheHeaders(false)
            .build()
    }

}
