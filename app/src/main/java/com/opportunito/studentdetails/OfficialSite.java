//created by Abin Shoby ,R3
//loads the official site

package com.opportunito.studentdetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OfficialSite extends AppCompatActivity {
    WebView offs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_site);
        offs=(WebView)findViewById(R.id.offs);
        offs.getSettings().setJavaScriptEnabled(true);
        offs.setWebViewClient(new WebViewClient());     //set webview client for preview
        offs.loadUrl("http://www.tkmce.ac.in");



    }

    public void goback(View view) { //goto previous url
        if(offs.canGoBack())
        {
            offs.goBack();
        }
    }

    public void refresh(View view) {
        offs.reload();
    } //refresh option
}
