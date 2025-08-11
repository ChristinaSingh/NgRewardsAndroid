package main.com.ngrewards.marchant.rent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import main.com.ngrewards.R;
import main.com.ngrewards.databinding.ActivityEnquiryDetailBinding;
import main.com.ngrewards.restapi.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnquiryDetailAct extends AppCompatActivity {
    ActivityEnquiryDetailBinding binding;
    String propertyEnquiryId="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_enquiry_detail);

        initView();

    }

    private void initView() {
        if(getIntent()!=null){
            propertyEnquiryId = getIntent().getStringExtra("id");
        }

        propertyEnquiry(propertyEnquiryId);

        binding.btnCall.setOnClickListener(v -> openDialer(binding.tvPhoneNumber.getText().toString()));

        binding.btnMail.setOnClickListener(v -> sendEmail(binding.tvEmail.getText().toString()));

        binding.btnBack.setOnClickListener(v -> finish());

    }


    public void propertyEnquiry(String propertyEnquiryId) {
        binding.progresbar.setVisibility(View.VISIBLE);
        Call<ResponseBody> call = ApiClient.getApiInterface().singlePropertyEnquiry(propertyEnquiryId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progresbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("single property enquiry response >", " >" + responseData);
                        if (object.getBoolean("status")) {

                            binding.tvTitle.setText(object.getJSONObject("data").getJSONObject("property").getString("title"));
                            binding.tvFullName.setText(object.getJSONObject("data").getString("full_name"));
                            binding.tvUsername.setText(object.getJSONObject("data").getString("username"));
                            binding.tvEmail.setText(object.getJSONObject("data").getString("email"));
                            binding.tvPhoneNumber.setText(object.getJSONObject("data").getString("phone_number"));
                            binding.tvMessage.setText(object.getJSONObject("data").getString("message"));

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


    private void sendEmail11(String mail) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + mail)); // Properly formatted mailto URI

        // Optional: add extras
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        } else {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmail(String mail) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // Ensures only email apps respond
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");

        // Try to open Gmail specifically
        intent.setPackage("com.google.android.gm");

        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            // Gmail not available – fallback to default email apps
            Intent fallbackIntent = new Intent(Intent.ACTION_SENDTO);
            fallbackIntent.setData(Uri.parse("mailto:"));
            fallbackIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
            fallbackIntent.putExtra(Intent.EXTRA_SUBJECT, "");
            fallbackIntent.putExtra(Intent.EXTRA_TEXT, "");

            if (fallbackIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(fallbackIntent);
            } else {
                Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
            }
        }
    }




    private void openDialer(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

}
