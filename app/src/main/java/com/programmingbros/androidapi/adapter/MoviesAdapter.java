package com.programmingbros.androidapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.programmingbros.androidapi.MovieViewActivity;
import com.programmingbros.androidapi.R;
import com.programmingbros.androidapi.models.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private List<Movie> movies;

    public MoviesAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public MoviesAdapter.MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.movie_item, parent, false);

        return new MoviesViewHolder(v, parent.getContext());
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MoviesViewHolder holder, int position) {
        Movie movie = this.movies.get(position);
        if (movie != null) {
            holder.movie = movie;

            Bitmap poster = movie.getPoster();
            if (poster != null)
                holder.imageView.setImageBitmap(poster);
            else
                holder.imageView.setImageResource(R.drawable.defaultposter);
            holder.titleView.setText(movie.title);
            holder.yearView.setText(movie.year);
        }
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }

    public static class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Context context;
        public Movie movie;
        public ImageView imageView;
        public TextView titleView, yearView;

        public MoviesViewHolder(View itemView, Context ctx) {
            super(itemView);

            itemView.setOnClickListener(this);
            this.context = ctx;
            this.imageView = (ImageView) itemView.findViewById(R.id.movie_img);
            this.titleView = (TextView) itemView.findViewById(R.id.titulo);
            this.yearView = (TextView) itemView.findViewById(R.id.ano);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(this.context, MovieViewActivity.class);
            intent.putExtra("movie_imdbID", this.movie.getImdbId());

            this.context.startActivity(intent);
        }
    }
}
