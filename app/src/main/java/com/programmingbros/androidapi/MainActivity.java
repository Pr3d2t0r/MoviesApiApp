package com.programmingbros.androidapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eclipsesource.json.JsonObject;
import com.programmingbros.androidapi.adapter.MoviesAdapter;
import com.programmingbros.androidapi.httpclients.OMDBHttpClient;
import com.programmingbros.androidapi.models.Movie;

import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText queryView;
    private Button button;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.queryView = (EditText) findViewById(R.id.query);
        this.button = (Button) findViewById(R.id.queryBtn);

        this.button.setOnClickListener(this);

        this.recyclerView = (RecyclerView) findViewById(R.id.movies);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(null);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.queryBtn)
            queryMovies(this.queryView.getText().toString());
    }

    private void queryMovies(String query) {
        if (query.isEmpty())
            return;

        OMDBHttpClient omdbHttpClient = new OMDBHttpClient();
        this.recyclerView.setAdapter(new MoviesAdapter(omdbHttpClient.queryMovie(query)));
    }
}