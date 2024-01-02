package com.kamford.app.compose.user.home

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.kamford.app.R
import com.kamford.app.compose.utils.ItemGlideImage
import com.kamford.app.compose.utils.viewModelProviderFactoryOf
import com.kamford.app.di.Constants
import java.io.File
import kotlin.reflect.KFunction0

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHomeScreen(
    openDrawer: () -> Unit,
    onSignIn: () -> Unit,
    onSignUp: () -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    val context = LocalContext.current
    val viewModel: UserHomeViewModel = viewModel(
        key = "user_home",
        factory = viewModelProviderFactoryOf { UserHomeViewModel(photoUriManager = PhotoUriManager(context)) }
    )
    val viewState by viewModel.state.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val userItem = viewState.userData

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            UserHomeTopAppBar(
                scrollBehavior = scrollBehavior,
                openDrawer = openDrawer
            )
        }
    ) { contentPadding ->

        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding,
            state = state
        ) {
            if (userItem != null && userItem.loginState == 1){
                item {
                    Spacer(Modifier.height(8.dp))
                    Text(text = viewState.userData?.nickName ?: "nickName")
                    Spacer(Modifier.height(8.dp))

                    val imageModifier = Modifier
                        .size(width = 40.dp,height = 40.dp)
                        .fillMaxWidth()
                        .clip(shape = MaterialTheme.shapes.medium)

                    ItemGlideImage(
                        model = if (viewState.userData?.avatar != null) "${Constants.KAMFORD_APP_WEB}${viewState.userData?.avatar}" else "${Constants.KAMFORD_APP_WEB}/images/avatar.png",
                        contentDescription = null,
                        modifier = imageModifier,
                        contentScale = ContentScale.Crop
                    )


                    Spacer(Modifier.height(8.dp))
                }
                item {
                    TakeSelfieQuestion(
                        imageUri = viewModel.selfieUri,
                        imageFile = viewModel.selFile,
                        getNewImageUri = viewModel::getNewSelfie,
                        onPhotoTaken = viewModel::onSelfieResponse,
                        updateAvatar = viewModel::updateAvatar,
                        modifier = modifier,
                    )
                }
            }else{
                item {
                    TextButton(onClick = onSignUp ) {
                        Text(text = "Signup button")
                    }
                    Spacer(Modifier.height(8.dp))
                    TextButton(onClick = onSignIn ) {
                        Text(text = "Signin Button")
                    }
                }

            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserHomeTopAppBar(
    openDrawer: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    CenterAlignedTopAppBar(
        title = {
            Row {
                Icon(
                    painterResource(R.drawable.ic_kamford_logo),
                    contentDescription = stringResource(id = R.string.app_name),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .width(36.dp)
                        .height(36.dp)
                )
                Spacer(Modifier.width(8.dp))
                Column{
                    Text(
                        text = "金科偉業",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Kamford.App",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(
                    imageVector = Icons.Default.MenuOpen,
                    contentDescription = stringResource(id = R.string.cd_open_navigation_drawer)
                )
            }
        },
        actions = {
            IconButton(onClick = {
                Toast.makeText(
                    context,
                    "Search is not yet implemented in this configuration",
                    Toast.LENGTH_LONG
                ).show()
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.cd_search)
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@Composable
fun TakeSelfieQuestion(
    imageUri: Uri?,
    imageFile: File?,
    getNewImageUri: () -> PhotoState,
    onPhotoTaken: (PhotoState) -> Unit,
    updateAvatar: (File) -> Unit,
    modifier: Modifier = Modifier,
) {
    PhotoQuestion(
        imageUri = imageUri,
        imageFile = imageFile,
        getNewImageUri = getNewImageUri,
        onPhotoTaken = onPhotoTaken,
        updateAvatar = updateAvatar
    )
}
