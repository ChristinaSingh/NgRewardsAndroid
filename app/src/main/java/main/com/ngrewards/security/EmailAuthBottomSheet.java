package main.com.ngrewards.security;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import main.com.ngrewards.Interfaces.FilterSheetListener;
import main.com.ngrewards.R;
import main.com.ngrewards.databinding.BottomSheetEmailBinding;

public class EmailAuthBottomSheet extends BottomSheetDialogFragment {
    private BottomSheetEmailBinding binding;
    private BottomSheetBehavior<View> behavior;
    FilterSheetListener listener;
    String type="";

    public EmailAuthBottomSheet(String type) {
        // Required empty public constructor
        this.type = type;
    }

    public EmailAuthBottomSheet callBack(FilterSheetListener listener) {
        this.listener = listener;
        return this;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.bottom_sheet_email, null, false);
        dialog.setContentView(binding.getRoot());
        behavior = BottomSheetBehavior.from((View) binding.getRoot().getParent());
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);


        binding.btnEmail.setOnClickListener(v -> {
            dismiss();
            listener.onFilter(type,"sendOtp");
        });


        binding.btnCancel.setOnClickListener(v -> dismiss());


        return dialog;
    }
}