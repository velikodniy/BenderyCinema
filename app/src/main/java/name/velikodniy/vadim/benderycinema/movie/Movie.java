package name.velikodniy.vadim.benderycinema.movie;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Movie implements Serializable {
    public String title;
    public String sessions;
    public String url;
    public String poster_url;
    public String description;

    public Movie(String title, String sessions, String url, String poster_url, String description) {
        this.title = title;
        this.sessions = sessions;
        this.url = url;
        this.poster_url = poster_url;
        this.description = description;
    }

    public static ArrayList<Movie> loadMovies() {
        AsyncTask<Void, Void, ArrayList<Movie>> infoLoadTask = new MovieInfoLoadAsync().execute();
        try {
            return infoLoadTask.get();
        } catch (InterruptedException|ExecutionException e) {
            return null;
        }
    }
}

class MovieInfoLoadAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {
    final static String rss_url = "http://kino-bendery.info/rss.xml";

    @Override
    protected ArrayList<Movie> doInBackground(Void... voids) {
        ArrayList<Movie> movies = new ArrayList<>();
        Document doc;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(rss_url);
        } catch (Exception e) {
            return movies;
        }

        NodeList items = doc.getElementsByTagName("item");
        for (int i = 0; i < items.getLength(); i++) {
            Element item = (Element)items.item(i);
            try {
                String title = item.getElementsByTagName("title").item(0).getTextContent();
                String sessions = item.getElementsByTagName("seanses").item(0).getTextContent();
                String URL = item.getElementsByTagName("link").item(0).getTextContent();
                Element poster_url_el = (Element)item.getElementsByTagName("enclosure").item(0);
                String poster_url = poster_url_el.getAttribute("url");
                String description = item.getElementsByTagName("description").item(0).getTextContent();
                movies.add(new Movie(title, sessions, URL, poster_url, description));
            } catch (NullPointerException e) { }
        }
        return movies;
    }
}
