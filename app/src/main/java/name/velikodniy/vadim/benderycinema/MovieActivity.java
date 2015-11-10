package name.velikodniy.vadim.benderycinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(url);
    }
}
