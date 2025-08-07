package main.com.ngrewards.marchant.merchantbottum;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import main.com.ngrewards.Models.PropertyListModel;
import main.com.ngrewards.R;
import main.com.ngrewards.Utils.LocaleHelper;
import main.com.ngrewards.Utils.Tools;
import main.com.ngrewards.beanclasses.MerchantItem;
import main.com.ngrewards.beanclasses.MerchantItemList;
import main.com.ngrewards.constant.MySession;
import main.com.ngrewards.marchant.activity.ActiveProductsAct;
import main.com.ngrewards.marchant.activity.SoldProductsAct;
import main.com.ngrewards.marchant.activity.StartYourListing;
import main.com.ngrewards.marchant.activity.UnsoldProductsAct;
import main.com.ngrewards.marchant.draweractivity.MerchantBaseActivity;
import main.com.ngrewards.marchant.rent.AddPropertyAct;
import main.com.ngrewards.marchant.rent.MerchantPropertyAct;
import main.com.ngrewards.marchant.rent.PropertyEnquiryListAct;
import main.com.ngrewards.marchant.rent.PropertyEnquiryModel;
import main.com.ngrewards.restapi.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchantBotSell extends MerchantBaseActivity {
    public static ArrayList<MerchantItemList> myproductslist;
    FrameLayout contentFrameLayout;
    SwipeRefreshLayout swipeToRefresh;
    private RecyclerView activity_list;
    private LinearLayout lay_out, lay_removed;
    private TextView listitem,tvAuth;
    private MySession mySession;
    private String user_id = "";
    private TextView cont_find, active_count_tv, sold_count_tv, unsold_count_tv, total_earning,tvActiveCount,tvEnquiryCount;
    private LinearLayout active_lay, unsold_lay, sold_lay,llEnquiry,llActive,llRealState,llItem;
    String user_log_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        contentFrameLayout = (FrameLayout) findViewById(R.id.contentFrame);
        getLayoutInflater().inflate(R.layout.activity_merchant_bot_sell, contentFrameLayout);
        mySession = new MySession(this);
        Log.e("TAG", "onCreate: " + mySession.getadmin_created_password());
        idinits();
        clickevent();
        user_log_data = mySession.getKeyAlldata();
        if (user_log_data == null) {

        }
        else {
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

            if (mySession.getsell_items_reomve_access().equalsIgnoreCase("remove")) {
                lay_out.setVisibility(View.GONE);
                lay_removed.setVisibility(View.VISIBLE);
            } else {
                if (mySession.getPassSet().equalsIgnoreCase("")) {
                    lay_out.setVisibility(View.GONE);
                    setSellPassDialog();
                } else {
                    lay_out.setVisibility(View.VISIBLE);
                    lay_removed.setVisibility(View.GONE);
                    if(mySession.get_business_category().equalsIgnoreCase("999")){
                        listitem.setText(getString(R.string.add_property));
                        tvAuth.setVisibility(View.VISIBLE);
                        llRealState.setVisibility(View.VISIBLE);
                        llItem.setVisibility(View.GONE);
                        getMerchantProperty();
                        gePropertyEnquiry();
                    }
                    else {
                        listitem.setText(getString(R.string.listanitem));
                        tvAuth.setVisibility(View.GONE);
                        llRealState.setVisibility(View.GONE);
                        llItem.setVisibility(View.VISIBLE);
                        getSoldItems();
                    }
                }

            }

        }
    }

    private void setSellPassDialog() {
        try {
            final Dialog dialogSts = new Dialog(MerchantBotSell.this, R.style.DialogSlideAnim);
            dialogSts.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogSts.setCancelable(false);
            dialogSts.setContentView(R.layout.removed_item);
            dialogSts.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Button submitt = (Button) dialogSts.findViewById(R.id.submitt);

            TextView close = (TextView) dialogSts.findViewById(R.id.close);
            close.setOnClickListener(v -> {
                Intent Intent5 = new Intent(this, MerchantBottumAct.class);
                startActivity(Intent5);
                finish();
            });
            TextView cont_find = (TextView) dialogSts.findViewById(R.id.cont_find);
            EditText pass = (EditText) dialogSts.findViewById(R.id.pass);
            CheckBox acceptcondition = (CheckBox) dialogSts.findViewById(R.id.acceptcondition);
            submitt.setOnClickListener(v -> {
                if (!acceptcondition.isChecked()) {
                    new SweetAlertDialog(MerchantBotSell.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getString(R.string.tnc))
                            .hideConfirmButton()
                            .setCancelButton(getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                    return;
                }
                if (pass.getText().toString().equalsIgnoreCase("")) {
                    pass.setError("Empty");
                    return;
                }
                if (!pass.getText().toString().equalsIgnoreCase(mySession.getadmin_created_password())) {
                    new SweetAlertDialog(MerchantBotSell.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getString(R.string.wrong_password))
                            .hideConfirmButton()
                            .setCancelButton(getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                } else {
                    Snackbar.make(v, "Password Matched", Snackbar.LENGTH_SHORT).show();
                    mySession.setPassSet(mySession.getadmin_created_password());
                    dialogSts.dismiss();
                    lay_out.setVisibility(View.VISIBLE);
                    lay_removed.setVisibility(View.GONE);
                    if(mySession.get_business_category().equalsIgnoreCase("999")){
                        listitem.setText(getString(R.string.add_property));
                        tvAuth.setVisibility(View.VISIBLE);
                        llRealState.setVisibility(View.VISIBLE);
                        llItem.setVisibility(View.GONE);
                        getMerchantProperty();
                        gePropertyEnquiry();
                    }
                    else {
                        listitem.setText(getString(R.string.listanitem));
                        tvAuth.setVisibility(View.GONE);
                        llRealState.setVisibility(View.GONE);
                        llItem.setVisibility(View.VISIBLE);
                        getSoldItems();
                    }
                }


            });
            cont_find.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogSts.dismiss();
                }
            });
            dialogSts.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void clickevent() {
        listitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mySession.get_business_category().equalsIgnoreCase("999")){
                startActivity(new Intent(MerchantBotSell.this, AddPropertyAct.class));
                }
                else {
                    Intent i = new Intent(MerchantBotSell.this, StartYourListing.class);
                    startActivity(i);
                }

            }
        });
        active_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MerchantBotSell.this, ActiveProductsAct.class);
                startActivity(i);
            }
        });
        cont_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sold_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MerchantBotSell.this, SoldProductsAct.class);
                startActivity(i);
            }
        });
        unsold_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MerchantBotSell.this, UnsoldProductsAct.class);
                startActivity(i);
            }
        });

        llActive.setOnClickListener(v -> startActivity(new Intent(MerchantBotSell.this, MerchantPropertyAct.class)));

        llEnquiry.setOnClickListener(v ->
                startActivity(new Intent(MerchantBotSell.this, PropertyEnquiryListAct.class)));


    }

    private void idinits() {
        lay_out = findViewById(R.id.lay_out);
        cont_find = findViewById(R.id.cont_find);
        lay_removed = findViewById(R.id.lay_removed);
        total_earning = findViewById(R.id.total_earning);
        sold_lay = findViewById(R.id.sold_lay);
        active_lay = findViewById(R.id.active_lay);
        unsold_lay = findViewById(R.id.unsold_lay);
        active_count_tv = findViewById(R.id.active_count_tv);
        sold_count_tv = findViewById(R.id.sold_count_tv);
        unsold_count_tv = findViewById(R.id.unsold_count_tv);
        swipeToRefresh = findViewById(R.id.swipeToRefresh);
        listitem = findViewById(R.id.listitem);
        tvAuth = findViewById(R.id.tvAuth);

        llEnquiry = findViewById(R.id.llEnquiry);
        llActive = findViewById(R.id.llActive);
        llRealState = findViewById(R.id.llRealState);
        llItem = findViewById(R.id.llItem);
        tvActiveCount = findViewById(R.id.tvActiveCount);
        tvEnquiryCount = findViewById(R.id.tvEnquiryCount);


        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSoldItems();

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Tools.reupdateResources(this);


        if(mySession.get_business_category().equalsIgnoreCase("999")){
            listitem.setText(getString(R.string.add_property));
            tvAuth.setVisibility(View.VISIBLE);
            llRealState.setVisibility(View.VISIBLE);
            llItem.setVisibility(View.GONE);
            getMerchantProperty();
            gePropertyEnquiry();
        }
        else {
            listitem.setText(getString(R.string.listanitem));
            tvAuth.setVisibility(View.GONE);
            llRealState.setVisibility(View.GONE);
            llItem.setVisibility(View.VISIBLE);
            getSoldItems();
        }



    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    private void getSoldItems() {
        swipeToRefresh.setRefreshing(true);
        //progresbar.setVisibility(View.VISIBLE);
        myproductslist = new ArrayList<>();
        Call<ResponseBody> call = ApiClient.getApiInterface().getMerchantOwnProduct(user_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // progresbar.setVisibility(View.GONE);
                swipeToRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        Log.e("Sold Products >", " >" + responseData);
                        JSONObject object = new JSONObject(responseData);

                        if (object.getString("status").equals("1")) {
                            MerchantItem successData = new Gson().fromJson(responseData, MerchantItem.class);
                            myproductslist.addAll(successData.getResult());
                            active_count_tv.setText("" + successData.getActiveProductCount());
                            unsold_count_tv.setText("" + successData.getUnsoldProductCount());
                            sold_count_tv.setText("" + successData.getSoldProductCount());
                            if (successData.getTotal_earning_with_shipping() == null || successData.getTotal_earning_with_shipping().equalsIgnoreCase("") || successData.getTotal_earning_with_shipping().equalsIgnoreCase("null") || successData.getTotal_earning_with_shipping().equalsIgnoreCase("0")) {
                                total_earning.setText(mySession.getValueOf(MySession.CurrencySign) + "0.00");
                            } else {
                                total_earning.setText(mySession.getValueOf(MySession.CurrencySign) + successData.getTotal_earning_with_shipping());
                            }

                        } else {
                            active_count_tv.setText("0");
                            unsold_count_tv.setText("0");
                            sold_count_tv.setText("0");
                            total_earning.setText(mySession.getValueOf(MySession.CurrencySign) + "0.00");

                        }


                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                t.printStackTrace();
                //progresbar.setVisibility(View.GONE);
                swipeToRefresh.setRefreshing(false);
                Log.e("TAG", t.toString());
            }
        });
    }


    public void getMerchantProperty() {
        swipeToRefresh.setRefreshing(true);
        Call<ResponseBody> call = ApiClient.getApiInterface().getMerchantProperty(user_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                swipeToRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("get merchant property response >", " >" + responseData);
                        if (object.getBoolean("status")) {
                            PropertyListModel successData = new Gson().fromJson(responseData, PropertyListModel.class);
                            tvActiveCount.setText(successData.getStatusPublishedCount()+"");

                        }
                        else {

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
                swipeToRefresh.setRefreshing(false);
                Log.e("TAG", t.toString());
            }
        });
    }


    public void gePropertyEnquiry() {
        swipeToRefresh.setRefreshing(true);
        Call<ResponseBody> call = ApiClient.getApiInterface().getPropertyEnquiryApi(user_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                swipeToRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("get property enquiry response >", " >" + responseData);
                        if (object.getBoolean("status")) {
                            PropertyEnquiryModel successData = new Gson().fromJson(responseData, PropertyEnquiryModel.class);
                            tvEnquiryCount.setText(successData.getData().size()+"");
                        }
                        else {

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
                swipeToRefresh.setRefreshing(false);
                Log.e("TAG", t.toString());
            }
        });
    }




}