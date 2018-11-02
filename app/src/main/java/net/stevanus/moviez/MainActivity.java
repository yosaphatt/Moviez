package net.stevanus.moviez;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.stevanus.moviez.listeners.RecyclerViewClickListener;
import net.stevanus.moviez.model.Movie;
import net.stevanus.moviez.utils.GridSpacingItemDecoration;
import net.stevanus.moviez.adapter.MoviesAdapter;
import net.stevanus.moviez.api.RetrofitClient;
import net.stevanus.moviez.api.MovieDBApi;
import net.stevanus.moviez.constants.Constants;
import net.stevanus.moviez.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity implements Callback<MoviesResponse>, RecyclerViewClickListener {

    private RecyclerView recyclerView;
    private final static String TAG = MainActivity.class.getSimpleName();
    private MovieDBApi movieDBApi;
    private Call<MoviesResponse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        movieDBApi = RetrofitClient.getClient().create(MovieDBApi.class);

        call = movieDBApi.getPopularMovies(Constants.MOVIEDB_API_KEY);
        call.enqueue(this);
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
        Log.i(TAG, String.valueOf(response.code()));
        if (response.code() == 200) {
            MoviesAdapter adapter = new MoviesAdapter(this, response.body().getMovieResults(), this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onFailure(Call<MoviesResponse> call, Throwable t) {
        Log.e(TAG, t.getLocalizedMessage());
    }

    @Override
    public void recyclerViewListClicked(View v, Movie currentMovie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Constants.INTENT_MOVIE_DETAIL, currentMovie);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 0, 0, getString(R.string.most_popular));
        menu.add(Menu.NONE, 1, 1, getString(R.string.top_rated));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                call = movieDBApi.getPopularMovies(Constants.MOVIEDB_API_KEY);
                call.enqueue(this);
                break;

            case 1:
                call = movieDBApi.getTopRatedMovies(Constants.MOVIEDB_API_KEY);
                call.enqueue(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
