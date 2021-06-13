package com.twg.yoursmartcop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.Timer;
import java.util.TimerTask;

public class urlActivity extends AppCompatActivity {
    private WebView webView;
    private String[] urls = new String[]{"https://content3.jdmagicbox.com/comp/delhi/18/011p2077918/catalogue/police-station-connaught-place-delhi-police-1vtyoe.jpg?clr=660000","https://english.cdn.zeenews.com/sites/default/files/2020/08/10/878202-delhipolicepicture.jpg",
            "https://img.kesari.tv/2018_8image_19_04_378540744screenshot2018-08-01at5.52.40pm.png"};
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    TextView tvLoc, tvPhn, tvEmail, tvSHO, tvSI;
    private static final int REQUEST_PHONE_CALL = 1;
    CollapsingToolbarLayout ct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
//        webView = (WebView) findViewById(R.id.webView);
//        webView.setWebViewClient(new WebViewClient());
        tvLoc = findViewById(R.id.tvLocation);
        tvPhn = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvSHO = findViewById(R.id.tvSHO);
        tvSI = findViewById(R.id.tvSI);

        Intent intent;
        String url = getIntent().getStringExtra("url");



        if (url.equals("CP")){
            tvLoc.setText("Connaught Place");
            tvPhn.setText("011 2374 7100");
            tvEmail.setText("splcp-int-dl@nic.in");
            tvSHO.setText("SHO Chetan Tambe");
            tvSI.setText("SI Devdutt Parulkar");
            checkcallPer();
            tvPhn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tvPhn.getText().toString()));
                    startActivity(intent1);
                }
            });


        }else if (url.equals("saket")){
            tvLoc.setText("Rohini Sector 11");
            tvPhn.setText("011 2374 5900");
            tvEmail.setText("dprohini-int-dl@nic.in");
            tvSHO.setText("SHO Rahul Mishra");
            tvSI.setText("SI Digvijay Singh");
            checkcallPer();
            tvPhn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tvPhn.getText().toString()));
                    startActivity(intent1);
                }
            });
        }
        else if (url.equals("Police Station Rohini North")){
            webView.loadUrl("https://districts.delhipolice.gov.in/IndexPage.aspx?id=173");
        }

        init();


//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
    }

    private void checkcallPer() {
        int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_PHONE_CALL);
        } else {
            //Toast.makeText(this, "Allow Call Permission", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(urlActivity.this, urls));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = urls.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });


    }

}