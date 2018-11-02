package net.stevanus.moviez;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.stevanus.moviez.constants.Constants;
import net.stevanus.moviez.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {


    @BindView(R.id.originalTitle)
    TextView originalTitle;


    @BindView(R.id.title)
    TextView title;


    @BindView(R.id.releaseDate)
    TextView releaseDate;


    @BindView(R.id.cover)
    ImageView cover;


    @BindView(R.id.rating)
    TextView rating;


    @BindView(R.id.overview)
    TextView overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        Movie movie = (Movie) getIntent().getExtras().getSerializable(Constants.INTENT_MOVIE_DETAIL);

        if (movie != null) {
            Glide.with(this).load(Constants.MOVIEDB_LARGE_POSTER_URL + movie.getPosterPath()).into(cover);
            title.setText(movie.getTitle());
            originalTitle.setText(movie.getOriginalTitle());
            releaseDate.setText(movie.getReleaseDate());
            rating.setText(String.valueOf(movie.getVoteAverage()));
            overview.setText(movie.getOverview());
        }

    }
}
