package com.programmingbros.androidapi.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Movie {
    private final String DEFAULT_IMG = "https://motivatevalmorgan.com/wp-content/uploads/2016/06/default-movie-1-3.jpg";
    public String title;
    public String year;
    private String imdbId;
    private String posterUrl;

    public Movie(String title, String year, String imdbId, String posterUrl) {
        this.title = title;
        this.year = year;
        this.imdbId = imdbId;
        this.posterUrl = posterUrl != null && !posterUrl.isEmpty() ? posterUrl : DEFAULT_IMG;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Bitmap getPoster() {
        try {
            URL newurl = new URL(this.posterUrl);
            return BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
