package main.com.ngrewards.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import main.com.ngrewards.Models.MembershipModel;
import main.com.ngrewards.R;
import main.com.ngrewards.activity.SelectPayMethodAct;
import main.com.ngrewards.databinding.ItemMembershipBinding;
import main.com.ngrewards.placeorderclasses.SelectPaymentMethodAct;


public class MembershipAdapter extends RecyclerView.Adapter<MembershipAdapter.MyViewHolder> {
    Context context;
    ArrayList<MembershipModel.Result> arrayList;
   // ChatOnListener listener;

    public MembershipAdapter(Context context, ArrayList<MembershipModel.Result>arrayList/*, ChatOnListener listener*/) {
        this.context = context;
        this.arrayList = arrayList;
       // this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMembershipBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_membership,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.tvTitle.setText(arrayList.get(position).getPlanName());
        holder.binding.tvPrice.setText("$"+arrayList.get(position).getPrice() + "/" + arrayList.get(position).getDurationType());
        holder.binding.tvTitle1.setText(arrayList.get(position).getDescription());

        holder.binding.tv1.setText(arrayList.get(position).getFeatures().get(0));
        holder.binding.tv2.setText(arrayList.get(position).getFeatures().get(1));
        holder.binding.tv3.setText(arrayList.get(position).getFeatures().get(2));
        holder.binding.tv4.setText(arrayList.get(position).getFeatures().get(3));
        holder.binding.tv5.setText(arrayList.get(position).getFeatures().get(4));


        if(arrayList.get(position).getRecommended().equals("Yes")) {
            holder.binding.btnRecommended.setVisibility(View.VISIBLE);
            holder.binding.llMain.setBackground(context.getDrawable(R.drawable.rounded_white_beg_bg_5));
        }
         else {
             holder.binding.btnRecommended.setVisibility(View.GONE);
            holder.binding.llMain.setBackground(context.getDrawable(R.drawable.rounded_white_bg_5));

        }

        holder.itemView.setOnClickListener(view -> {
            context.startActivity(new Intent(context, SelectPayMethodAct.class)
                    .putExtra("planId",arrayList.get(position).getPlanId()));
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemMembershipBinding binding;
        public MyViewHolder(@NonNull ItemMembershipBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }
    }
}
