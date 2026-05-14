# TV Stream App

A modern Android TV streaming application built using Jetpack Compose and ExoPlayer.

The app provides:
- TV-style video browsing
- Smooth D-pad navigation
- Fullscreen video playback
- Animated focus effects
- Netflix-style categorized rows
- Search functionality
- Android TV support

---

# Features

## TV Navigation
- D-pad navigation support
- Focus animations
- Hover support
- Smooth scrolling

## Video Playback
- ExoPlayer integration
- Fullscreen player
- Auto play support
- Back navigation handling
- Proper player cleanup

## UI
- Netflix-style home screen
- Animated search bar
- Thumbnail loading with Coil
- Focus scaling effects
- Responsive layouts

---

# Tech Stack

- Kotlin
- Jetpack Compose
- Navigation Compose
- ExoPlayer (Media3)
- Coil
- Android TV APIs

---

# Project Structure

```text
com.george.tvstreamapp
│
├── data
│   ├── model
│   └── repository
│
├── navigation
│
├── ui
│   ├── components
│   ├── screens
│   └── theme
│
└── MainActivity.kt
```

---

# How to Run

## Requirements

- Android Studio Hedgehog or newer
- Android SDK 34
- Kotlin support enabled
- Internet connection for streaming

---

## Steps

1. Clone or download the project

2. Open the project in Android Studio

3. Sync Gradle

4. Run the app on:
   - Android TV Emulator
   - Android Phone
   - Physical Android TV device

---

# Build Instructions

## Debug Build

```bash
./gradlew assembleDebug
```

## Release Build

```bash
./gradlew assembleRelease
```

---

# Video Data Source

Videos are loaded from:

```text
assets/videos.json
```

The JSON file contains:
- video title
- thumbnail
- category
- video stream URL

---

# Performance Optimizations

## ExoPlayer Optimization
- Proper player release
- Surface cleanup
- Avoid multiple player instances
- Efficient buffering

## Compose Optimization
- Stable LazyRow keys
- Remembered state handling
- Immutable data models
- Reduced recomposition

## Image Optimization
- Coil AsyncImage
- Placeholder images
- Efficient thumbnail loading
- Cropped rendering

## TV Optimization
- D-pad focus handling
- Smooth focus animations
- Fullscreen immersive mode
- Landscape support

---

# Android TV Features

- Leanback launcher support
- Remote navigation
- TV-style focus handling
- Fullscreen playback

---

# Future Improvements

- Live TV support
- HLS streaming
- Video previews
- Firebase backend
- User authentication
- Watch history
- Favorites system

---

# Author

George
