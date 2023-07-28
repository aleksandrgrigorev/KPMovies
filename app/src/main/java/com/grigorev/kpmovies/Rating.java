package com.grigorev.kpmovies;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("kp")
    private String kp;

    public String getKp() {
        return kp;
    }

    public Rating(String kp) {
        this.kp = kp;
    }

    @NonNull
    @Override
    public String toString() {
        return "Rating{" +
                "kp='" + kp + '\'' +
                '}';
    }
}
