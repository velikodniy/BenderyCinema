package name.velikodniy.vadim.benderycinema;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import name.velikodniy.vadim.benderycinema.movie.Movie;

public class MovieActivity extends AppCompatActivity {

    private String URLWorkaround(String url) {
        return url.replace(
                "http://kino-bendery.infohttp://kino-bendery.info",
                "http://kino-bendery.info"
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra("Movie");

        TextView movieInfoTitle = (TextView) findViewById(R.id.movie_info_title);
        movieInfoTitle.setText(movie.title);

        TextView movieInfoDescription = (TextView) findViewById(R.id.movie_info_description);
        movieInfoDescription.setText(Html.fromHtml(movie.description));

        TextView movieInfoSessions = (TextView) findViewById(R.id.movie_info_sessions);
        movieInfoSessions.setText(movie.sessions);

        ImageView moviePosterImage = (ImageView) findViewById(R.id.movie_info_poster);
        Bitmap bmp;
        try {
            Log.d("Poster URL", URLWorkaround(movie.poster_url));
            bmp = new DownloadPosterAsync().execute(URLWorkaround(movie.poster_url)).get();
        } catch (InterruptedException | ExecutionException e) {
            bmp = null;
        }

        if (bmp != null)
            moviePosterImage.setImageBitmap(bmp);
    }

    private class DownloadPosterAsync extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... args) {
            URL url;
            BufferedOutputStream out;
            InputStream in;
            BufferedInputStream buf;
            try {
                url = new URL(args[0]);
            } catch (MalformedURLException e) {
                return null;
            }

            Bitmap bmp;
            try {
                in = url.openStream();
                buf = new BufferedInputStream(in);
                bmp = BitmapFactory.decodeStream(buf);
                in.close();
                buf.close();
            } catch (IOException e) {
                return null;
            }
            return bmp;
        }
    }
}
