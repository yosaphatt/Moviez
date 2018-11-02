package net.stevanus.moviez.listeners;

import android.view.View;

import net.stevanus.moviez.model.Movie;


public interface RecyclerViewClickListener {

    void recyclerViewListClicked(View v, Movie currentMovie);
}
