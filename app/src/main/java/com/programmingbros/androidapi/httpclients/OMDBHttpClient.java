package com.programmingbros.androidapi.httpclients;

import android.os.StrictMode;
import android.util.JsonReader;
import android.util.Log;

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
    public static final String TYPE = "&type=movie";
    private static final String MOVIE_INFO_URL = BASE_URL + APPID_URL + TYPE + "&i=";
    private static final String MOVIE_SEARCH_URL = BASE_URL + APPID_URL + TYPE + "&s=";


    public List<Movie> queryMovie(String query) {
        // json

        /*
            {
              "Search": [
                {
                  "Title": "The Mechanic",
                  "Year": "2011",
                  "imdbID": "tt0472399",
                  "Type": "movie",
                  "Poster": "https://m.media-amazon.com/images/M/MV5BMjEyMjk1NjI1M15BMl5BanBnXkFtZTcwODcyNjAxNA@@._V1_SX300.jpg"
                },
                {
                  "Title": "Mechanic: Resurrection",
                  "Year": "2016",
                  "imdbID": "tt3522806",
                  "Type": "movie",
                  "Poster": "https://m.media-amazon.com/images/M/MV5BMjYwODExNzUwMV5BMl5BanBnXkFtZTgwNTgwNjUyOTE@._V1_SX300.jpg"
                }
              ],
              "totalResults": "2",
              "Response": "True"
            }
        */

        HttpsURLConnection con = null;
        InputStream is = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            con = (HttpsURLConnection) (new URL(MOVIE_SEARCH_URL + query)).openConnection();
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

    public Movie getMovieDetails(String imdbID) {
        // json
        /*
            {
              "Title": "The Mechanic",
              "Year": "2011",
              "Rated": "R",
              "Released": "28 Jan 2011",
              "Runtime": "93 min",
              "Genre": "Action, Crime, Thriller",
              "Director": "Simon West",
              "Writer": "Richard Wenk, Lewis John Carlino",
              "Actors": "Jason Statham, Ben Foster, Donald Sutherland",
              "Plot": "An elite hitman teaches his trade to an apprentice who has a connection to one of his previous victims.",
              "Language": "English",
              "Country": "United States",
              "Awards": "1 nomination",
              "Poster": "https://m.media-amazon.com/images/M/MV5BMjEyMjk1NjI1M15BMl5BanBnXkFtZTcwODcyNjAxNA@@._V1_SX300.jpg",
              "Ratings": [
                {
                  "Source": "Internet Movie Database",
                  "Value": "6.6/10"
                },
                {
                  "Source": "Rotten Tomatoes",
                  "Value": "53%"
                },
                {
                  "Source": "Metacritic",
                  "Value": "49/100"
                }
              ],
              "Metascore": "49",
              "imdbRating": "6.6",
              "imdbVotes": "156,632",
              "imdbID": "tt0472399",
              "Type": "movie",
              "DVD": "17 May 2011",
              "BoxOffice": "$29,121,498",
              "Production": "N/A",
              "Website": "N/A",
              "Response": "True"
            }
        * */

        HttpsURLConnection con = null;
        InputStream is = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            con = (HttpsURLConnection) (new URL(MOVIE_INFO_URL + imdbID)).openConnection();
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

            return new Movie(jsonObject.get("Title").asString(), jsonObject.get("Plot").asString(), jsonObject.get("Year").asString(), imdbID, jsonObject.get("Poster").asString());
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
