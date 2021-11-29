package com.programmingbros.androidapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.programmingbros.androidapi.adapter.MoviesAdapter;
import com.programmingbros.androidapi.httpclients.OMDBHttpClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText queryView;
    private Button button;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.queryView = (EditText) findViewById(R.id.query);
        this.button = (Button) findViewById(R.id.queryBtn);

        this.button.setOnClickListener(this);

        this.recyclerView = (RecyclerView) findViewById(R.id.movies);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.queryBtn)
            queryMovies(this.queryView.getText().toString());
    }

    private void queryMovies(String query) {
        if (query.isEmpty())
            return;

        JsonTask jt = new JsonTask(this.progressBar, this.recyclerView);
        jt.execute("query", query);
    }
}