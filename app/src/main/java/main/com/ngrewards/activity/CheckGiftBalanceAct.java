package main.com.ngrewards.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import main.com.ngrewards.Adapter.GiftCertificateAdapter;
import main.com.ngrewards.R;
import main.com.ngrewards.beanclasses.GiftCertificateModel;
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
    public static LinearLayout llGift;
    public static TextView tvNotAvailable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_check_gift_balanace);
        initViews();
    }

    private void initViews() {

        if(getIntent()!=null) userId = getIntent().getStringExtra("user_id");

         arrayList = new ArrayList<>();
         llGift = binding.llGift;
         tvNotAvailable = binding.tvNotAvailable;
         adapter = new GiftCertificateAdapter(CheckGiftBalanceAct.this,arrayList);
         binding.rvGiftCertificate.setAdapter(adapter);

         binding.backlay.setOnClickListener(view -> finish());



         checkGiftCertificate(userId);

        binding.edSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                adapter.filter(s.toString());


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
                           // arrayList.clear();
                          //  arrayList.addAll(successData.getResult());
                          //  adapter.notifyDataSetChanged();
                            llGift.setVisibility(View.VISIBLE);
                            tvNotAvailable.setVisibility(View.GONE);




                            adapter.updateList((ArrayList<GiftCertificateModel.Result>) successData.getResult());


                        } else {
                            arrayList.clear();
                            adapter.notifyDataSetChanged();
                            llGift.setVisibility(View.GONE);
                            tvNotAvailable.setVisibility(View.VISIBLE);

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
