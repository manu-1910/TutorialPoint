package com.nativeapp.tutorialpoint.activities.playerscreen

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import com.nativeapp.tutorialpoint.R
import com.nativeapp.tutorialpoint.databinding.ActivityPlayerBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PlayerActivity : AppCompatActivity() {

    @Inject
    lateinit var player : ExoPlayer
    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
        this, R.layout.activity_player)
        binding.playerView.player = player
    }

    override fun onStart() {
        super.onStart()
        Handler(Looper.getMainLooper()).postDelayed(Runnable{
            intent.extras?.getString("url")?.let { prepareAndStartPlaying(this@PlayerActivity, it) }
        }, 2000)
    }

    @OptIn(UnstableApi::class) private fun prepareAndStartPlaying(context: Context, url: String){
        //val mediaItem = MediaItem.fromUri("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
        //val mediaItem = MediaItem.fromUri("http://sample.vodobox.net/skate_phantom_flex_4k/skate_phantom_flex_4k.m3u8")
        //val mediaItem = buildMediaSource(context, Uri.parse("http://sample.vodobox.net/skate_phantom_flex_4k/skate_phantom_flex_4k.m3u8"))
        val mediaItem = buildMediaSource(context, Uri.parse(url))
        player.setMediaSource(mediaItem)
        player.prepare()
        player.play()
    }

    @OptIn(UnstableApi::class) private fun buildMediaSource(context: Context, uri: Uri): MediaSource {
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        return HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri))
    }

    override fun onRestart() {
        super.onRestart()
        player.play()
    }

    override fun onStop() {
        super.onStop()
        player.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        player.release()
    }

}