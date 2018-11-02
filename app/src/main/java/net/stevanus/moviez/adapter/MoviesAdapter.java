package net.stevanus.moviez.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.stevanus.moviez.R;
import net.stevanus.moviez.constants.Constants;
import net.stevanus.moviez.listeners.RecyclerViewClickListener;
import net.stevanus.moviez.model.Movie;

import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> movieList;
    private RecyclerViewClickListener recyclerViewClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    
        public TextView title;

        public ImageView thumbnail;


        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }

        @Override
        public void onClick(View v) {
            recyclerViewClickListener.recyclerViewListClicked(v, movieList.get(getLayoutPosition()));
        }
    }



    public MoviesAdapter(Context mContext, List<Movie> movieList, RecyclerViewClickListener recyclerViewClickListener) {
        this.mContext = mContext;
        this.movieList = movieList;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.title.setText(movie.getTitle());
        Glide.with(mContext).load(Constants.MOVIEDB_SMALL_POSTER_URL + movie.getPosterPath()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
