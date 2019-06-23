package yatsekk.example.com.mymovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import yatsekk.example.com.mymovies.adapters.MovieAdapter;
import yatsekk.example.com.mymovies.data.FavoriteMovie;
import yatsekk.example.com.mymovies.data.MainViewModel;
import yatsekk.example.com.mymovies.data.Movie;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavoriteMovies;
    private MovieAdapter adapter;
    private MainViewModel viewModel;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itemMain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.itemFavorite:
                Intent intentToFavorite = new Intent(this, FavoriteActivity.class);
                startActivity(intentToFavorite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerViewFavoriteMovies = findViewById(R.id.recyclerViewFavoriteMovies);
        recyclerViewFavoriteMovies.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MovieAdapter();
        recyclerViewFavoriteMovies.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        LiveData<List<FavoriteMovie>> favoriteMovies = viewModel.getFavoriteMovies();
        favoriteMovies.observe(this, new Observer<List<FavoriteMovie>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteMovie> favoriteMovies) {
                List<Movie> movies = new ArrayList<>();
                if (favoriteMovies != null) {
                    movies.addAll(favoriteMovies);
                    adapter.setMovies(movies);
                }
            }
        });
        adapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                Movie movie = adapter.getMovies().get(position);
                Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
                intent.putExtra("id", movie.getId());
                startActivity(intent);
            }
        });
    }
}
