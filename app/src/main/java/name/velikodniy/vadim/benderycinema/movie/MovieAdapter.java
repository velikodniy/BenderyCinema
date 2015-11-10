package name.velikodniy.vadim.benderycinema.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import name.velikodniy.vadim.benderycinema.R;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private ArrayList<Movie> movies;

    public MovieAdapter(Context context, int resource, ArrayList<Movie> movies) {
        super(context, resource, movies);
        this.movies = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.movie_list_item, null);
        }

        Movie movie = movies.get(position);
        if (movie != null) {
            TextView title_view = (TextView) v.findViewById(R.id.movie_title);
            TextView sessions_view = (TextView) v.findViewById(R.id.movie_sessions);

            if (title_view != null) {
                title_view.setText(movie.title);
            }

            if (sessions_view != null) {
                sessions_view.setText(movie.sessions);
            }
        }
        return v;
    }
}
