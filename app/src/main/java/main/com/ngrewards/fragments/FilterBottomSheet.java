package main.com.ngrewards.fragments;

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
import main.com.ngrewards.databinding.BottomSheetFilterBinding;

public class FilterBottomSheet extends BottomSheetDialogFragment {
    private BottomSheetFilterBinding binding;
    private BottomSheetBehavior<View> behavior;
    FilterSheetListener listener;
    String type="";

    public FilterBottomSheet(String type) {
        // Required empty public constructor
        this.type = type;
    }

    public FilterBottomSheet callBack(FilterSheetListener listener) {
        this.listener = listener;
        return this;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.bottom_sheet_filter, null, false);
        dialog.setContentView(binding.getRoot());
        behavior = BottomSheetBehavior.from((View) binding.getRoot().getParent());
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        binding.tvAllNotification.setOnClickListener(v -> {
            dismiss();
            listener.onFilter(type,"all");
        });

        binding.tvCurrentWeek.setOnClickListener(v -> {
            dismiss();
            listener.onFilter(type,"current_week");
        });


        binding.tvLastWeek.setOnClickListener(v -> {
            dismiss();
            listener.onFilter(type,"last_week");
        });


        binding.tvLastMonth.setOnClickListener(v -> {
            dismiss();
            listener.onFilter(type,"last_month");
        });

        binding.tvLast3Month.setOnClickListener(v -> {
            dismiss();
            listener.onFilter(type,"last_3_months");
        });


        binding.btnCancel.setOnClickListener(v -> dismiss());


        return dialog;
    }
}
