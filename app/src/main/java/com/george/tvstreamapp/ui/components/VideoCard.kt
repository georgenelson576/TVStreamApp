package com.george.tvstreamapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.george.tvstreamapp.data.model.VideoItem

@Composable
fun VideoCard(

    video: VideoItem,

    isPreview: Boolean,

    onFocused: () -> Unit,

    onClick: () -> Unit
) {

    val interactionSource =
        remember {
            MutableInteractionSource()
        }

    val isFocused by
    interactionSource.collectIsFocusedAsState()

    val isHovered by
    interactionSource.collectIsHoveredAsState()

    val selected =
        isFocused || isHovered

    val scale by animateFloatAsState(

        targetValue =
            if (selected) 1.12f else 1f,

        label = ""
    )

    val borderColor by animateColorAsState(

        targetValue =
            if (selected)
                Color.Red
            else
                Color.Transparent,

        label = ""
    )

    val elevation by animateFloatAsState(

        targetValue =
            if (selected) 18f else 4f,

        label = ""
    )

    Card(

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = elevation.dp
            ),

        colors =
            CardDefaults.cardColors(
                containerColor = Color(0xFF111111)
            ),

        modifier = Modifier
            .padding(10.dp)

            .scale(scale)

            .border(
                width =
                    if (selected) 3.dp else 0.dp,

                color = borderColor,

                shape = RoundedCornerShape(16.dp)
            )

            .onFocusChanged {

                if (it.isFocused) {

                    onFocused()
                }
            }

            .focusable(
                interactionSource = interactionSource
            )

            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {

                onClick()
            }

            .onKeyEvent {

                if (

                    it.type == KeyEventType.KeyDown

                    &&

                    (
                            it.key == Key.DirectionCenter
                                    ||
                                    it.key == Key.Enter
                            )
                ) {

                    onClick()

                    true

                } else {

                    false
                }
            }
    ) {

        Column {

            AsyncImage(

                model = video.thumbnail,

                contentDescription = video.title,

                contentScale = ContentScale.Crop,

                modifier = Modifier
                    .size(
                        width = 250.dp,
                        height = 145.dp
                    )
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                    )
            )

            AnimatedVisibility(

                visible = isPreview,

                enter = fadeIn(),

                exit = fadeOut()
            ) {

                Text(

                    text = "▶ Preview Ready",

                    color = Color.Red,

                    fontWeight = FontWeight.Bold,

                    modifier = Modifier
                        .padding(
                            horizontal = 10.dp,
                            vertical = 6.dp
                        )
                )
            }

            Text(

                text = video.title,

                color = Color.White,

                maxLines = 1,

                overflow = TextOverflow.Ellipsis,

                fontWeight = FontWeight.SemiBold,

                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}