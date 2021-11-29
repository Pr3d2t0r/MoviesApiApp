package com.programmingbros.androidapi.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

public class Movie {
    private final String DEFAULT_IMG = "https://motivatevalmorgan.com/wp-content/uploads/2016/06/default-movie-1-3.jpg";
    public String title;
    public String plot;
    public String year;
    private String imdbId;
    private Bitmap poster;

    public Movie(String title, String plot, String year, String imdbId, String posterUrl) {
        this(title, year, imdbId, posterUrl);
        this.plot = plot;
    }

    public Movie(String title, String year, String imdbId, String posterUrl) {
        this.title = title;
        this.year = year;
        this.imdbId = imdbId;
        this.poster = this.setPoster(posterUrl != null && !posterUrl.isEmpty() ? posterUrl : DEFAULT_IMG);
    }

    public String getImdbId() {
        return imdbId;
    }

    private Bitmap setPoster(String posterUrl) {
        try {
            return BitmapFactory.decodeStream(new URL(posterUrl).openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Bitmap getPoster() {
        return this.poster;
    }
}
