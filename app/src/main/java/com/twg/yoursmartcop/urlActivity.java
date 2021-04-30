package com.twg.yoursmartcop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class urlActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        Intent intent;
        String url = getIntent().getStringExtra("url");

        if (url.equals("south")){
            webView.loadUrl("https://dmsouthwest.delhi.gov.in/police/");

        }else if (url.equals("west")){
            webView.loadUrl("https://districts.delhipolice.gov.in/IndexPage.aspx?id=959");
        }
        else if (url.equals("north")){
            webView.loadUrl("https://districts.delhipolice.gov.in/IndexPage.aspx?id=173");
        }




        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}