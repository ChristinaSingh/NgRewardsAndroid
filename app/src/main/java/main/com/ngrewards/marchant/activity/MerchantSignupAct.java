package main.com.ngrewards.marchant.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import ir.alirezabdn.wp7progress.WP10ProgressBar;
import main.com.ngrewards.R;
import main.com.ngrewards.activity.WebViewAc;

public class MerchantSignupAct extends AppCompatActivity {
    boolean loadingFinished = true;
    boolean redirect = false;
    private WebView TermsWV;
    private String Url,tid="";
    private WP10ProgressBar loader_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        TermsWV = (WebView) findViewById(R.id.TermsWV3);
        loader_page = findViewById(R.id.loader_page);
        loader_page.showProgressBar();

        Random addition1 = new Random();
        int additionint1 = addition1.nextInt(100) + 1;
        String random_no = String.valueOf(additionint1);

        Url = "https://myngrewards.com/signup-merchant.php?affiliate_name=&affiliate_no=&how_invited_you=&country=&source=";

        TermsWV.getSettings().setLoadsImagesAutomatically(true);
        TermsWV.getSettings().setJavaScriptEnabled(true);
        TermsWV.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        TermsWV.setWebViewClient(new HelloWebViewClient());
        TermsWV.getSettings().setDomStorageEnabled(true);
        TermsWV.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        TermsWV.getSettings().setLoadsImagesAutomatically(true);
        TermsWV.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        TermsWV.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TermsWV.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        Log.e("signup_url>>>", Url);
        TermsWV.loadUrl(Url);
        TermsWV.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
                Log.e("should override url====", urlNewString);

                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                view.loadUrl(urlNewString);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap facIcon) {
                loadingFinished = false;
                //SHOW LOADING IF IT ISNT ALREADY VISIBLE
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("on page finish url====", url);
                // https://myngrewards.com/welcome-merchant.php?tid=152
                if (!redirect) {
                    loadingFinished = true;
                }
                if (loadingFinished && !redirect) {
                    loader_page.hideProgressBar();
                } else {
                    redirect = false;
                }

                if(url.contains("welcome-merchant.php")){
// Parse URI
                    Uri uri = Uri.parse(url);

// Get the query parameter
                     tid = uri.getQueryParameter("tid");

                    if (tid != null) {
                        Log.e("TAG", "TID value: " + tid);
                    } else {
                        Log.e("TAG", "TID not found in URL");
                    }
                    startActivity(new Intent(MerchantSignupAct.this,BusinessSignupAct.class)
                            .putExtra("merchantId",tid));
                    finish();
                }

            }
        });
    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}