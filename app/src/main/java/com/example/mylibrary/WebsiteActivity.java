package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebsiteActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);

        // to make your website activity reusable, get incoming intent
        Intent intent = getIntent();
        //check if it's not null
        if (null != intent){
            String url = intent.getStringExtra("url");
            webView = findViewById(R.id.webView);
            //load url to the webView
            webView.loadUrl(url);
            //create new WebViewClient to open google.com in our application (otherwise we'll get redirected to google application)
            webView.setWebViewClient(new WebViewClient());
            //you can enable javascript but it is a security issue and it may cause vulnerabilities to your application
//        webView.getSettings().setJavaScriptEnabled(true);

        }

    }

    /**
     * If you want to go back one page after searching for something on google.com
     * you have override this method otherwise you will go back to your application
     */
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }
}