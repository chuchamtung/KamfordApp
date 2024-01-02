package com.kamford.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kamford.app.data.local.albums.Album
import com.kamford.app.data.local.albums.AlbumsDao
import com.kamford.app.data.local.articles.ArticlesDao
import com.kamford.app.data.local.attachments.AttachmentsDao
import com.kamford.app.data.local.courses.CoursesDao
import com.kamford.app.data.local.products.ProductsDao
import com.kamford.app.data.local.users.UsersDao
import com.kamford.app.data.local.articles.Article
import com.kamford.app.data.local.attachments.Attachment
import com.kamford.app.data.local.courses.Course
import com.kamford.app.data.local.products.Product
import com.kamford.app.data.local.transaction.TransactionRunnerDao
import com.kamford.app.data.local.users.User

/**
 * The [RoomDatabase] we use in this app.
 */
@Database(
    entities = [
        Attachment::class,
        Article::class,
        Album::class,
        Product::class,
        Course::class,
        User::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun attachmentsDao(): AttachmentsDao
    abstract fun articlesDao(): ArticlesDao
    abstract fun albumsDao(): AlbumsDao
    abstract fun productsDao(): ProductsDao
    abstract fun coursesDao(): CoursesDao
    abstract fun usersDao(): UsersDao
    abstract fun transactionRunnerDao(): TransactionRunnerDao
}
