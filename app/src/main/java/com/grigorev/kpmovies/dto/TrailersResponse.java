package com.grigorev.kpmovies.dto;

import com.google.gson.annotations.SerializedName;

public class TrailersResponse {

    @SerializedName("videos")
    private TrailersList trailersList;

    public TrailersResponse(TrailersList trailersList) {
        this.trailersList = trailersList;
    }

    public TrailersList getTrailersList() {
        return trailersList;
    }

    @Override
    public String toString() {
        return "TrailerResponse{" +
                "trailersList=" + trailersList +
                '}';
    }
}
