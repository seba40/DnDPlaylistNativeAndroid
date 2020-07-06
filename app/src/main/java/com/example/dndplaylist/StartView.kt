package com.example.dndplaylist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class StartView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_view_layout)
        // Load and play the background video on the start view
        val videoUri: Uri =
            Uri.parse("android.resource://" + packageName + "/" + R.raw.start_view_video)
        val backgroundVideo = findViewById<VideoView>(R.id.backgroundVideo)
        backgroundVideo.setVideoURI(videoUri)
        backgroundVideo.requestFocus()
        backgroundVideo.start()
        // Set the video on looping after it has been loaded
        backgroundVideo.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // If the app has regained focus, hide the system ui and restart the background video
        if (hasFocus) {
            AppController.hideSystemUI(window)
            val backgroundVideo = findViewById<VideoView>(R.id.backgroundVideo)
            backgroundVideo.requestFocus()
            backgroundVideo.start()
        }
    }
    
    fun openPlaylist(@Suppress("UNUSED_PARAMETER") v: View) {
        val intent = Intent(this, PlaylistView::class.java)
        startActivity(intent)
        finish()
    }
}
