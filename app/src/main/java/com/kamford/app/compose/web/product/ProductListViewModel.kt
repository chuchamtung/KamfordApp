package com.kamford.app.compose.web.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamford.app.data.local.products.Product
import com.kamford.app.data.local.products.ProductsStore
import com.kamford.app.data.local.products.asStoreProductList
import com.kamford.app.di.RoomModule
import com.kamford.app.data.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProductListViewMode(
    private val courseId: String,
    private val productStore: ProductsStore = RoomModule.productStore,
) : ViewModel() {

    private val refreshing = MutableStateFlow(false)
    private val repository = MainRepository()

    private val _state = MutableStateFlow(ProductViewState())
    val state: StateFlow<ProductViewState>
        get() = _state

    init {
        viewModelScope.launch {
            combine(
                productStore.productListByCourseId(
                    author = "kamford_web",
                    courseId = courseId,
                    limit = Integer.MAX_VALUE
                ).onEach {
                    if ( it.isEmpty() ){
                        refreshing.value = true
                    }
                },
                refreshing
            ) {productList, refreshing ->
                ProductViewState(
                    productList = productList,
                    courseId = courseId,
                    refreshing = refreshing
                )
            }.collect {
                _state.value = it
                refresh(_state.value.refreshing)
            }

        }
    }

    private fun refresh(force: Boolean) {
        if (force){
            viewModelScope.launch {
                runCatching {
                    try {
                        val storeProductList = repository.getWebCourseItem(courseId,2, 0, 0,0).data.asStoreProductList()
                        productStore.addProductList(storeProductList)
                        ProductViewState(
                            productList = storeProductList?.toList()
                        )
                    } catch (e: Exception) {
                        Log.d("TAG", "getProductFeed: ${e}")
                    }
                }
                refreshing.value = false
            }
        }
    }

}

data class ProductViewState(
    val productList: List<Product>? = emptyList(),
    val courseId: String? = null,
    val refreshing: Boolean = false,
)