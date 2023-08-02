package com.grigorev.kpmovies.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsResponse {

    @SerializedName("docs")
    private List<Review> reviews;

    public ReviewsResponse(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return "ReviewsResponse{" +
                "reviews=" + reviews +
                '}';
    }
}
