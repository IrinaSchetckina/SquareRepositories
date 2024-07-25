package com.shchotkina.squarerepositories.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shchotkina.squarerepositories.MainViewModel
import com.shchotkina.squarerepositories.R
import com.shchotkina.squarerepositories.model.RepositoryItem

@Composable
fun RepositoriesScreen(viewModel: MainViewModel, modifier: Modifier) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.error != null || uiState.repositories.isEmpty()) {
        EmptyListPlaceholder(modifier, uiState.error?.message ?: "No repositories found")
    } else {
        RepositoryList(
            data = uiState.repositories,
            modifier = modifier
        )
    }

    AnimatedVisibility(
        visible = uiState.isLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        ProgressIndicator()
    }
}

@Composable
fun RepositoryList(data: List<RepositoryItem>, modifier: Modifier) {
    LazyColumn( modifier = modifier) {
        items(data.size) { index ->
            RepositoryItemView(data[index])
        }
    }
}

@Composable
fun RepositoryItemView(item: RepositoryItem) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { isExpanded = !isExpanded }) {
        Column (modifier = Modifier.fillMaxWidth().padding(8.dp)){
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.owner.avatarUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "avatar",
                    modifier = Modifier.padding(8.dp)
                )
                Column {
                    Text(
                        item.name,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    item.description?.let {
                        Text(
                            item.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            if (isExpanded) {
                HorizontalDivider(
                    Modifier
                        .height(1.dp)
                        .padding(horizontal = 8.dp), color = MaterialTheme.colorScheme.onSurface)
                Text(
                    stringResource(R.string.owner_data, item.owner.login),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    stringResource(R.string.language_data, item.language ?: stringResource(R.string.unknown)),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = stringResource(R.string.watchers_data, item.watchersCount),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = stringResource(R.string.created_date, item.createdAt),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(4.dp)
                )
                Text(text = stringResource(R.string.updated_date, item.updatedAt),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {},
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyListPlaceholder(
    modifier: Modifier = Modifier,
    message: String,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = message)
    }
}