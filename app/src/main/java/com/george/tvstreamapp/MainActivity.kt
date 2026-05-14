package com.george.tvstreamapp

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.george.tvstreamapp.navigation.NavGraph
import com.george.tvstreamapp.ui.theme.TVStreamTheme

class MainActivity : ComponentActivity() {

    override fun dispatchGenericMotionEvent(
        ev: MotionEvent?
    ): Boolean {

        if (
            ev?.action == MotionEvent.ACTION_HOVER_ENTER
            ||
            ev?.action == MotionEvent.ACTION_HOVER_MOVE
            ||
            ev?.action == MotionEvent.ACTION_HOVER_EXIT
        ) {

            return true
        }

        return super.dispatchGenericMotionEvent(ev)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)

        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

        window.decorView.systemUiVisibility = (

                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )

        setContent {

            TVStreamTheme {

                NavGraph()
            }
        }
    }
}