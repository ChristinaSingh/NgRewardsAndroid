package main.com.ngrewards.marchant.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import cz.msebera.android.httpclient.extras.Base64;
import de.hdodenhof.circleimageview.CircleImageView;
import main.com.ngrewards.Interfaces.FilterSheetListener;
import main.com.ngrewards.Models.NotificationModel;
import main.com.ngrewards.R;
import main.com.ngrewards.Utils.LocaleHelper;
import main.com.ngrewards.Utils.Tools;
import main.com.ngrewards.activity.EMIManualActivity;
import main.com.ngrewards.activity.PreferenceConnector;
import main.com.ngrewards.constant.BaseUrl;
import main.com.ngrewards.constant.MySession;
import main.com.ngrewards.fragments.FilterBottomSheet;
import main.com.ngrewards.placeorderclasses.MerchantReceiptActivity;
import main.com.ngrewards.placeorderclasses.ReceiptActivity;
import main.com.ngrewards.restapi.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchantAdminNotificationAct extends AppCompatActivity implements FilterSheetListener {
    private RecyclerView notificationlist;
    private RelativeLayout backlay;
    private NotificationAdpter notificationAdpter;
    private MySession mySession;
    private String user_id = "", time_zone = "";
    private SwipeRefreshLayout swipeToRefresh;
    private ArrayList<NotificationModel.Result> notificationModels;
    private TextView nonotiavb,btnDetail,tvNotification;
    private String type,filterType ="today";

    public static String fromBase64(String message) {
        byte[] data = Base64.decode(message, Base64.DEFAULT);
        return new String(data, StandardCharsets.UTF_8);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.reupdateResources(this);
        setContentView(R.layout.activity_notification);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                type = null;
            } else {
                type = extras.getString("type");
            }
        } else {
            type = (String) savedInstanceState.getSerializable("type");
        }

        if(type.equalsIgnoreCase("pay_bill_merchant")){
            type = "merchant";
        }
        else {
            type = "member";
        }

        Log.e("check type===",type);


        //   Log.e("sagar>>>>>", type);
        //  Toast.makeText(this, type, Toast.LENGTH_SHORT).show();

        mySession = new MySession(this);
        String user_log_data = mySession.getKeyAlldata();

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
        Calendar c = Calendar.getInstance();
        TimeZone tz = c.getTimeZone();
        time_zone = tz.getID();
        idinit();
        clickevent();
        UpdateStatus();
    }

    private void UpdateStatus() {
        new UpdateStatus().execute();
    }

    private void clickevent() {

        backlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDetail.setOnClickListener(v -> {
            GreetingDialog();

        });

        tvNotification.setOnClickListener(v -> {
            new FilterBottomSheet(type).callBack(this::onFilter).show(getSupportFragmentManager(),"");
        });




    }

    private void idinit() {
        nonotiavb = findViewById(R.id.nonotiavb);
        swipeToRefresh = findViewById(R.id.swipeToRefresh);
        backlay = findViewById(R.id.backlay);
        btnDetail = findViewById(R.id.btnDetail);
        notificationlist = findViewById(R.id.notificationlist);
        tvNotification = findViewById(R.id.tvNotification);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(MerchantAdminNotificationAct.this, LinearLayoutManager.VERTICAL, false);
        notificationlist.setLayoutManager(horizontalLayoutManagaer);

        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyNotification(filterType);
                //  new MyNotification().execute();
                // swipeToRefresh.setRefreshing(false);
            }
        });

        //getMyNotification();
        getMyNotification(filterType);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //  new MyNotification().execute();
    }

    private void getMyNotification(String filterType) {
        swipeToRefresh.setRefreshing(true);
        notificationModels = new ArrayList<>();
        Call<NotificationModel> call =
                ApiClient.getApiInterface().admin_notification_list_new(user_id, type,filterType);
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                //progresbar.setVisibility(View.GONE);
                swipeToRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    try {
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            Log.e("TAG",
                                    "onResponse: response.body().getStatus()----" + response.body().getStatus());
                            notificationModels = (ArrayList<NotificationModel.Result>) response.body().getResult();
                            notificationAdpter = new NotificationAdpter(MerchantAdminNotificationAct.this, notificationModels);
                            notificationlist.setAdapter(notificationAdpter);
                            notificationAdpter.notifyDataSetChanged();
                            tvNotification.setVisibility(View.VISIBLE);
                        } else {
                            nonotiavb.setVisibility(View.VISIBLE);
                            tvNotification.setVisibility(View.GONE);

                        }
                    } catch (Exception e) {
                        nonotiavb.setVisibility(View.VISIBLE);
                        tvNotification.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                } else {
                    nonotiavb.setVisibility(View.VISIBLE);
                    tvNotification.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                t.printStackTrace();
                swipeToRefresh.setRefreshing(false);
                Log.e("TAG", t.toString());
                Log.e("TAG", "onFailure: " + t.getMessage());
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onFilter(String type, String filter) {
        filterType = filter;
        getMyNotification(filterType);
    }

    private class UpdateStatus extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);

            try {
                super.onPreExecute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String postReceiverUrl = BaseUrl.baseurl + "update_chat_status.php?";
                URL url = new URL(postReceiverUrl);
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("reciever_id", user_id);
                params.put("type", type);

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
                Log.e("Json Add Response", ">>>>>>>>>>>>" + response);
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
            if (result == null) {
            } else if (result.isEmpty()) {

            } else {
                try {
                    JSONObject jsonObject = null;
                    jsonObject = new JSONObject(result);
                    if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                        //  Toast.makeText(MerchantNotificationActivity.this, getResources().getString(R.string.status), Toast.LENGTH_LONG).show();

                    } else {
                        //    Toast.makeText(MerchantNotificationActivity.this, getResources().getString(R.string.somethingwrong), Toast.LENGTH_LONG).show();

                    }
                } catch (Exception e) {

                }
            }
        }
    }

