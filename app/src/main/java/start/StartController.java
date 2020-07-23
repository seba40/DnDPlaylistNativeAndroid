package start;

import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.VideoView;

public class StartController {

    public static void createBackgroundVideo(VideoView backgroundVideo, Uri videoUri) {
        // Load and play the background video on the start view

        backgroundVideo.setVideoURI(videoUri);
        backgroundVideo.requestFocus();
        backgroundVideo.start();
        // Set the video on looping after it has been loaded
        backgroundVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
    }
}
