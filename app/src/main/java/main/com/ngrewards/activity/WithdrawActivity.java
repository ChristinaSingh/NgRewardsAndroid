package main.com.ngrewards.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import main.com.ngrewards.R;
import main.com.ngrewards.androidmigx.MainTabActivity;
import main.com.ngrewards.beanclasses.MemberDetail;
import main.com.ngrewards.constant.BaseUrl;
import main.com.ngrewards.constant.MySession;

public class WithdrawActivity extends AppCompatActivity {
    private ProgressBar progresbar;
    private EditText ngcashavb;
    private RelativeLayout backlay ;
    TextView avbngcash,tvAccountType,applytv,btnWithdraw;
    private String card_id = "", apply_ngcassh = "0", card_number = "", card_brand = "", customer_id = "",member_ngcash="",user_id="";

    private double ngcash_val = 0, total_amt_calculate = 0, apply_ng_cash = 0;
    ImageView addlay;
    private MySession mySession;
    JSONObject jsonObject1;
    boolean loadingFinished = true;
    boolean redirect = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        mySession = new MySession(this);
        String user_log_data = mySession.getKeyAlldata();
        if (user_log_data == null) {
        }else {
            try {

                JSONObject jsonObject = new JSONObject(user_log_data);
                String message = jsonObject.getString("status");
                if (message.equalsIgnoreCase("1")) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                    user_id = jsonObject1.getString("id");
                }
            } catch (JSONException ee) {
                ee.printStackTrace();
            }
        }

        idinit();

    }
    private void idinit() {
        ngcashavb = findViewById(R.id.ngcashavb);
        avbngcash = findViewById(R.id.avbngcash);
        progresbar = findViewById(R.id.progresbar);
        addlay = findViewById(R.id.addlay);
        backlay = findViewById(R.id.backlay);
        tvAccountType = findViewById(R.id.tvAccountType);
        applytv = findViewById(R.id.applytv);
        btnWithdraw = findViewById(R.id.btnWithdraw);
        backlay.setOnClickListener(v -> {
           finish();
        });



        addlay.setOnClickListener(v -> {
             try {
                 if(jsonObject1!=null){
                     if(jsonObject1.getString("member_stripe_account_id")==null || jsonObject1.getString("member_stripe_account_id").equalsIgnoreCase("")
                             && jsonObject1.getString("stripe_account_login_link")==null || jsonObject1.getString("stripe_account_login_link").equalsIgnoreCase("") ){
                       new  GenerateStripeAccount().execute();
                     }

                     else {
                         startActivity(new Intent(WithdrawActivity.this, StripeExpressAcountAct.class));
                     }
                 }             }catch (Exception e){

             }
        });

        new GetProfile().execute();
/*

        if (BaseActivity.member_ngcash == null || BaseActivity.member_ngcash.equalsIgnoreCase("0") || BaseActivity.member_ngcash.equalsIgnoreCase("") || BaseActivity.member_ngcash.equalsIgnoreCase("0.0") || BaseActivity.member_ngcash.equalsIgnoreCase("null")) {
            avbngcash.setText("$0.00");
        } else {
            avbngcash.setText("$" + BaseActivity.member_ngcash);
            ngcash_val = Double.parseDouble((BaseActivity.member_ngcash.replace(",", "")));
        }
*/

        ngcashavb.setFilters(new InputFilter[]{
                new DigitsKeyListener(Boolean.FALSE, Boolean.TRUE) {
                    final int beforeDecimal = 8;
                    final int afterDecimal = 2;

                    @Override
                    public CharSequence filter(CharSequence source, int start, int end,
                                               Spanned dest, int dstart, int dend) {
                        String temp = ngcashavb.getText() + source.toString();

                        if (temp.equals(".")) {
                            return "0.";
                        } else if (temp.indexOf(".") == -1) {
                            // no decimal point placed yet
                            if (temp.length() > beforeDecimal) {
                                return "";
                            }
                        } else {
                            temp = temp.substring(temp.indexOf(".") + 1);
                            if (temp.length() > afterDecimal) {
                                return "";
                            }
                        }
                        return super.filter(source, start, end, dest, dstart, dend);
                    }
                }
        });



        ngcashavb.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                applytv.setText("" + getResources().getString(R.string.apply));
                if (applytv.getText().toString().equalsIgnoreCase(getResources().getString(R.string.applied))) {
                    double amt = total_amt_calculate - apply_ng_cash;
                }
            }
        });

        applytv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply_ngcassh = ngcashavb.getText().toString();
                if (apply_ngcassh == null || apply_ngcassh.equalsIgnoreCase("") || apply_ngcassh.equalsIgnoreCase("0")) {
                    Toast.makeText(WithdrawActivity.this, getResources().getString(R.string.enteramount), Toast.LENGTH_LONG).show();
                    apply_ng_cash = 0;
                    applytv.setText("" + getResources().getString(R.string.apply));
                } else {
                    apply_ng_cash = 0;
                    double apply_ng = Double.parseDouble(apply_ngcassh);
                    if (apply_ng > ngcash_val) {
                        Toast.makeText(WithdrawActivity.this, getResources().getString(R.string.appliedamtisgreaterthanngcash), Toast.LENGTH_LONG).show();
                        applytv.setText("" + getResources().getString(R.string.apply));
                    } else {

                                applytv.setText("" + getResources().getString(R.string.applied));
                    }
                    }
            }
        });




        btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (ngcashavb.getText().toString() == null || ngcashavb.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(WithdrawActivity.this, getResources().getString(R.string.enteramount), Toast.LENGTH_LONG).show();
                }

               else if (Integer.parseInt(ngcashavb.getText().toString())<10) {
                    Toast.makeText(WithdrawActivity.this, getResources().getString(R.string.enter_amount_must_be_gretor_or), Toast.LENGTH_LONG).show();
                }

                 else {
                    new  WithDrawAmount().execute();
                }
            }
        });


    }



    private class GetProfile extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progresbar.setVisibility(View.VISIBLE);

            try {
                super.onPreExecute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                String postReceiverUrl = BaseUrl.baseurl + "member_profile.php?";
                URL url = new URL(postReceiverUrl);
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("member_id", user_id);
                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }

                String urlParameters = postData.toString();
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(urlParameters);
                writer.flush();
                String response = "";
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                writer.close();
                reader.close();
                Log.e("GetProfile Response", ">>>>>>>>>>>>" + response);

                return response;
            } catch (UnsupportedEncodingException e1) {

                e1.printStackTrace();

            } catch (IOException e1) {

                e1.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            progresbar.setVisibility(View.GONE);

            if (result == null) {

            } else if (result.isEmpty()) {

            } else {

                try {

                    JSONObject jsonObject = new JSONObject(result);

                    Log.e("jsonObjectresult", String.valueOf(jsonObject));

                    String message = jsonObject.getString("status");

                    if (message.equalsIgnoreCase("1")) {

                        jsonObject1 = jsonObject.getJSONObject("result");
                        member_ngcash = jsonObject1.getString("member_ngcash");


                        if(jsonObject1.getString("member_stripe_account_id")==null || jsonObject1.getString("member_stripe_account_id").equalsIgnoreCase("")
                                && jsonObject1.getString("stripe_account_login_link")==null || jsonObject1.getString("stripe_account_login_link").equalsIgnoreCase("")  ) {
                            tvAccountType.setText(getString(R.string.add_stripe_account));
                            if (jsonObject1.getString("stripe_account_login_link").equalsIgnoreCase("")) new CheckStripeAccount().execute();
                        }
                        else {
                            tvAccountType.setText(getString(R.string.see_your_stripe_dashboard));
                            mySession.setValueOf(MySession.AuthUrl, jsonObject1.getString("stripe_account_login_link"));
                            mySession.setValueOf(MySession.Key_TYPE, "Withdraw");

                        }

                        if (member_ngcash == null || member_ngcash.equalsIgnoreCase("0") || member_ngcash.equalsIgnoreCase("") || member_ngcash.equalsIgnoreCase("0.0") || member_ngcash.equalsIgnoreCase("null")) {
                            avbngcash.setText(mySession.getValueOf(MySession.CurrencySign) + "0.00");
                        } else {
                            avbngcash.setText(mySession.getValueOf(MySession.CurrencySign) + member_ngcash);
                            ngcash_val = Double.parseDouble((member_ngcash.replace(",", "")));
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    private class WithDrawAmount extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progresbar.setVisibility(View.VISIBLE);

            try {
                super.onPreExecute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                String postReceiverUrl = BaseUrl.baseurl + "withdrawing_ng_cash.php?";
                URL url = new URL(postReceiverUrl);
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("user_id", user_id);
                params.put("amount", ngcashavb.getText().toString());
                params.put("currency", mySession.getValueOf(MySession.CurrencyCode));
                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }

                String urlParameters = postData.toString();
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(urlParameters);
                writer.flush();
                String response = "";
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                writer.close();
                reader.close();
                Log.e("GetProfile Response", ">>>>>>>>>>>>" + response);

                return response;
            } catch (UnsupportedEncodingException e1) {

                e1.printStackTrace();

            } catch (IOException e1) {

                e1.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            progresbar.setVisibility(View.GONE);

            if (result == null) {

            } else if (result.isEmpty()) {

            } else {

                try {

                    JSONObject jsonObject = new JSONObject(result);

                    Log.e("jsonObjectresult", String.valueOf(jsonObject));

                    String message = jsonObject.getString("status");

                    if (message.equalsIgnoreCase("1")) {
                        Toast.makeText(WithdrawActivity.this, "Withdraw successfully...", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private class GenerateStripeAccount extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progresbar.setVisibility(View.VISIBLE);

            try {
                super.onPreExecute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                String postReceiverUrl = "https://myngrewards.com/wp-content/plugins/webservice/create_stripe_account.php?";
                URL url = new URL(postReceiverUrl);
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("user_id", user_id);
                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }

                String urlParameters = postData.toString();
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(urlParameters);
                writer.flush();
                String response = "";
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                writer.close();
                reader.close();
                Log.e("GetProfile Response", ">>>>>>>>>>>>" + response);

                return response;
            } catch (UnsupportedEncodingException e1) {

                e1.printStackTrace();

            } catch (IOException e1) {

                e1.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            progresbar.setVisibility(View.GONE);

            if (result == null) {

            } else if (result.isEmpty()) {

            } else {

                try {

                    JSONObject jsonObject = new JSONObject(result);

                    Log.e("jsonObjectresult", String.valueOf(jsonObject));

                    String status = jsonObject.getString("status");

                    if (status.equalsIgnoreCase("5")) {
                        UploadDialog(jsonObject.getString("result"));
                    }
                    else if (status.equalsIgnoreCase("1")){
                        new GetProfile().execute();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void UploadDialog(String url){
        Dialog dialog = new Dialog(WithdrawActivity.this,android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_webview);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
       WebView stripewebview = dialog.findViewById(R.id.stripewebview1);
       ProgressBar progressabar = dialog.findViewById(R.id.progressabar);
        RelativeLayout backlay = dialog.findViewById(R.id.backlay);

        backlay.setOnClickListener(v -> dialog.dismiss());

        stripewebview.getSettings().setLoadsImagesAutomatically(true);
        stripewebview.getSettings().setJavaScriptEnabled(true);
        stripewebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        stripewebview.setWebViewClient(new HelloWebViewClient());
        stripewebview.getSettings().setDomStorageEnabled(true);
        //  stripewebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        stripewebview.getSettings().setBlockNetworkLoads (false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            stripewebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            stripewebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        }
        else {
            stripewebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);

        }

        stripewebview.loadUrl(url);

        stripewebview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
                if (!loadingFinished) {
                    redirect = true;
                }
                progressabar.setVisibility(View.VISIBLE);
                loadingFinished = false;
                view.loadUrl(urlNewString);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap facIcon) {
                loadingFinished = false;
                progressabar.setVisibility(View.VISIBLE);
//SHOW LOADING IF IT ISNT ALREADY VISIBLE
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressabar.setVisibility(View.GONE);
                Log.e("url=====",url);
                if(url.contains("https://myngrewards.com/return/")){
                    dialog.dismiss();
                   new CheckStripeAccount().execute();
                }
            }
        });

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.show();
    }


    private class CheckStripeAccount extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progresbar.setVisibility(View.VISIBLE);

            try {
                super.onPreExecute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                String postReceiverUrl = "https://myngrewards.com/wp-content/plugins/webservice/create_login_link.php?";
                URL url = new URL(postReceiverUrl);
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("user_id", user_id);
                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }

                String urlParameters = postData.toString();
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(urlParameters);
                writer.flush();
                String response = "";
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                writer.close();
                reader.close();
                Log.e("Check Stripe Response", ">>>>>>>>>>>>" + response);

                return response;
            } catch (UnsupportedEncodingException e1) {

                e1.printStackTrace();

            } catch (IOException e1) {

                e1.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            progresbar.setVisibility(View.GONE);

            if (result == null) {

            } else if (result.isEmpty()) {

            } else {

                try {

                    JSONObject jsonObject = new JSONObject(result);

                    Log.e("jsonObjectresult", String.valueOf(jsonObject));

                    String status = jsonObject.getString("status");

                    if (status.equalsIgnoreCase("1")) {
                        new GetProfile().execute();
                    }
                    else if(status.equalsIgnoreCase("3")){
                        UploadDialog(jsonObject.getJSONObject("result").getString("url"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }





    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }



}
