package playlist;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dndplaylist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import domain.Playlist;
import domain.SongCategory;
import player.PlayerView;

// 96-97
public class PlaylistController extends AppCompatActivity {
    ArrayList<SongCategory> playlistCollection;
    Context viewContext;

    PlaylistController(Context viewContext) {
        playlistCollection = new ArrayList<>();
        this.viewContext = viewContext;
    }

//    public ArrayList<SongCategory> getPlaylistCollection() {
//        return playlistCollection;
//    }

//    public void setPlaylistCollection(ArrayList<SongCategory> playlistCollection) {
//        this.playlistCollection = playlistCollection;
//    }

    public void createAppData(final Map<String, View> layoutData) {
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
                                    for (Object playlistData : (ArrayList<?>) Objects.requireNonNull(document.getData().get("Playlists"))) {
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
        final Button backButton = (Button) layoutData.get("backButton");

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
                        assert backButton != null;
                        backButton.setVisibility(View.VISIBLE);
                        for (final Playlist playlist : category.getPlaylists()) {
                            assert playlistLayout != null;
                            Button newPlaylistButton = createPlaylistButton(playlist.getName(), buttonReference, playlistLayout);
                            newPlaylistButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View vi) {
                                    openPlayer(playlist.getName());
                                }
                            });
                        }
                    }
                });
            }
        }
        assert backButton != null;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(layoutData.get("categoryView")).setVisibility(View.VISIBLE);
                Objects.requireNonNull(layoutData.get("playlistView")).setVisibility(View.GONE);
                backButton.setVisibility(View.GONE);
            }
        });

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

    private void openPlayer(String playlistName) {
        Intent intent = new Intent(viewContext, PlayerView.class);
        Bundle b = new Bundle();
        b.putString("playlistName", playlistName);
        intent.putExtras(b);
        viewContext.startActivity(intent);
        finish();
    }
}
