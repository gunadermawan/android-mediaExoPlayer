package com.gunder.mediaexoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.util.Util
import com.gunder.mediaexoplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var player: ExoPlayer? = null
    private fun initializePlayer() {
        val mediaItem = MediaItem.fromUri(VIDEO_URL)
        val anotherMediaItem = MediaItem.fromUri(URL_AUDIO)

        player = ExoPlayer.Builder(this).build().also { exoPlayer ->
            viewBinding.videView.player = exoPlayer
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.addMediaItem(anotherMediaItem)
            exoPlayer.prepare()
        }
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    companion object {
        const val VIDEO_URL =
            "https://github.com/dicodingacademy/assets/releases/download/release-video/VideoDicoding.mp4"
        const val URL_AUDIO =
            "https://github.com/dicodingacademy/assets/raw/main/android_intermediate_academy/bensound_ukulele.mp3"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val player = ExoPlayer.Builder(this).build()
        viewBinding.videView.player = player

        val mediaItem = MediaItem.fromUri(VIDEO_URL)
        val anotherMediaItem = MediaItem.fromUri(URL_AUDIO)
        player.setMediaItem(mediaItem)
        player.addMediaItem(anotherMediaItem)
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, viewBinding.videView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }
}