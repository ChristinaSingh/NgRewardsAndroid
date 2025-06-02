package main.com.ngrewards.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import main.com.ngrewards.Models.MembershipModel;
import main.com.ngrewards.Models.PlanHistoryModel;
import main.com.ngrewards.R;
import main.com.ngrewards.activity.WebViewCalled;
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
        holder.binding.tvPrice.setText("$"+arrayList.get(position).getAmount() + "/" + arrayList.get(position).getDurationName());
        holder.binding.tvEndDate.setText( "End Date - "+arrayList.get(position).getEndDate());
        holder.binding.tvStartDate.setText( "Start Date - "+arrayList.get(position).getStartDate());

        holder.binding.tv1.setText(arrayList.get(position).getFeatures().get(0));
        holder.binding.tv2.setText(arrayList.get(position).getFeatures().get(1));
        holder.binding.tv3.setText(arrayList.get(position).getFeatures().get(2));
        holder.binding.tv4.setText(arrayList.get(position).getFeatures().get(3));
        holder.binding.tv5.setText(arrayList.get(position).getFeatures().get(4));

        holder.binding.tvStatus.setText(arrayList.get(position).getStatus());

        holder.binding.btnReceipt.setOnClickListener(v -> {
           if(arrayList.get(position).getReceiptUrl()!=null){
               context.startActivity(new Intent(context, WebViewCalled.class)
                       .putExtra("reciept_url", arrayList.get(position).getReceiptUrl()));
           }
           ;
        });

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