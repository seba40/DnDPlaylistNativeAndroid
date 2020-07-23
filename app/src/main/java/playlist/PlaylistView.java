package playlist;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dndplaylist.R;
import com.example.dndplaylist.Utils;

import java.util.HashMap;
import java.util.Map;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class PlaylistView extends AppCompatActivity {
    PlaylistController playlistController;
    ProgressBar progressBar;
    LinearLayout playlistLayout;
    LinearLayout categoryLayout;
    Button playlistButton;
    ScrollView playlistView;
    ScrollView categoryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist_view_layout);

        // Initialize App Controller and get a reference to every UI element
        Context context = PlaylistView.this;
        playlistController = new PlaylistController(context);
        progressBar = findViewById(R.id.progressBar);
        playlistLayout = findViewById(R.id.playlistLayout);
        categoryLayout = findViewById(R.id.categoryLayout);
        playlistButton = findViewById(R.id.playlistButton);
        playlistView = findViewById(R.id.playlistView);
        categoryView = findViewById(R.id.categoryView);

        // Initialize the over-scroll effect
        OverScrollDecoratorHelper.setUpOverScroll(playlistView);
        OverScrollDecoratorHelper.setUpOverScroll(categoryView);

        //Pass the map that holds UI elements to App Controller
        Map<String, View> layoutData = createLayoutData();
        playlistController.createAppData(layoutData);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Utils.hideSystemUI(getWindow());
        }
    }

    Map<String, View> createLayoutData() {
        Map<String, View> layoutMap = new HashMap<>();
        layoutMap.put("progressBar", progressBar);
        layoutMap.put("playlistButton", playlistButton);
        layoutMap.put("categoryLayout", categoryLayout);
        layoutMap.put("playlistLayout", playlistLayout);
        layoutMap.put("categoryView", categoryView);
        layoutMap.put("playlistView", playlistView);
        return layoutMap;
    }
}
