package main.com.ngrewards.activity;

import android.content.Intent;
import android.graphics.Color;
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
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import main.com.ngrewards.Adapter.GiftCertificateAdapter;
import main.com.ngrewards.Models.PropertyListModel;
import main.com.ngrewards.R;
import main.com.ngrewards.beanclasses.GiftCertificateModel;
import main.com.ngrewards.constant.MySession;
import main.com.ngrewards.databinding.ActivityCheckGiftBalanaceBinding;
import main.com.ngrewards.restapi.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckGiftBalanceAct extends AppCompatActivity {
    ActivityCheckGiftBalanaceBinding binding;
    ArrayList<GiftCertificateModel.Result> arrayList;
    GiftCertificateAdapter adapter;
    String userId="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_check_gift_balanace);
        initViews();
    }

    private void initViews() {

        if(getIntent()!=null) userId = getIntent().getStringExtra("user_id");

         arrayList = new ArrayList<>();

         adapter = new GiftCertificateAdapter(CheckGiftBalanceAct.this,arrayList);
         binding.rvGiftCertificate.setAdapter(adapter);

         binding.backlay.setOnClickListener(view -> finish());



         checkGiftCertificate(userId);
    }





    private void checkGiftCertificate(String user_id) {

        binding.progresbar.setVisibility(View.VISIBLE);
        Call<ResponseBody> call = ApiClient.getApiInterface().check_user_gift_certificate(user_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progresbar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("check gift certificate >", " >" + responseData);
                        if (object.getString("status").equals("1")) {
                            GiftCertificateModel successData = new Gson().fromJson(responseData, GiftCertificateModel.class);
                            arrayList.clear();
                            arrayList.addAll(successData.getResult());
                            adapter.notifyDataSetChanged();


                        } else {
                            arrayList.clear();
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                binding.progresbar.setVisibility(View.GONE);
                Log.e("TAG", t.toString());
            }
        });

    }


}
