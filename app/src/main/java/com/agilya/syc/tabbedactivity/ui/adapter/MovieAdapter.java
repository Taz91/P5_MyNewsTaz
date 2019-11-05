package com.agilya.syc.tabbedactivity.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agilya.syc.tabbedactivity.R;
import com.agilya.syc.tabbedactivity.model.Movie;
import com.agilya.syc.tabbedactivity.ui.utils.MovieClickListener;
import com.agilya.syc.tabbedactivity.ui.viewholder.MovieViewHolder;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("ALL")
public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private final MovieClickListener movieClickListener;
    private final List<Movie> movieList;

    public MovieAdapter(List<Movie> movieList, MovieClickListener movieClickListener) {
        this.movieList = movieList;
        this.movieClickListener = movieClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card_view, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = this.movieList.get(position);
        holder.bind(movie, movieClickListener);
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }

    @Override
    public void onViewRecycled(MovieViewHolder holder) {
        super.onViewRecycled(holder);
    }
}
