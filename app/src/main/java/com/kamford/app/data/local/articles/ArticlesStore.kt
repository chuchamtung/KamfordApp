package com.kamford.app.data.local.articles

import kotlinx.coroutines.flow.Flow


/**
 * @description
 * @author chamtungchu
 * @date 2023/9/25
 * @version 1.0
 */
class ArticlesStore(
    private val articlesDao: ArticlesDao,
) {

    fun articleListAll(
        limit: Int
    ): Flow<List<Article>> {
        return articlesDao.articleListAll(limit)
    }

    fun articleListByAuthor(
        author: String,
        limit: Int
    ): Flow<List<Article>> {
        return articlesDao.articleListByAuthor(author,limit)
    }

    fun articleListByTypeId(
        author: String,
        typeId: Int,
        limit: Int
    ): Flow<List<Article>> {
        return articlesDao.articleListByAuthorAndTypeId(author,typeId,limit)
    }

    fun articleListByCourseId(
        author: String,
        courseId: String,
        limit: Int,
    ): Flow<List<Article>> {
        return articlesDao.articleListByAuthorAndCourseId(author,courseId,limit)
    }


    suspend fun addArticle(article: Article?): String {
        if (article != null){
            return when (val local = articlesDao.getArticleById(article.id)) {
                null -> articlesDao.insert(article).toString()
                else -> {
                    articlesDao.update(article)
                    local.id
                }
            }
        }else{
            return ""
        }
    }

    fun findArticleById(id: String): Flow<Article?> {
        return articlesDao.findArticleById(id)
    }

    suspend fun addArticleList(articles: Collection<Article>?) {
        if (!articles.isNullOrEmpty()){
            articlesDao.insertAll(articles)
        }
    }

    suspend fun isEmpty(): Boolean = articlesDao.count() == 0
}