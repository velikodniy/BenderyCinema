package name.velikodniy.vadim.benderycinema;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import name.velikodniy.vadim.benderycinema.movies.MovieInfoLoadTask;
import name.velikodniy.vadim.benderycinema.movies.MovieRecord;
import name.velikodniy.vadim.benderycinema.movies.MovieRecordAdapter;

public class MainActivity extends AppCompatActivity {

    ArrayList<MovieRecord> movieList = new ArrayList<>();
    MovieRecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView list = (ListView) findViewById(R.id.listMovies);
        adapter = new MovieRecordAdapter(
                getApplicationContext(),
                R.layout.movielistitem,
                movieList
            );
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            MovieRecord movie = adapter.getItem(i);
                                            Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                                            intent.putExtra("URL", movie.url);
                                            startActivity(intent);
                                        }
                                    }
        );

        AsyncTask<Void, Void, ArrayList<MovieRecord>> infoLoadTask = new MovieInfoLoadTask().execute();
        try {
            ArrayList<MovieRecord> movies = infoLoadTask.get();
            adapter.addAll(movies);
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
    }
}
