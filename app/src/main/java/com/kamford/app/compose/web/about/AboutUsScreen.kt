package com.kamford.app.compose.web.about

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamford.app.compose.utils.KamfordSnackbarHost
import com.kamford.app.compose.utils.ListTopAppBar
import com.kamford.app.compose.utils.viewModelProviderFactoryOf
import com.kamford.app.data.local.albums.Album
import com.kamford.app.data.local.courses.Course

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit,
    onAlbumClick: (Album) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val viewModel: AboutUsViewModel = viewModel(
        key = "web_about_us",
        factory = viewModelProviderFactoryOf { AboutUsViewModel() }
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

                Crossfade(
                    targetState = selectedNextCourse,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    label = selectedNextCourse.name ?: "Course name"
                ) { course ->

                    when (course.isType) {
                        1 -> {
                            // Album
                            AlbumListScreen(
                                courseId = course.id,
                                onAlbumClick = onAlbumClick
                            )
                        }
                        9 -> {
                            // Page Texts
                            TextsScreen(
                                course = course
                            )

                        }
                    }

                }

            }
        }

    }




}
@Composable
fun CoursesTabs(
    nextCourses: List<Course>?,
    selectedNextCourse: Course,
    onNextCourseSelected: (Course) -> Unit,
) {
    val selectedNextIndex = nextCourses?.indexOfFirst { it == selectedNextCourse }
    val emptyTabIndicator: @Composable (List<TabPosition>) -> Unit = {}

    if (nextCourses != null && selectedNextIndex != null) {
        ScrollableTabRow(
            selectedTabIndex = selectedNextIndex,
            divider = {}, /* Disable the built-in divider */
            edgePadding = 6.dp,
            indicator = emptyTabIndicator
        ) {
            nextCourses.forEachIndexed { index, course ->
                Tab(
                    selected = index == selectedNextIndex,
                    onClick = {
                        onNextCourseSelected(course)
                    },
                    selectedContentColor = when {
                        index == selectedNextIndex -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.onSurface
                    }
                ){
                    ChoiceChipContent(
                        text = course.name ?: "name",
                        selected = index == selectedNextIndex,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun ChoiceChipContent(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        color = when {
            selected -> MaterialTheme.colorScheme.primaryContainer
            else -> MaterialTheme.colorScheme.primaryContainer
        },
        contentColor = when {
            selected -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.onSurface
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}