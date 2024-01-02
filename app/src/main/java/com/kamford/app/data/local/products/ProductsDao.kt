package com.kamford.app.data.local.products

import androidx.room.Dao
import androidx.room.Query
import com.kamford.app.data.local.BaseDao
import kotlinx.coroutines.flow.Flow

/**
 * [Room] DAO for [Products] related operations.
 */
@Dao
abstract class ProductsDao : BaseDao<Product> {

    @Query(
        """
        SELECT * FROM products 
        WHERE author = :author AND course_id = :courseId 
        ORDER BY created_at DESC 
        LIMIT :limit
        """
    )
    abstract fun productListByAuthorAndCourseId(
        author: String,
        courseId: String,
        limit: Int
    ): Flow<List<Product>>

    @Query(
        """
        SELECT * FROM products
        WHERE is_type = :typeId AND author = :author
        ORDER BY created_at DESC
        LIMIT :limit
        """
    )
    abstract fun productListByAuthorAndTypeId(
        author: String,
        typeId: Int,
        limit: Int
    ): Flow<List<Product>>

    @Query(
        """
        SELECT * FROM products
        WHERE author = :author
        ORDER BY created_at DESC
        LIMIT :limit
        """
    )
    abstract fun productListByAuthor(
        author: String,
        limit: Int
    ): Flow<List<Product>>

    @Query(
        """
        SELECT * FROM products
        ORDER BY order_sort DESC
        LIMIT :limit
        """
    )
    abstract fun productListByOrder(
        limit: Int
    ): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id = :id")
    abstract suspend fun getProductById(id: String): Product?

    @Query("SELECT * FROM products WHERE id = :id")
    abstract fun findProductById(id: String): Flow<Product?>

    @Query("SELECT COUNT(*) FROM products")
    abstract suspend fun count(): Int

}
