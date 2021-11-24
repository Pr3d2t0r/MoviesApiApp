package com.programmingbros.androidapi.httpclients;

import android.os.StrictMode;
import android.util.JsonReader;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.programmingbros.androidapi.models.Movie;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class OMDBHttpClient {
    private static final String BASE_URL = "https://www.omdbapi.com/?";
    private static final String APPID_URL = "apikey=56049e69";

    public List<Movie> queryMovie(String query) {
        HttpsURLConnection con = null;
        InputStream is = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            con = (HttpsURLConnection) (new URL(BASE_URL + APPID_URL + "&type=movie&s=" + query)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect();

            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line = null;

            while((line = br.readLine()) != null)
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();

            JsonObject jsonObject = JsonObject.readFrom(buffer.toString());

            List<Movie> movies = new ArrayList<>();
            for (JsonValue item : jsonObject.get("Search").asArray()) {
                JsonObject movieObject = item.asObject();

                movies.add(new Movie(movieObject.get("Title").asString(), movieObject.get("Year").asString(), movieObject.get("imdbID").asString(), movieObject.get("Poster").asString()));
            }

            return movies;
        }  catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            try {
                is.close();
            } catch (Throwable t) {
            }

            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }

        return null;
    }
}
