package main.com.ngrewards.settingclasses;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import main.com.ngrewards.R;
import main.com.ngrewards.databinding.ActivityBiometricEnableDisableBinding;

public class BioMetricAuthenticationAct extends AppCompatActivity {
    ActivityBiometricEnableDisableBinding binding;
    
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_biometric_enable_disable);
        initViews();
    }

    private void initViews() {


        SharedPreferences prefs = getSharedPreferences("login_prefs", MODE_PRIVATE);
        boolean isBiometricEnabled = prefs.getBoolean("biometric_enabled", false);
        binding.switchAuthentication.setChecked(isBiometricEnabled);
        if(isBiometricEnabled) binding.tvChk.setText(getString(R.string.enable));
         else binding.tvChk.setText(getString(R.string.disable));

        binding.switchAuthentication.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("biometric_enabled", isChecked).apply();
            if(isChecked) binding.tvChk.setText(getString(R.string.enable));
            else binding.tvChk.setText(getString(R.string.disable));
        });


        binding.backlay.setOnClickListener(view -> finish());

    }
}
