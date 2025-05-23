package main.com.ngrewards.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import main.com.ngrewards.Adapter.CardAdapter;
import main.com.ngrewards.Models.MembershipModel;
import main.com.ngrewards.R;
import main.com.ngrewards.Utils.LocaleHelper;
import main.com.ngrewards.Utils.Tools;
import main.com.ngrewards.beanclasses.CardBean;
import main.com.ngrewards.constant.BaseUrl;
import main.com.ngrewards.constant.MySavedCardInfo;
import main.com.ngrewards.constant.MySession;
import main.com.ngrewards.restapi.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectPayMethodAct extends AppCompatActivity implements CardClickListener{

    public static String card_id = "", card_number = "", card_brand = "", customer_id = "",planId="";
    private RecyclerView addedcardlist;
    private ArrayList<CardBean> cardBeanArrayList;
    private MySavedCardInfo mySavedCardInfo;
    private ProgressBar progresbar;
    private MySession mySession;
    private String user_id = "";
    private CardAdapter customCardAdp;
    private RelativeLayout addaddressdlay, backlay;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.reupdateResources(this);
        setContentView(R.layout.activity_select_pay_method);
        mySession = new MySession(this);
        mySavedCardInfo = new MySavedCardInfo(this);
        String user_log_data = mySession.getKeyAlldata();
        if(getIntent()!=null){
           planId =  getIntent().getStringExtra("planId");
        }
        if (user_log_data == null) {

        } else {

            try {

                JSONObject jsonObject = new JSONObject(user_log_data);
                String message = jsonObject.getString("status");
                if (message.equalsIgnoreCase("1")) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                    user_id = jsonObject1.getString("id");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        idinit();
        clickevent();
    }

    private void clickevent() {
        addaddressdlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectPayMethodAct.this, AddMemberCard.class);
                startActivity(i);
            }
        });

        backlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void idinit() {
        progresbar = findViewById(R.id.progresbar);
        backlay = findViewById(R.id.backlay);
        addaddressdlay = findViewById(R.id.addaddressdlay);
        addedcardlist = findViewById(R.id.addedcardlist);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mySavedCardInfo.getKeyCarddata() != null && !mySavedCardInfo.getKeyCarddata().isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(mySavedCardInfo.getKeyCarddata());
                cardBeanArrayList = new ArrayList<>();
                if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("sources");
                    String customer_id = jsonObject1.getString("id");
                    Log.e("customer_id >> ", " >> " + customer_id);


                    JSONArray jsonArray = jsonObject2.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                        CardBean cardBean = new CardBean();
                        cardBean.setId(jsonObject3.getString("id"));
                        cardBean.setLast4(jsonObject3.getString("last4"));
                        cardBean.setExp_month(jsonObject3.getString("exp_month"));
                        cardBean.setExp_year(jsonObject3.getString("exp_year"));
                        cardBean.setBrand(jsonObject3.getString("brand"));
                        cardBean.setFunding(jsonObject3.getString("funding"));
                        cardBean.setCustomer(jsonObject3.getString("customer"));
                        cardBean.setCard_name(jsonObject3.getString("name"));
                        String star = "************";
                        String cardlastfour = jsonObject3.getString("last4");

                        cardBean.setSetfullcardnumber(star + cardlastfour);
                        cardBean.setSetfullexpyearmonth(jsonObject3.getString("exp_month") + "/" + jsonObject3.getString("exp_year"));

                        cardBeanArrayList.add(cardBean);

                    }
                    customCardAdp = new CardAdapter(SelectPayMethodAct.this, cardBeanArrayList,SelectPayMethodAct.this);
                    addedcardlist.setAdapter(customCardAdp);
                    customCardAdp.notifyDataSetChanged();

                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            new GetAddedCard().execute();
        }

    }

    @Override
    public void onCardClick(String cardId, String cusId) {
        planPurchase(cardId,cusId);
    }


    private class GetAddedCard extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progresbar.setVisibility(View.VISIBLE);
            cardBeanArrayList = new ArrayList<>();
            try {
                super.onPreExecute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                String postReceiverUrl = BaseUrl.baseurl + "get_customer_stripe_card_list.php?";
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
                Log.e("Json Login Response", ">>>>>>>>>>>>" + response);
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
                    if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                        JSONObject jsonObject2 = jsonObject1.getJSONObject("sources");
                        String customer_id = jsonObject1.getString("id");
                        Log.e("customer_id >> ", " >> " + customer_id);
                        mySavedCardInfo.setKeyCarddata(result);

                        JSONArray jsonArray = jsonObject2.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                            CardBean cardBean = new CardBean();
                            cardBean.setId(jsonObject3.getString("id"));
                            cardBean.setLast4(jsonObject3.getString("last4"));
                            cardBean.setExp_month(jsonObject3.getString("exp_month"));
                            cardBean.setExp_year(jsonObject3.getString("exp_year"));
                            cardBean.setBrand(jsonObject3.getString("brand"));
                            cardBean.setFunding(jsonObject3.getString("funding"));
                            cardBean.setCustomer(jsonObject3.getString("customer"));
                            cardBean.setCard_name(jsonObject3.getString("name"));
                            String star = "************";
                            String cardlastfour = jsonObject3.getString("last4");
                            cardBean.setSetfullcardnumber(star + cardlastfour);
                            cardBean.setSetfullexpyearmonth(jsonObject3.getString("exp_month") + "/" + jsonObject3.getString("exp_year"));

                            cardBeanArrayList.add(cardBean);

                        }

                        customCardAdp = new CardAdapter(SelectPayMethodAct.this, cardBeanArrayList,SelectPayMethodAct.this);
                        addedcardlist.setAdapter(customCardAdp);
                        customCardAdp.notifyDataSetChanged();

                        // new TransferAmount().execute(customer_id);


                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }


    private void planPurchase(String cardId,String cusId) {
         progresbar.setVisibility(View.VISIBLE);
         Call<ResponseBody> call = ApiClient.getApiInterface().purchaseMemberShipPlanApi(user_id,cardId,cusId,planId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progresbar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("purchase membership plan", " >" + responseData);
                        if (object.getString("status").equals("1")) {
                            //MembershipModel successData = new Gson().fromJson(responseData, MembershipModel.class);
                            if ( object.getJSONObject("membership_data") != null) {
                               // successData.getMembershipData().getEndDate();
                                //Toast.makeText(SelectPayMethodAct.this, object.getJSONObject("membership_data").getString("end_date"), Toast.LENGTH_SHORT).show();
                                Toast.makeText(SelectPayMethodAct.this, object.getString("message"), Toast.LENGTH_SHORT).show();


                                finish();
                            }
                        }
                       else {
                            Toast.makeText(SelectPayMethodAct.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                        }




                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                t.printStackTrace();
                progresbar.setVisibility(View.GONE);

                Log.e("TAG", t.toString());
            }
        });
    }




}
