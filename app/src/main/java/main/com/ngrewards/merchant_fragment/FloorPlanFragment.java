package main.com.ngrewards.merchant_fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.com.ngrewards.Models.PropertyListModel;
import main.com.ngrewards.R;
import main.com.ngrewards.constant.MySession;
import main.com.ngrewards.databinding.FragmentFloorPlanBinding;
import main.com.ngrewards.marchant.rent.AddPropertyAct;
import main.com.ngrewards.marchant.rent.FloorAdapter;
import main.com.ngrewards.marchant.rent.RentCategoryModel;
import main.com.ngrewards.restapi.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FloorPlanFragment  extends Fragment {
   FragmentFloorPlanBinding binding;
    private MySession mySession;
    String merchantId ="",categoryId="",amenities="";
    ArrayList<PropertyListModel.Datum>propertyList;
    FloorAdapter floorAdapter;
    public  ArrayList<RentCategoryModel.Datum> categoryArrayList;

    public FloorPlanFragment(String merchantId,String categoryId,String amenities) {
        // Required empty public constructor
        this.merchantId =merchantId;
        this.categoryId =categoryId;
        this.amenities =amenities;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_floor_plan, container, false);

        initViews();
        mySession = new MySession(getActivity());
        return binding.getRoot();
    }

    private void initViews() {
        propertyList = new ArrayList<>();
        categoryArrayList = new ArrayList<>();
        floorAdapter = new FloorAdapter(requireActivity(),propertyList);
        binding.rvFloor.setAdapter(floorAdapter);

        getMerchantProperty();
        getCategory();

        binding.tvCategory.setOnClickListener(v -> showDropDownCategory(v,binding.tvCategory,categoryArrayList));

        binding.tvReset.setOnClickListener(v -> {
            categoryId = "";
            binding.tvCategory.setText(getString(R.string.filterbycateory));
            getMerchantProperty();

        });


    }


    public void getMerchantProperty() {
       binding. progresbar.setVisibility(View.VISIBLE);
        Log.e("Merc Photo >", " >" + merchantId + " >> " );
        Call<ResponseBody> call = ApiClient.getApiInterface().getMerchantProperty(merchantId/*, categoryId, amenities*/);
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
                                propertyList.clear();
                                propertyList.addAll(successData.getData());
                                floorAdapter.notifyDataSetChanged();

                            }
                            else {
                                binding.tvNotFound.setVisibility(View.VISIBLE);
                                propertyList.clear();
                                floorAdapter.notifyDataSetChanged();
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

    public void getMerchantPropertyFilter() {
        binding. progresbar.setVisibility(View.VISIBLE);
        Log.e("Merc Photo >", " >" + merchantId + " >> " );
        Call<ResponseBody> call = ApiClient.getApiInterface().getMerchantPropertyFilter(merchantId, categoryId, amenities);
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
                            propertyList.clear();
                            propertyList.addAll(successData.getData());
                            floorAdapter.notifyDataSetChanged();

                        }
                        else {
                            binding.tvNotFound.setVisibility(View.VISIBLE);
                            propertyList.clear();
                            floorAdapter.notifyDataSetChanged();
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



    private void showDropDownCategory(View v, TextView textView, List<RentCategoryModel.Datum> stringList) {
        PopupMenu popupMenu = new PopupMenu(requireActivity(), v);
        for (int i = 0; i < stringList.size(); i++) {
            popupMenu.getMenu().add(stringList.get(i).getName());
        }


        popupMenu.setOnMenuItemClickListener(menuItem -> {
            //   textView.setText(menuItem.getTitle());
            for (int i = 0; i < stringList.size(); i++) {
                if (stringList.get(i).getName().equalsIgnoreCase(menuItem.getTitle().toString())) {
                    categoryId = stringList.get(i).getName();
                    textView.setText(stringList.get(i).getName());
                    getMerchantPropertyFilter();


                }
            }
            return true;
        });
        popupMenu.show();
    }



    private void getCategory() {
        binding.progresbar.setVisibility(View.VISIBLE);
        Call<ResponseBody> call = ApiClient.getApiInterface().getPropertyCategoryApi();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progresbar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("get category response >", " >" + responseData);
                        if (object.getBoolean("status")) {
                            RentCategoryModel successData = new Gson().fromJson(responseData, RentCategoryModel.class);
                            categoryArrayList.clear();
                            categoryArrayList.addAll(successData.getData());

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
