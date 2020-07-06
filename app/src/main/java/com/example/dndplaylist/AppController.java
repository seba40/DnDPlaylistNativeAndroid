package com.example.dndplaylist;

import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Parcel
public class AppController {
    ArrayList<SongCategory> playlistCollection;

    static void hideSystemUI(Window window) {
        // Enables regular immersive mode.
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    static void showSystemUI(Window window) {
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    void InitializeController() {
        playlistCollection = new ArrayList<>();
    }

    public ArrayList<SongCategory> getPlaylistCollection() {
        return playlistCollection;
    }

    public void setPlaylistCollection(ArrayList<SongCategory> playlistCollection) {
        this.playlistCollection = playlistCollection;
    }

    void createAppData(final Map<String, View> layoutData) {
        // Initialize a Firebase instance and get the song categories with their corresponding playlists from the database
        FirebaseFirestore databaseInstance = FirebaseFirestore.getInstance();
        databaseInstance.collection("SongCategories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Objects.requireNonNull(layoutData.get("progressBar")).setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                SongCategory newCategory = new SongCategory((String) document.getData().get("CategoryName"));
                                if (document.getData().get("Playlists") != null) {
                                    ArrayList<Playlist> newPlaylists = new ArrayList<>();
                                    for (Object playlistData : (ArrayList) Objects.requireNonNull(document.getData().get("Playlists"))) {
                                        newPlaylists.add(new Playlist((String) playlistData));
                                    }
                                    Collections.sort(newPlaylists);
                                    newCategory.setPlaylists(newPlaylists);
                                }
                                playlistCollection.add(newCategory);
                            }
                            Collections.sort(playlistCollection);
                            createUI(layoutData);
                        } else {
                            // TODO add error handler
                        }
                    }
                });
    }

    // TODO change setTextAppearance for keeping min API 21
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createUI(final Map<String, View> layoutData) {
        // Creates the buttons from the playlists data
        final Button buttonReference = (Button) layoutData.get("playlistButton");
        final LinearLayout categoryLayout = (LinearLayout) layoutData.get("categoryLayout");
        final LinearLayout playlistLayout = (LinearLayout) layoutData.get("playlistLayout");
        for (final SongCategory category : playlistCollection) {
            assert buttonReference != null;
            assert categoryLayout != null;
            Button newCategoryButton = createPlaylistButton(category.getName(), buttonReference, categoryLayout);
            if (category.getPlaylists() != null) {
                newCategoryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Objects.requireNonNull(layoutData.get("categoryView")).setVisibility(View.GONE);
                        Objects.requireNonNull(layoutData.get("playlistView")).setVisibility(View.VISIBLE);
                        for (Playlist playlist : category.getPlaylists()) {
                            assert playlistLayout != null;
                            Button newPlaylistButton = createPlaylistButton(playlist.getName(), buttonReference, playlistLayout);
                            // TODO add loading to player activity
                        }
                    }
                });
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Button createPlaylistButton(String label, Button buttonReference, LinearLayout layoutParent) {
        // creates a button for a specific category or playlist
        Button newButton = new Button(buttonReference.getContext());
        newButton.setLayoutParams(buttonReference.getLayoutParams());
        newButton.setTextAppearance(R.style.TextAppearance_AppCompat_Medium);
        newButton.setBackground(buttonReference.getBackground());
        newButton.setTypeface(buttonReference.getTypeface());
        newButton.setIncludeFontPadding(buttonReference.getIncludeFontPadding());
        newButton.setTextAlignment(buttonReference.getTextAlignment());
        newButton.setTextColor(buttonReference.getTextColors());
        newButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        newButton.setText(label);
        layoutParent.addView(newButton);
        return newButton;
    }
}
