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

import java.io.IOException;
import java.util.ArrayList;

import main.com.ngrewards.R;
import main.com.ngrewards.constant.MySession;
import main.com.ngrewards.databinding.ActivityEnquiryBinding;
import main.com.ngrewards.restapi.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyEnquiryListAct extends AppCompatActivity  {
    ActivityEnquiryBinding binding;
    public ArrayList<PropertyEnquiryModel.Datum> enquiryArrayList;
    EnquiryAdapter enquiryAdapter;
    MySession mySession;
    private String userId="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enquiry);
        initViews();
    }

    private void initViews() {

        enquiryArrayList = new ArrayList<>();


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
                getPropertyEnquiry();

            }
        });

        enquiryAdapter = new EnquiryAdapter(PropertyEnquiryListAct.this,enquiryArrayList);
        binding.rvProperty.setAdapter(enquiryAdapter);

        binding.btnBack.setOnClickListener(v -> finish());


        getPropertyEnquiry();
    }

    public void getPropertyEnquiry() {
        binding.progresbar.setVisibility(View.VISIBLE);
        Call<ResponseBody> call = ApiClient.getApiInterface().getPropertyEnquiryApi(userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progresbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("get property enquiry response >", " >" + responseData);
                        if (object.getBoolean("status")) {
                            PropertyEnquiryModel successData = new Gson().fromJson(responseData, PropertyEnquiryModel.class);
                            binding.tvNotFound.setVisibility(View.GONE);
                            enquiryArrayList.clear();
                            enquiryArrayList.addAll(successData.getData());
                            enquiryAdapter.notifyDataSetChanged();
                        }
                        else {
                            binding.tvNotFound.setVisibility(View.VISIBLE);
                            enquiryArrayList.clear();
                            enquiryAdapter.notifyDataSetChanged();
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