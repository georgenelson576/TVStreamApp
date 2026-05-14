package com.george.tvstreamapp.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {

    val scale = remember {

        Animatable(0.5f)
    }

    val alpha = remember {

        Animatable(0f)
    }

    LaunchedEffect(Unit) {

        scale.animateTo(

            targetValue = 1f,

            animationSpec = tween(

                durationMillis = 1200,

                easing = FastOutSlowInEasing
            )
        )

        alpha.animateTo(

            targetValue = 1f,

            animationSpec = tween(1200)
        )

        delay(1800)

        onFinished()
    }

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(

                Brush.verticalGradient(

                    colors = listOf(

                        Color.Black,

                        Color(0xFF111111)
                    )
                )
            ),

        contentAlignment = Alignment.Center
    ) {

        Text(

            text = "TV STREAM",

            color = Color.Red,

            fontSize = 42.sp,

            fontWeight = FontWeight.Bold,

            modifier = Modifier
                .scale(scale.value)
                .alpha(alpha.value)
        )
    }
}