package name.velikodniy.vadim.benderycinema.movies;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MovieInfoLoadTask extends AsyncTask<Void, Void, ArrayList<MovieRecord>> {
    final static String rss_url = "http://kino-bendery.info/rss.xml";

    @Override
    protected ArrayList<MovieRecord> doInBackground(Void... voids) {
        ArrayList<MovieRecord> movies = new ArrayList<>();
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
                movies.add(new MovieRecord(title, sessions, URL));
            } catch (NullPointerException e) {
                continue;
            }
        }

        return movies;
    }
}
