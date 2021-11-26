package com.programmingbros.androidapi;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.programmingbros.androidapi.adapter.MoviesAdapter;
import com.programmingbros.androidapi.httpclients.OMDBHttpClient;
import com.programmingbros.androidapi.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class JsonTask extends AsyncTask<String, Integer, Object> {
    private final OMDBHttpClient httpClient = new OMDBHttpClient();
    private String type;
    private View[] views;

    public JsonTask() {
    }
    public JsonTask(View... views) {
        this.views = views;
    }

    @Override
    protected Object doInBackground(String... args) {
        String content;
        try {
            this.type = args[0];
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            return null;
        }

        try {
            content = args[1];
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            return null;
        }

        if (this.type.equals("query")){
            String data = httpClient.queryMovie(content);
            JsonObject jsonObject = JsonObject.readFrom(data);

            List<Movie> movies = new ArrayList<>();
            for (JsonValue item : jsonObject.get("Search").asArray()) {
                JsonObject movieObject = item.asObject();

                movies.add(new Movie(movieObject.get("Title").asString(),
                                        movieObject.get("Year").asString(),
                                        movieObject.get("imdbID").asString(),
                                        movieObject.get("Poster").asString()));
            }
            return movies;
        }else if (this.type.equals("details")){
            String data = httpClient.getMovieDetails(content);
            JsonObject jsonObject = JsonObject.readFrom(data);
            return new Movie(jsonObject.get("Title").asString(), jsonObject.get("Plot").asString(), jsonObject.get("Year").asString(), content, jsonObject.get("Poster").asString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object obj) {
        super.onPostExecute(obj);
        if (this.type.equals("query")){
            ((RecyclerView)this.views[1]).setAdapter(new MoviesAdapter((ArrayList<Movie>) obj));
        }else if (this.type.equals("details")){
            Movie movie = (Movie) obj;
            Bitmap poster = movie.getPoster();
            if (poster != null)
                ((ImageView) this.views[1]).setImageBitmap(poster);
            else
                ((ImageView) this.views[1]).setImageResource(R.drawable.defaultposter);

            ((TextView) this.views[2]).setText(movie.title);
            ((TextView) this.views[3]).setText(movie.plot);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        ((ProgressBar) this.views[0]).setProgress(values[0]);
    }
}
