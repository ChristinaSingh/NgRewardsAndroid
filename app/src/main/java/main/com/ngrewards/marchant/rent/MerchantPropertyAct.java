package main.com.ngrewards.marchant.rent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import main.com.ngrewards.Models.PropertyListModel;
import main.com.ngrewards.R;
import main.com.ngrewards.constant.MySession;
import main.com.ngrewards.databinding.ActivityPropertyBinding;
import main.com.ngrewards.restapi.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchantPropertyAct extends AppCompatActivity implements onPropertyListener {
    ActivityPropertyBinding binding;
    public  ArrayList<PropertyListModel.Datum> properArrayList;
    PropertyAdapter propertyAdapter;
    MySession mySession;
    private String userId="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_property);
        initViews();
    }

    private void initViews() {

        properArrayList = new ArrayList<>();


        mySession = new MySession(this);
        String user_log_data = mySession.getKeyAlldata();
        if (user_log_data == null) {

        } else {
            try {
                JSONObject jsonObject = new JSONObject(user_log_data);
                String message = jsonObject.getString("status");
                if (message.equalsIgnoreCase("1")) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                    userId = jsonObject1.getString("id");

                    // stripe_account_id = jsonObject1.getString("stripe_account_id");


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        binding.swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMerchantProperty();

            }
        });


        propertyAdapter = new PropertyAdapter(MerchantPropertyAct.this,properArrayList,MerchantPropertyAct.this);
        binding.rvProperty.setAdapter(propertyAdapter);

        binding.btnBack.setOnClickListener(v -> finish());


        getMerchantProperty();
    }

    public void getMerchantProperty() {
        binding. progresbar.setVisibility(View.VISIBLE);
        Call<ResponseBody> call = ApiClient.getApiInterface().getMerchantProperty(userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progresbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("get merchant property response >", " >" + responseData);
                        if (object.getBoolean("status")) {
                            PropertyListModel successData = new Gson().fromJson(responseData, PropertyListModel.class);
                            binding.tvNotFound.setVisibility(View.GONE);
                            properArrayList.clear();
                            properArrayList.addAll(successData.getData());
                            propertyAdapter.notifyDataSetChanged();
                        }
                        else {
                            binding.tvNotFound.setVisibility(View.VISIBLE);
                            properArrayList.clear();
                            propertyAdapter.notifyDataSetChanged();
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
                binding.progresbar.setVisibility(View.GONE);
                Log.e("TAG", t.toString());
            }
        });
    }


    @Override
    public void onProperty(int position, String Type, PropertyListModel.Datum data) {
        if(Type.equalsIgnoreCase("edit")){

        }
           else if(Type.equalsIgnoreCase("publish")){
            updatePropertyStatus(data,"Published");
        }
           else  if(Type.equalsIgnoreCase("hide")){
            updatePropertyStatus(data,"Draft");

        }
               else deleteProperty(data.getId());


    }


    public void deleteProperty(String propertyId) {
        binding. progresbar.setVisibility(View.VISIBLE);
        Map<String, String> params = new LinkedHashMap<>();
        params.put("property_id",propertyId);
        Call<ResponseBody> call = ApiClient.getApiInterface().propertyDeleteApi(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progresbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("delete merchant property response >", " >" + responseData);
                        if (object.getBoolean("status")) {
                              getMerchantProperty();
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
                binding.progresbar.setVisibility(View.GONE);
                Log.e("TAG", t.toString());
            }
        });
    }

    public void updatePropertyStatus(PropertyListModel.Datum data,String status) {
        binding. progresbar.setVisibility(View.VISIBLE);
        Map<String, String> params = new LinkedHashMap<>();
        params.put("property_id",data.getId());
        params.put("merchant_id",data.getMerchantId());
        params.put("status",status);

        Call<ResponseBody> call = ApiClient.getApiInterface().propertyUpdateStatusApi(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progresbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("delete merchant property response >", " >" + responseData);
                        if (object.getBoolean("status")) {
                           finish();
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
                binding.progresbar.setVisibility(View.GONE);
                Log.e("TAG", t.toString());
            }
        });
    }


}
