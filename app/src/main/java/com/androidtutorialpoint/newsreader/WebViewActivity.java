package com.androidtutorialpoint.newsreader;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.assad.newsreader.R;
import com.robertsimoes.shareable.Shareable;

import dmax.dialog.SpotsDialog;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;



public class WebViewActivity extends AppCompatActivity {


    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);


        final AlertDialog dialog = new SpotsDialog(WebViewActivity.this);
        dialog.show();






        final WebView myWebView = (WebView) findViewById(R.id.webView);
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");


        //myWebView.setWebViewClient(new WebViewClient());

       // myWebView.getSettings().setJavaScriptEnabled(true);

        myWebView.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //view.loadUrl("javascript:(function() { " +
                  //      "document.getElementById('masthead').style.display='none';})()");
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                // hide element by class name


                dialog.dismiss();
                myWebView.setVisibility(View.VISIBLE);
            }




        });


        FabSpeedDial fabSpeedDial= findViewById(R.id.fabButton);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem item) {
                //TODO: Start some activity


                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_share) {
                    Shareable shareAction = new Shareable.Builder(WebViewActivity.this)
                            .message("")
                            .url(url)
                            .socialChannel(Shareable.Builder.ANY)
                            .build();
                    shareAction.share();

                    return true;
                }
                else if(id==R.id.action_email){

                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,url);
                    startActivity(emailIntent);

                }
                return false;
            }
        });




        if (Build.VERSION.SDK_INT >= 21) {
            myWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            myWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }



        myWebView.setVisibility(View.GONE);
        myWebView.loadUrl(url);

    }
}
