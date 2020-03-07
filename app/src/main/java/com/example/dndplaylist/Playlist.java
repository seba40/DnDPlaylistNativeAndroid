package com.example.dndplaylist;

public class Playlist implements Comparable<Playlist> {

    private String name;

    Playlist(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Playlist other) {
        return this.getName().compareTo(other.getName());
    }
}
