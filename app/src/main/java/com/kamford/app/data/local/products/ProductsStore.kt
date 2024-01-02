package com.kamford.app.data.local.products

import kotlinx.coroutines.flow.Flow


/**
 * @description
 * @author chamtungchu
 * @date 2023/9/25
 * @version 1.0
 */
class ProductsStore(
    private val productsDao: ProductsDao
) {

    fun productListByOrder(
        limit: Int
    ): Flow<List<Product>> {
        return productsDao.productListByOrder(limit)
    }

    fun productListByAuthor(
        author: String,
        limit: Int
    ): Flow<List<Product>> {
        return productsDao.productListByAuthor(author,limit)
    }

    fun productListByTypeId(
        author: String,
        typeId: Int,
        limit: Int
    ): Flow<List<Product>> {
        return productsDao.productListByAuthorAndTypeId(author,typeId,limit)
    }

    fun productListByCourseId(
        author: String,
        courseId: String,
        limit: Int
    ): Flow<List<Product>> {
        return productsDao.productListByAuthorAndCourseId(author,courseId,limit)
    }


    suspend fun addProduct(product: Product?): String {
        if (product == null){
            return "null"
        }else{
            return when (val local = productsDao.getProductById(product.id)) {
                null -> productsDao.insert(product).toString()
                else ->{
                    productsDao.update(product)
                    local.id
                }
            }
        }
    }

    fun findProductById(id: String): Flow<Product?> {
        return productsDao.findProductById(id)
    }

    suspend fun addProductList(products: Collection<Product>?) {
        if (!products.isNullOrEmpty()){
            productsDao.insertAll(products)
        }
    }

    suspend fun isEmpty(): Boolean = productsDao.count() == 0
}