package com.kamford.app.compose.web.product

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamford.app.compose.utils.ItemGlideImage
import com.kamford.app.compose.utils.KamfordSnackbarHost
import com.kamford.app.compose.utils.ListTopAppBar
import com.kamford.app.compose.utils.viewModelProviderFactoryOf
import com.kamford.app.compose.web.about.CoursesTabs
import com.kamford.app.data.local.products.Product
import com.kamford.app.di.Constants


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit,
    onProductClick: (Product) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val viewModel: ProductsViewModel = viewModel(
        key = "web_products",
        factory = viewModelProviderFactoryOf { ProductsViewModel() }
    )
    val viewState by viewModel.state.collectAsStateWithLifecycle()
    val selectedNextCourse = viewState.selectedNextCourse

    Scaffold(
        snackbarHost = { KamfordSnackbarHost(hostState = snackbarHostState) },
        topBar = {
            ListTopAppBar(openDrawer = openDrawer)
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding).fillMaxSize()
        ) {
            if (viewState.nextCourseList != null && selectedNextCourse != null) {
                CoursesTabs(
                    nextCourses = viewState.nextCourseList,
                    selectedNextCourse = selectedNextCourse,
                    onNextCourseSelected = viewModel::onNextCourseSelected,
                )
                ItemGlideImage(
                    model = Constants.IMAGE_BASE_URL+ Constants.IMAGE_SIZE_BIG+"9ce608df-7c9e-4514-b5e5-21db8b0bbfd7",
                    contentDescription = "JKWT-A007-A型(升级版)净水器-D20",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(shape = MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop,
                )
                Spacer(Modifier.height(8.dp))

                Crossfade(
                    targetState = selectedNextCourse,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    label = selectedNextCourse.name ?: "Course name"
                ) { course ->

                    ProductListScreen(
                        courseId = course.id,
                        onProductClick = onProductClick
                    )

                }
            }
        }

    }
}