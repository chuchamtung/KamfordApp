package com.kamford.app.di

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.room.Room
import com.kamford.app.data.local.LocalDatabase
import com.kamford.app.data.local.albums.AlbumsStore
import com.kamford.app.data.local.articles.ArticlesStore
import com.kamford.app.data.local.attachments.AttachmentsStore
import com.kamford.app.data.local.courses.CoursesStore
import com.kamford.app.data.local.products.ProductsStore
import com.kamford.app.data.local.users.UsersStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


object RoomModule {
    lateinit var localDatabase: LocalDatabase
        private set

    lateinit var androidID: String
        private set

    lateinit var androidFinger: String
        private set

    lateinit var androidVersion: String
        private set


    private val kamfordMainDispatcher: CoroutineDispatcher
        get() = Dispatchers.Main

    private val kamfordIoDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO



    val albumsStore by lazy {
        AlbumsStore(
            albumsDao = localDatabase.albumsDao()
        )
    }

    val attachmentStore by lazy {
        AttachmentsStore(
            attachmentsDao = localDatabase.attachmentsDao()
        )
    }

    val articleStore by lazy {
        ArticlesStore(
            articlesDao = localDatabase.articlesDao()
        )
    }

    val productStore by lazy {
        ProductsStore(
            productsDao = localDatabase.productsDao()
        )
    }

    val courseStore by lazy {
        CoursesStore(
            coursesDao = localDatabase.coursesDao()
        )
    }

    val userStore by lazy {
        UsersStore(
            usersDao = localDatabase.usersDao()
        )
    }

    val userByAndroidId by lazy {
        userStore.getUserByAndroidId(androidFinger)
    }

    val tokenKey by lazy {
        userByAndroidId?.userKey
    }

    @SuppressLint("HardwareIds")
    fun provide(context: Context) {
        localDatabase = Room.databaseBuilder(context, LocalDatabase::class.java, "kamford.db")
            .fallbackToDestructiveMigration()
            .build()

        androidID = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

        androidFinger = "${Build.BRAND}-${Build.PRODUCT}-KamFord-${androidID}"

    }
}