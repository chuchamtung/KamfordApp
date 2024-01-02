package com.kamford.app.data.local.blogs

import kotlinx.coroutines.flow.Flow


/**
 * @description
 * @author chamtungchu
 * @date 2023/9/25
 * @version 1.0
 */
class BlogsStore(
    private val blogsDao: BlogsDao,
) {
    suspend fun addBlogs(blogs: Collection<Blog>) = blogsDao.insertAll(blogs)

    suspend fun addBlogById(blog: Blog): String {
        return when (val local = blogsDao.getBlogById(blog.id)) {
            null -> blogsDao.insert(blog).toString()
            else -> {
                blogsDao.update(blog)
                local.id
            }
        }
    }

    suspend fun addBlogByName(blog: Blog): String {
        return when (val local = blogsDao.getBlogByName(blog.name!!)) {
            null -> blogsDao.insert(blog).toString()
            else -> {
                blogsDao.update(blog)
                local.name!!
            }
        }
    }

    fun findBlogByAuthor(author: String): Flow<Blog> {
        return blogsDao.findBlogByAuthor(author)
    }

    fun findBlogByName(name: String): Flow<Blog> {
        return blogsDao.findBlogByName(name)
    }
}