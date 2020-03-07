package com.example.dndplaylist;

import java.util.ArrayList;

public class SongCategory implements Comparable<SongCategory> {

    private String name;
    private ArrayList<Playlist> playlists;

    SongCategory (String name, ArrayList<Playlist> playlists) {
        this.name = name;
        this.playlists = playlists;
    }

    SongCategory(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    @Override
    public int compareTo(SongCategory other) {
        return this.getName().compareTo(other.getName());
    }
}
