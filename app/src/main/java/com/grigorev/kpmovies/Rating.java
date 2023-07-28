package com.grigorev.kpmovies;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("kp")
    private double kp;

    public double getKp() {
        return kp;
    }

    public Rating(double kp) {
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
