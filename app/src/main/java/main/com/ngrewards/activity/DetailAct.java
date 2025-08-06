package main.com.ngrewards.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import main.com.ngrewards.Models.PropertyListModel;
import main.com.ngrewards.R;
import main.com.ngrewards.databinding.ActivityDetailBinding;
import main.com.ngrewards.marchant.rent.PropertyImgSlideAdapter;

public class DetailAct extends AppCompatActivity {
    ActivityDetailBinding binding;
    PropertyListModel.Datum dataModel;
    PropertyImgSlideAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        initViews();
    }

    private void initViews() {

        if(getIntent()!=null){
            dataModel = (PropertyListModel.Datum) getIntent().getSerializableExtra("propertyData");
            binding.tvTitle.setText(dataModel.getTitle());
            binding.tvCategory.setText(dataModel.getCategory());
            binding.tvDescription.setText(dataModel.getDescription());
            binding.tvAmenities.setText(dataModel.getAmenities().get(0).getName());
            binding.tvUnitNumber.setText(dataModel.getUnitNumber());
            binding.tvFloorLevel.setText(dataModel.getFloorLevel());
            binding.tvPrice.setText("$"+dataModel.getPrice());
            binding.tvSquare.setText(dataModel.getSquareFootage());
            binding.tvAvailable.setText(dataModel.getAvailabilityDate());

        }

        binding.backlay.setOnClickListener(v -> finish());

        binding.btnApply.setOnClickListener(v -> startActivity(new Intent(this, PropertyEnquiryAct.class)
                .putExtra("propertyData",dataModel )));


        adapter = new PropertyImgSlideAdapter(DetailAct.this, dataModel.getFiles());
        binding.productimagePager.setAdapter(adapter);
        binding.fullscreenIndecator.setViewPager(binding.productimagePager);
        final float density = getResources().getDisplayMetrics().density;
        binding.fullscreenIndecator.setRadius(5 * density);
    }
}
