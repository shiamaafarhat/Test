package com.khadmqtr.khadm.alharthy;




import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //webView
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSeting = webView.getSettings();
        webSeting.setJavaScriptEnabled(true);
        webSeting.setLightTouchEnabled(true);
        webSeting.setGeolocationEnabled(true);
        webSeting.setMediaPlaybackRequiresUserGesture(false);
        webSeting.setAppCacheEnabled(true);
        webView.getSettings().setAllowContentAccess(true);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);

        webView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    progressDialog.show();
                }
                if (newProgress == 100) {
                    progressDialog.dismiss();
                }

            }

        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
            public void onPageFinished(WebView view, String url) {
                getSupportActionBar().setTitle(webView.getTitle());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
          Log.e("tag","url overrride url  = "+ url);

                if( URLUtil.isNetworkUrl(url) ) {
                    if(url.startsWith("mailto:")){
                        Intent intent = null;
                        try {
                            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        view.getContext().startActivity(intent);
                    }
                    return false;
                }
                    if(url.startsWith("youtube:")){
                        Intent intent = null;
                        try {
                            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        view.getContext().startActivity(intent);
                        return false;
                    }



                if (url.startsWith("whatsapp:")) {

                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.putExtra(Intent.EXTRA_TEXT, "sorry");
                    startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
//                    view.reload();

                    return true;
                }

//
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity( intent );
                return false;
//
//
//
            }

        });


        webView.loadUrl("https://www.google.com/");


    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed(); }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                case KeyEvent.KEYCODE_MENU:
                    webView.loadUrl("javascript:open_menu()");
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

}




//    private class ZoftinoWebViewClient extends WebViewClient {
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading Data...");
//        progressDialog.setCancelable(false);
//
//        //webView
//        webView = (WebView) findViewById(R.id.webView);
//        webView.setWebViewClient(new WebViewClient());
//        WebSettings webSeting = webView.getSettings();
//        webSeting.setJavaScriptEnabled(true);
//        webSeting.setLightTouchEnabled(true);
//        webSeting.setGeolocationEnabled(true);
////      webSeting.setMediaPlaybackRequiresUserGesture(false);
//        webSeting.setAppCacheEnabled(true);
//        webView.getSettings().setAllowContentAccess(true);
//        webView.setWebChromeClient(new WebChromeClient() {
//
//            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress < 100) {
//                    progressDialog.show();
//                }
//                if (newProgress == 100) {
//                    progressDialog.dismiss();
//                }
//
//            }
//
//        });
//
//
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
//                super.onReceivedError(view, errorCode, description, failingUrl);
//            }
//            public void onPageFinished(WebView view, String url) {
////                getSupportActionBar().setTitle(webView.getTitle());
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url ) {
//                Log.e("tag","url overrride url  = "+ url);
//
//                if( URLUtil.isNetworkUrl(url) ) {
//                    if(url.startsWith("mailto:")){
//                        Intent intent = null;
//                        try {
//                            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
//                        } catch (URISyntaxException e) {
//                            e.printStackTrace();
//                        }
//                        view.getContext().startActivity(intent);
//                    }
//                    return false;
//                }
//                    if(url.startsWith("youtube:")){
//                        Intent intent = null;
//                        try {
//                            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
//                        } catch (URISyntaxException e) {
//                            e.printStackTrace();
//                        }
//                        view.getContext().startActivity(intent);
//                        return false;
//                    }
//
//
//
//                if (url.startsWith("whatsapp:")) {
//
//                    Intent share = new Intent(Intent.ACTION_SEND);
//                    share.setType("text/plain");
//                    share.putExtra(Intent.EXTRA_TEXT, "sorry");
//                    startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
////                    view.reload();
//
//                    return true;
//                }
//
//
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity( intent );
//                return true;
//
//
//
//            }
//
//        });
////            public boolean shouldOverrideUrlLoading(WebView view, String url) {  if(url.startsWith("www.youtube.Com")) {
////                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
////                return true;
////            }view.loadUrl(url);
////            return false;
////            }
////
////        });
//
//        //https://www.khadmqtr.com
//        //https://www.dawratedu.com/index.php
//        webView.loadUrl("https://www.khadmqtr.com");
//
//
//    }
//    @Override
//    public void onBackPressed() {
//        if (webView.canGoBack()){
//            webView.goBack();
//        }else{
//            super.onBackPressed(); }
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (event.getAction() == KeyEvent.ACTION_DOWN) {
//            switch (keyCode) {
//                case KeyEvent.KEYCODE_BACK:
//                    if (webView.canGoBack()) {
//                        webView.goBack();
//                    } else {
//                        finish();
//                    }
//                case KeyEvent.KEYCODE_MENU:
//                    webView.loadUrl("javascript:open_menu()");
//                    return true;
//            }
//
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    private boolean appInstalledOrNot(String uri) {
//        PackageManager pm = getPackageManager();
//        try {
//            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
//            return true;
//        } catch (PackageManager.NameNotFoundException e) {
//        }
//
//        return false;
//    }
//}
//
//
//
