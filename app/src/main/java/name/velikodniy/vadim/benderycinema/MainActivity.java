package name.velikodniy.vadim.benderycinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import name.velikodniy.vadim.benderycinema.movie.Movie;
import name.velikodniy.vadim.benderycinema.movie.MovieAdapter;

public class MainActivity extends AppCompatActivity {

    ArrayList<Movie> movieList = new ArrayList<>();
    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView list = (ListView) findViewById(R.id.listMovies);
        adapter = new MovieAdapter(
                getApplicationContext(),
                R.layout.movielistitem,
                movieList
        );
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Movie movie = adapter.getItem(i);
                                            Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                                            intent.putExtra("URL", movie.url);
                                            startActivity(intent);
                                        }
                                    }
        );

        ArrayList<Movie> movies = Movie.loadMovies();
        if (movies != null) adapter.addAll(movies);
    }
}