/*    private class MyNotification extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            swipeToRefresh.setRefreshing(true);
            notificationBeanNewArrayList = new ArrayList<>();
            super.onPreExecute();
            try {
                super.onPreExecute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
//                String postReceiverUrl = BaseUrl.baseurl + "admin_notification_list.php?";
                String postReceiverUrl = BaseUrl.baseurl + "admin_notification_list_new.php?";
                URL url = new URL(postReceiverUrl);
                Map<String, Object> params = new LinkedHashMap<>();
                Log.e("postReceiverUrl>>", " .." + postReceiverUrl + "user_id=" + user_id + "&type="+type);
                params.put("user_id", user_id);
                params.put("type", type);
                //params.put("timezone", time_zone);

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
            Log.e("Get Notification >>", "" + result);
            swipeToRefresh.setRefreshing(false);
            if (result == null) {

            } else if (result.isEmpty()) {

            } else {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int jsonlenth = 0;
                    if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        jsonlenth = jsonArray.length();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            NotificationBeanNew notificationBeanNew = new NotificationBeanNew();
                            notificationBeanNew.setChatMesssage(jsonObject2.getString("chat_message"));
                            notificationBeanNew.setCreated_date(jsonObject2.getString("date_time"));
                            if (jsonObject2.has("payload")){
                                if (jsonObject2.getString("chat_message").equalsIgnoreCase("You " +
                                        "have emi pending please pay now")){
                                Log.e("TAG", "onPostExecute:datadatadatadata " );
                                 if (!jsonObject2.getJSONObject("payload").equals(null)){
                                JSONObject data = jsonObject2.getJSONObject("payload");
                                Log.e("TAG", "onPostExecute:datadatadatadata "+data.toString() );
                                if (data.isNull("")) {}else {notificationBeanNew.setData(data.toString());}}
                                 }
                            }

                            Log.e("chat_message>>>>", jsonObject2.getString("chat_message"));
                            Log.e("date_time>>>>", jsonObject2.getString("date_time"));

                            notificationBeanNewArrayList.add(notificationBeanNew);

                        }


                    } else {
                        nonotiavb.setVisibility(View.VISIBLE);
                    }

                    if (notificationBeanNewArrayList == null || notificationBeanNewArrayList.isEmpty() || notificationBeanNewArrayList.size() == 0) {
                        nonotiavb.setVisibility(View.VISIBLE);
                    } else {
                        notificationAdpter = new NotificationAdpter(MerchantNotificationActivity.this, notificationBeanNewArrayList);
                        notificationlist.setAdapter(notificationAdpter);
                        notificationAdpter.notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }*/

    public class NotificationAdpter extends RecyclerView.Adapter<NotificationAdpter.MyViewHolder> {
        Context context;
        ArrayList<NotificationModel.Result> notificationBeanNewArrayList;

        public NotificationAdpter(Activity myContacts,
                                  ArrayList<NotificationModel.Result> notificationBeanNewArrayList) {
            this.context = myContacts;
            this.notificationBeanNewArrayList = notificationBeanNewArrayList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_notilay, parent, false);
            MyViewHolder holder = new NotificationAdpter.MyViewHolder(itemView);
            return holder;
            // return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            NotificationModel.Result result = notificationBeanNewArrayList.get(position);
            NotificationModel.Result.Payload paylode = notificationBeanNewArrayList.get(position).getPayload();
            if (paylode.getDueDate() != null) {
                try {
                    int number_of_emi = Integer.parseInt(paylode.getNumberOfEmi());
                    String str = "th";
                    if (number_of_emi == 1) str = "st";
                    if (number_of_emi == 2) str = "nd";
                    if (number_of_emi == 3) str = "rd";
                    holder.message_tv.setText("Reminder for " + number_of_emi + str +
                            " Payment " + paylode.getSplitAmountX() + "  Due on " + paylode.getDueDate());

                } catch (Exception e) {
                    holder.message_tv.setText("Reminder for EMI" + " Payment " + paylode.getSplitAmountX() + "Due on " + paylode.getDueDate());

                }

            } else {
                holder.message_tv.setText("" + result.getChatMessage());
            }
            holder.time_tv.setText("" + result.getDateTime());


            holder.itemView.setOnClickListener(v -> {
                Log.e("TAG",
                        "onBindViewHolder: paylode.toString()---" + paylode);
                if (result.getType().equalsIgnoreCase("merchant")) {
                    //dfghjbvdf = notificationBeanNewArrayList.get(position).getOrder_cart_id();
                    Intent i = new Intent(MerchantAdminNotificationAct.this, MerchantReceiptActivity.class);
                    i.putExtra("member_user_name", notificationBeanNewArrayList.get(position).getPayBillData().get(0).getMemberDetail().get(0).getAffiliateName());
                    i.putExtra("member_id", notificationBeanNewArrayList.get(position).getPayBillData().get(0).getMemberDetail().get(0).getId());
                    i.putExtra("member_fullname_number", notificationBeanNewArrayList.get(position).getPayBillData().get(0).getMemberDetail().get(0).getFullname());
                    i.putExtra("member_img_str", notificationBeanNewArrayList.get(position).getPayBillData().get(0).getMemberDetail().get(0).getMemberImage());
                    i.putExtra("order_id", "" + notificationBeanNewArrayList.get(position).getId());
                    i.putExtra("cardnumber_tv", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getCardNumber());
                    i.putExtra("cardbrand", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getCardBrand());
                    i.putExtra("total_amt_tv_str", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getTotalAmount());
                    i.putExtra("due_amt_tv_str", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getAmount());
                    i.putExtra("ngcash_str", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getNgcash());
                    i.putExtra("tip_str", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getTipAmount());
                    i.putExtra("order_special", "" + "");
                    i.putExtra("employee_name", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getEmployeeName());
                    i.putExtra("reciept_url", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getRecieptUrl());
                    i.putExtra("order_date", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getPayBillDate());
                    i.putExtra("order_cart_id", "" + "");
                    startActivity(i);
                } else if (result.getType().equalsIgnoreCase("member")) {


                    if(notificationBeanNewArrayList.get(position).getTransferRequestData()!=null) {
                        if (notificationBeanNewArrayList.get(position).getTransferRequestData().get(0).getType().equalsIgnoreCase("Transfer")) {

                            Intent i = new Intent(MerchantAdminNotificationAct.this, ReceiptActivity.class);

                            Log.e("getBusinessName", notificationBeanNewArrayList.get(position).getTransferRequestData().get(0).getMemberDetail().get(0).getAffiliateName());
                            i.putExtra("merchant_name", notificationBeanNewArrayList.get(position).getTransferRequestData().get(0).getMemberDetail().get(0).getUsername());
                            i.putExtra("member_name", notificationBeanNewArrayList.get(position).getTransferRequestData().get(0).getMemberDetail().get(0).getFullname());
                            i.putExtra("merchant_id", notificationBeanNewArrayList.get(position).getTransferRequestData().get(0).getMemberDetail().get(0).getId());
                            i.putExtra("merchant_number", notificationBeanNewArrayList.get(position).getTransferRequestData().get(0).getMemberDetail().get(0).getPhone());
                            i.putExtra("merchant_contact_name", "");
                            i.putExtra("address", "");
                            i.putExtra("address_2", "");
                            i.putExtra("merchant_img_str", notificationBeanNewArrayList.get(position).getTransferRequestData().get(0).getMemberDetail().get(0).getMemberImage());
                            i.putExtra("date_tv", notificationBeanNewArrayList.get(position).getTransferRequestData().get(0).getCreatedDate());
                            i.putExtra("order_id", "" + notificationBeanNewArrayList.get(position).getId());
                            i.putExtra("cardnumber_tv", "" + notificationBeanNewArrayList.get(position).getTransferRequestData().get(0).getCardNumber());
                            i.putExtra("cardbrand", "" + notificationBeanNewArrayList.get(position).getTransferRequestData().get(0).getCardBrand());
                            i.putExtra("total_amt_tv_str", "" + notificationBeanNewArrayList.get(position).getTransferRequestData().get(0).getTotalAmount());
                            i.putExtra("due_amt_tv_str", "" + notificationBeanNewArrayList.get(position).getTransferRequestData().get(0).getAmount());
                            i.putExtra("ngcash_str", "" + notificationBeanNewArrayList.get(position).getTransferRequestData().get(0).getNgcash());
                            i.putExtra("tip_str", "" + "0.00");
                            i.putExtra("employee_name", "");
                            i.putExtra("mdate", "" + notificationBeanNewArrayList.get(position).getTransferRequestData().get(0).getCreatedDate());
                            i.putExtra("time", "" + notificationBeanNewArrayList.get(position).getTransferRequestData().get(0).getCreatedDate());
                            i.putExtra("Order_guset_No", "" + "");
                            i.putExtra("Order_Table_No", "" + "");
                            i.putExtra("reciept_url", "" + notificationBeanNewArrayList.get(position).getTransferRequestData().get(0).getRecieptUrl());
                            i.putExtra("order_special", "");
                            i.putExtra("order_cart_id", "");
                            i.putExtra("type123", notificationBeanNewArrayList.get(position).getTransferRequestData().get(0).getType());
                            startActivity(i);


                           /* Intent i = new Intent(MerchantNotificationActivity.this, MerchantReceiptActivity.class);
                            i.putExtra("member_user_name", notificationBeanNewArrayList.get(position).getPayBillData().get(0).getMemberDetail().get(0).getAffiliateName());
                            i.putExtra("member_id", notificationBeanNewArrayList.get(position).getPayBillData().get(0).getMemberDetail().get(0).getId());
                            i.putExtra("member_fullname_number", notificationBeanNewArrayList.get(position).getPayBillData().get(0).getMemberDetail().get(0).getFullname());
                            i.putExtra("member_img_str", notificationBeanNewArrayList.get(position).getPayBillData().get(0).getMemberDetail().get(0).getMemberImage());
                            i.putExtra("order_id", "" + notificationBeanNewArrayList.get(position).getId());
                            i.putExtra("cardnumber_tv", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getCardNumber());
                            i.putExtra("cardbrand", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getCardBrand());
                            i.putExtra("total_amt_tv_str", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getTotalAmount());
                            i.putExtra("due_amt_tv_str", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getAmount());
                            i.putExtra("ngcash_str", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getNgcash());
                            i.putExtra("tip_str", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getTipAmount());
                            i.putExtra("order_special", "" + "");
                            i.putExtra("employee_name", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getEmployeeName());
                            i.putExtra("reciept_url", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getRecieptUrl());
                            i.putExtra("order_date", "" + notificationBeanNewArrayList.get(position).getPayBillData().get(0).getPayBillDate());
                            i.putExtra("order_cart_id", "" + "");
                            startActivity(i);*/


                        }

                    }


                } else {
                    if (paylode.getDueDate() != null) {

                        Intent intentw = new Intent(getApplicationContext(), EMIManualActivity.class);
                        intentw.putExtra("object", paylode.toString());
                        context.startActivity(intentw);
                    }
                }


            });



        }

        @Override
        public int getItemCount() {
            // return 6;
            return notificationBeanNewArrayList == null ? 0 : notificationBeanNewArrayList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public RelativeLayout backlay;
            CircleImageView user_img;
            TextView user_name_tv, message_tv, time_tv, reqcount;

            public MyViewHolder(View view) {
                super(view);
                reqcount = itemView.findViewById(R.id.reqcount);
                user_img = itemView.findViewById(R.id.user_img);
                user_name_tv = itemView.findViewById(R.id.user_name_tv);
                message_tv = itemView.findViewById(R.id.message_tv);
                time_tv = itemView.findViewById(R.id.time_tv);
            }
        }
    }



    public void GreetingDialog(){
        Dialog dialog = new Dialog(MerchantAdminNotificationAct.this,android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_greeting);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);


        TextView btnStart = dialog.findViewById(R.id.btnStarted);

        btnStart.setOnClickListener(v -> {
            PreferenceConnector.writeString(MerchantAdminNotificationAct.this, PreferenceConnector.Greeting_Status, "true");

            dialog.dismiss();
        });

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.show();
    }



}

