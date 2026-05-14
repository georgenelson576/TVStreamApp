package com.george.tvstreamapp.ui.screens

import android.graphics.Color as AndroidColor
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PlayerScreen(
    videoUrl: String,
    onBack: () -> Unit
) {

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val decodedUrl = Uri.decode(videoUrl)

    var playerView: PlayerView? by remember {
        mutableStateOf(null)
    }

    var showPlayer by remember {
        mutableStateOf(true)
    }

    val exoPlayer = remember {

        ExoPlayer.Builder(context)

            .setHandleAudioBecomingNoisy(true)

            .build()

            .apply {

                val mediaItem =
                    MediaItem.fromUri(
                        Uri.parse(decodedUrl)
                    )

                setMediaItem(mediaItem)

                prepare()

                playWhenReady = true
            }
    }

    DisposableEffect(Unit) {

        onDispose {

            playerView?.player = null

            exoPlayer.clearVideoSurface()

            exoPlayer.pause()

            exoPlayer.stop()

            exoPlayer.clearMediaItems()

            exoPlayer.release()
        }
    }

    BackHandler {

        scope.launch {

            showPlayer = false

            playerView?.player = null

            exoPlayer.clearVideoSurface()

            exoPlayer.pause()

            exoPlayer.stop()

            exoPlayer.clearMediaItems()

            delay(120)

            onBack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        if (showPlayer) {

            AndroidView(

                factory = {

                    PlayerView(context).apply {

                        player = exoPlayer

                        useController = true

                        keepScreenOn = true

                        setKeepContentOnPlayerReset(false)

                        setShutterBackgroundColor(
                            AndroidColor.BLACK
                        )

                        setShowBuffering(
                            PlayerView.SHOW_BUFFERING_ALWAYS
                        )

                        playerView = this
                    }
                },

                modifier = Modifier.fillMaxSize()
            )
        }
    }
}