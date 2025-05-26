package main.com.ngrewards.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import main.com.ngrewards.Adapter.SubscriptionPlanAdapter;
import main.com.ngrewards.Models.MembershipModel;
import main.com.ngrewards.Models.PlanHistoryModel;
import main.com.ngrewards.R;
import main.com.ngrewards.constant.MySession;
import main.com.ngrewards.restapi.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPlanHistoryAct extends AppCompatActivity {
    RecyclerView rvPlan;
    RelativeLayout backlay;
    private ProgressBar progresbar;
    ArrayList<PlanHistoryModel.MembershipDataHistory> arrayList = new ArrayList<>();
    String user_log_data = "", user_id = "";
    private MySession mySession;
    SubscriptionPlanAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_history);

        mySession = new MySession(this);
        rvPlan = findViewById(R.id.rvPlan);
        backlay = findViewById(R.id.backlay);
        progresbar = findViewById(R.id.progresbar11);

        user_log_data = mySession.getKeyAlldata();
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


        adapter = new SubscriptionPlanAdapter(MyPlanHistoryAct.this, arrayList);
        rvPlan.setAdapter(adapter);


        getPlanHistory(user_id);


        backlay.setOnClickListener(v -> finish());

    }

    private void getPlanHistory(String id) {
        progresbar.setVisibility(View.VISIBLE);
        Call<ResponseBody> call = ApiClient.getApiInterface().getPlanHistoryApi(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progresbar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("get All membership plan", " >" + responseData);
                        if (object.getString("status").equals("1")) {
                            PlanHistoryModel successData = new Gson().fromJson(responseData, PlanHistoryModel.class);
                            arrayList.clear();
                            arrayList.addAll(successData.getMembershipDataHistory());

                            adapter.notifyDataSetChanged();


                        } else {
                            arrayList.clear();
                            adapter.notifyDataSetChanged();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
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
