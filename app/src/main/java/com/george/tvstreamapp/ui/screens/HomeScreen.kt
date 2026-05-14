package com.george.tvstreamapp.ui.screens

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.george.tvstreamapp.data.repository.VideoRepository
import com.george.tvstreamapp.navigation.Routes
import com.george.tvstreamapp.ui.components.VideoCard

@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current

    // Load videos once
    val allVideos = remember { VideoRepository.loadVideos(context) }

    // State for Search and Focus
    var searchQuery by remember { mutableStateOf("") }
    var focusedVideoId by remember { mutableStateOf("") }

    // Dynamically filter videos based on search
    val filteredVideos = remember(searchQuery, allVideos) {
        if (searchQuery.isBlank()) {
            emptyList()
        } else {
            allVideos.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                        it.category.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    // Group videos by category for the Netflix-style layout
    val groupedVideos = remember(allVideos) {
        allVideos.groupBy { it.category }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 1. Premium Animated Search Bar
        TvSearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )

        // 2. Main Content Area (Categorized Rows OR Search Results)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 48.dp,
                top = 16.dp,
                bottom = 48.dp,
                end = 48.dp
            ),
            verticalArrangement = Arrangement.spacedBy(32.dp) // Premium spacing between rows
        ) {

            // Show Search Results if the user is typing
            if (searchQuery.isNotBlank()) {
                item {
                    Text(
                        text = "Search Results for \"$searchQuery\"",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(start = 12.dp, bottom = 16.dp)
                    )

                    if (filteredVideos.isEmpty()) {
                        Text(
                            text = "No videos found.",
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    } else {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 24.dp)
                        ) {
                            items(filteredVideos, key = { it.id }) { video ->
                                VideoCard(
                                    video = video,
                                    isPreview = focusedVideoId == video.id,
                                    onFocused = { focusedVideoId = video.id },
                                    onClick = {
                                        val encodedUrl = Uri.encode(video.videoUrl)
                                        navController.navigate(Routes.playerRoute(encodedUrl))
                                    }
                                )
                            }
                        }
                    }
                }
            }
            // Otherwise, show the classic Netflix-style categorized rows
            else {
                groupedVideos.forEach { (category, videosInCategory) ->
                    item {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
                        )

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 24.dp)
                        ) {
                            items(videosInCategory, key = { it.id }) { video ->
                                VideoCard(
                                    video = video,
                                    isPreview = focusedVideoId == video.id,
                                    onFocused = { focusedVideoId = video.id },
                                    onClick = {
                                        val encodedUrl = Uri.encode(video.videoUrl)
                                        navController.navigate(Routes.playerRoute(encodedUrl))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TvSearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    // Smooth animations for TV focus
    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.02f else 1f,
        animationSpec = tween(300),
        label = "search_scale"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) MaterialTheme.colorScheme.primary else Color.Transparent,
        animationSpec = tween(300),
        label = "search_border"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isFocused) Color(0xFF2A2D36) else Color(0xFF1A1C23),
        animationSpec = tween(300),
        label = "search_bg"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 48.dp, end = 48.dp, top = 36.dp, bottom = 12.dp)
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            modifier = Modifier
                .fillMaxWidth()
                .scale(scale)
                .focusRequester(focusRequester)
                .onFocusChanged { isFocused = it.isFocused }
                .focusable()
                .border(
                    width = if (isFocused) 2.dp else 0.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .background(backgroundColor, RoundedCornerShape(16.dp))
                .padding(horizontal = 24.dp, vertical = 18.dp),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = if (isFocused) MaterialTheme.colorScheme.primary else Color.Gray,
                        modifier = Modifier.size(28.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Box(modifier = Modifier.weight(1f)) {
                        if (query.isEmpty()) {
                            Text(
                                text = "Search movies, shows, or categories...",
                                color = Color.Gray,
                                fontSize = 18.sp
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )
    }
}