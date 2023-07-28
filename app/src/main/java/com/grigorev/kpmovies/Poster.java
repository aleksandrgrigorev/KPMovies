package com.grigorev.kpmovies;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Poster {

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public Poster(String url) {
        this.url = url;
    }

    @NonNull
    @Override
    public String toString() {
        return "Poster{" +
                "url=" + url + '\'' +
                '}';
    }
}