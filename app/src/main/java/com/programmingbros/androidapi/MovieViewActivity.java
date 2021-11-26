package com.programmingbros.androidapi;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.programmingbros.androidapi.httpclients.OMDBHttpClient;
import com.programmingbros.androidapi.models.Movie;

public class MovieViewActivity extends AppCompatActivity {
    private ImageView image;
    private TextView title, plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_activity);

        this.image = (ImageView) findViewById(R.id.poster);
        this.title = (TextView) findViewById(R.id.title);
        this.plot = (TextView) findViewById(R.id.description);

        String imdbID = getIntent().getStringExtra("movie_imdbID");

        OMDBHttpClient omdbHttpClient = new OMDBHttpClient();
        Movie movie = omdbHttpClient.getMovieDetails(imdbID);

        Bitmap poster = movie.getPoster();
        if (poster != null)
            this.image.setImageBitmap(poster);
        else
            this.image.setImageResource(R.drawable.defaultposter);

        this.title.setText(movie.title);
        this.plot.setText(movie.plot);
    }
}
