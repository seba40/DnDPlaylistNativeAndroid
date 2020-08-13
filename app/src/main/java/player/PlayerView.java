package player;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dndplaylist.R;
import com.example.dndplaylist.Utils;

public class PlayerView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_view_layout);
        TextView test = findViewById(R.id.playlistName);
        Bundle playlistBundle = getIntent().getExtras();
        String playlistName = ""; // or other values
        if (playlistBundle != null)
            playlistName = playlistBundle.getString("playlistName");
        test.setText(playlistName);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Utils.hideSystemUI(getWindow());
        }
    }
}
