package domain;

import java.util.ArrayList;

public class SongCategory implements Comparable<SongCategory> {

    private String name;
    private ArrayList<Playlist> playlists;

//    public SongCategory(String name, ArrayList<Playlist> playlists) {
//        this.name = name;
//        this.playlists = playlists;
//    }

    public SongCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    @Override
    public int compareTo(SongCategory other) {
        return this.getName().compareTo(other.getName());
    }
}
