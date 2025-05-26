package main.com.ngrewards.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import main.com.ngrewards.Models.MembershipModel;
import main.com.ngrewards.Models.PlanHistoryModel;
import main.com.ngrewards.R;
import main.com.ngrewards.databinding.ItemPlanBinding;

public class SubscriptionPlanAdapter extends RecyclerView.Adapter<SubscriptionPlanAdapter.MyViewHolder> {
    Context context;
    ArrayList<PlanHistoryModel.MembershipDataHistory> arrayList;
    // ChatOnListener listener;

    public SubscriptionPlanAdapter(Context context, ArrayList<PlanHistoryModel.MembershipDataHistory>arrayList/*, ChatOnListener listener*/) {
        this.context = context;
        this.arrayList = arrayList;
        // this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPlanBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_plan,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.tvTitle.setText(arrayList.get(position).getPlanName());
        holder.binding.tvPrice.setText("USD "+arrayList.get(position).getAmount() + " / " + arrayList.get(position).getDurationName());
        holder.binding.tvEndDate.setText("End Date - "+arrayList.get(position).getEndDate());





    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemPlanBinding binding;
        public MyViewHolder(@NonNull ItemPlanBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }
    }
}