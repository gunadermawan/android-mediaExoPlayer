package com.gunder.mediaexoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.gunder.mediaexoplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    companion object {
        const val VIDEO_URL =
            "https://github.com/dicodingacademy/assets/releases/download/release-video/VideoDicoding.mp4"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val player = ExoPlayer.Builder(this).build()
        viewBinding.videView.player = player

        val mediaItem = MediaItem.fromUri(VIDEO_URL)
        player.setMediaItem(mediaItem)
        player.prepare()
    }


}