package com.george.tvstreamapp.data.repository

import android.content.Context
import com.george.tvstreamapp.data.model.VideoItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object VideoRepository {

    fun loadVideos(context: Context): List<VideoItem> {

        val jsonString = context.assets
            .open("videos.json")
            .bufferedReader()
            .use { it.readText() }

        val listType =
            object : TypeToken<List<VideoItem>>() {}.type

        return Gson().fromJson(jsonString, listType)
    }
}