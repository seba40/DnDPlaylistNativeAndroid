package start

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.dndplaylist.R
import com.example.dndplaylist.Utils
import playlist.PlaylistView

class StartView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_view_layout)
        val videoUri: Uri =
            Uri.parse("android.resource://" + packageName + "/" + R.raw.start_view_video)
        val backgroundVideo = findViewById<VideoView>(R.id.backgroundVideo)
        StartController.createBackgroundVideo(backgroundVideo, videoUri)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // If the app has regained focus, hide the system ui and restart the background video
        if (hasFocus) {
            Utils.hideSystemUI(window)
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
