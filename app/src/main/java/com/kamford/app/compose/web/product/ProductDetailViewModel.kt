package com.kamford.app.compose.web.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamford.app.data.local.products.Product
import com.kamford.app.data.local.products.ProductsStore
import com.kamford.app.data.local.products.asStoreProduct
import com.kamford.app.di.RoomModule
import com.kamford.app.data.MainRepository
import com.kamford.app.data.local.courses.Course
import com.kamford.app.data.local.courses.CoursesStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val productId: String?,
    private val productStore: ProductsStore = RoomModule.productStore,
    private val courseStore: CoursesStore = RoomModule.courseStore
) : ViewModel() {

    private val refreshing = MutableStateFlow(false)
    private val repository = MainRepository()


    private val _selectedProductCourse = MutableStateFlow<Course?>(null)
    private val _state = MutableStateFlow(ProductDetailViewState())
    val state: StateFlow<ProductDetailViewState>
        get() = _state

    init {
        Log.d("TAG", "getProductFeed: ${productId}")
        if (productId != null){
            viewModelScope.launch {
                combine(
                    productStore.findProductById(productId).onEach {
                        if (it == null){
                            refreshing.value = true
                        }else if (it.courseId != null && _selectedProductCourse.value == null) {
                            _selectedProductCourse.value = courseStore.getCourseById(it.courseId!!)
                        }
                    },
                    _selectedProductCourse,
                    refreshing
                ) {product, productCourse, refreshing ->
                    Log.d("TAG", "getProductFeed: ${product}")
                    ProductDetailViewState(
                        selectedProduct = product,
                        productCourse = productCourse,
                        refreshing = refreshing
                    )
                }.collect {
                    _state.value = it
                    refresh(_state.value.refreshing)
                }
            }
        }
    }

    private fun refresh(force: Boolean) {
        if (force && productId != null){
            viewModelScope.launch {
                runCatching {
                    try {
                        val storeProduct = repository.getWebProductDetail(productId).data.productItem?.asStoreProduct()
                        Log.d("TAG", "getProductFeed: ${storeProduct}")
                        productStore.addProduct(storeProduct)
                        ProductDetailViewState(
                            selectedProduct = storeProduct
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

data class ProductDetailViewState(
    val selectedProduct: Product? = null,
    val productCourse: Course? = null,
    val refreshing: Boolean = false,
)