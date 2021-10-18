package com.socketforpy;

import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebViewClient extends WebViewClient {
    //prevent overwriting successfully loaded pages
    private int onLoadResourceCount = 0;

    @Override
    //prevent overwriting successfully loaded pages
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        onLoadResourceCount++;
    }

    //load local error page
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        //this function doesn't work perfectly yet
        if (onLoadResourceCount <= 1) {
            view.loadUrl("file:///android_asset/index.html");
        }
    }
}
