package main.com.ngrewards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import main.com.ngrewards.Models.PropertyListModel;
import main.com.ngrewards.R;
import main.com.ngrewards.androidmigx.MainTabActivity;
import main.com.ngrewards.constant.MySession;
import main.com.ngrewards.databinding.ActivityPropertyEnquiryBinding;
import main.com.ngrewards.restapi.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyEnquiryAct extends AppCompatActivity {
    ActivityPropertyEnquiryBinding binding;
    PropertyListModel.Datum dataModel;
    MySession mySession;
    private String userId="",userName="",email="",fullName="",phone="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_property_enquiry);

        mySession = new MySession(PropertyEnquiryAct.this);
        String user_log_data = mySession.getKeyAlldata();
        Log.e("USER DATA", ">> " + user_log_data);
        if (user_log_data == null) {

        } else {

            try {
                JSONObject jsonObject = new JSONObject(user_log_data);
                String message = jsonObject.getString("status");
                if (message.equalsIgnoreCase("1")) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                    userId = jsonObject1.getString("id");
                    userName = jsonObject1.getString("username");
                    binding.edEmail.setText("" + jsonObject1.getString("email"));
                    binding.edFullName.setText("" + jsonObject1.getString("fullname"));
                    binding.edPhoneNumber.setText("" + jsonObject1.getString("phone"));

                }
            } catch (JSONException ee) {
                ee.printStackTrace();

            }
        }
        initViews();
    }

    private void initViews() {

        if(getIntent()!=null){
            dataModel = (PropertyListModel.Datum) getIntent().getSerializableExtra("propertyData");


        }

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnSubmit.setOnClickListener(v -> validation());
    }

    private void validation() {
        if(binding.edFullName.getText().toString().isEmpty())
            Toast.makeText(this, getString(R.string.enter_full_name), Toast.LENGTH_SHORT).show();
        else if(binding.edEmail.getText().toString().isEmpty())
            Toast.makeText(this, getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
        else if(binding.edPhoneNumber.getText().toString().isEmpty())
            Toast.makeText(this, getString(R.string.enter_phone_number), Toast.LENGTH_SHORT).show();
        else sendEnquiry();

    }


    private void sendEnquiry() {
        binding.progresbar.setVisibility(View.VISIBLE);
        Map<String, String> params = new LinkedHashMap<>();
        params.put("property_id", dataModel.getId());
        params.put("member_id", userId);
        params.put("full_name", binding.edFullName.getText().toString());
        params.put("username", userName);
        params.put("phone_number", binding.edPhoneNumber.getText().toString());
        params.put("email", binding.edEmail.getText().toString());
        params.put("message", binding.edMsg.getText().toString());
        params.put("merchant_id", dataModel.getMerchantId());


        Log.e("request data >> ", " >> " + params);


        Call<ResponseBody> call = ApiClient.getApiInterface().propertyEnquiryApi(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progresbar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("property enquiry response >", " >" + responseData);
                        if (object.getBoolean("status")) {
                            Toast.makeText(PropertyEnquiryAct.this, getString(R.string.your_request_send), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(PropertyEnquiryAct.this, MainTabActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
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
