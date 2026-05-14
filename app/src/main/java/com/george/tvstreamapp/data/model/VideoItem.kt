package com.george.tvstreamapp.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class VideoItem(

    val id: String,

    val title: String,

    val thumbnail: String,

    val videoUrl: String,

    val category: String
)