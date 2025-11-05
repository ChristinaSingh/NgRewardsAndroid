package main.com.ngrewards.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.com.ngrewards.Models.MembershipModel;
import main.com.ngrewards.R;
import main.com.ngrewards.activity.MyListener;
import main.com.ngrewards.activity.PreferenceConnector;
import main.com.ngrewards.activity.SelectPayMethodAct;
import main.com.ngrewards.androidmigx.MainTabActivity;
import main.com.ngrewards.databinding.ItemMembershipBinding;


public class MembershipAdapter extends RecyclerView.Adapter<MembershipAdapter.MyViewHolder> {
    Context context;
    ArrayList<MembershipModel.Result> arrayList;
    MyListener listener;
    ArrayList<String> durationArrayList= new ArrayList<>();
    private String durationType = "Week";
    double totalPrice=0.0;
    public MembershipAdapter(Context context, ArrayList<MembershipModel.Result>arrayList, MyListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
        durationArrayList.add("Week");
        durationArrayList.add("Month");
        durationArrayList.add("Annual");

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMembershipBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_membership,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.tvTitle.setText(arrayList.get(position).getPlanDisplayName());
        holder.binding.tvPrice.setText("$"+arrayList.get(position).getPrice() + "/" + durationType);
        holder.binding.tvTitle1.setText(arrayList.get(position).getDescription());

      //  holder.binding.tv1.setText(arrayList.get(position).getFeatures().get(0));
      //  holder.binding.tv2.setText(arrayList.get(position).getFeatures().get(1));
      //  holder.binding.tv3.setText(arrayList.get(position).getFeatures().get(2));
      //  holder.binding.tv4.setText(arrayList.get(position).getFeatures().get(3));
      //  holder.binding.tv5.setText(arrayList.get(position).getFeatures().get(4));




        if(arrayList.get(position).getRecommended().equals("Yes")) {
            holder.binding.btnRecommended.setVisibility(View.VISIBLE);
            holder.binding.llMain.setBackground(context.getDrawable(R.drawable.rounded_white_beg_bg_5));
        }
         else {
             holder.binding.btnRecommended.setVisibility(View.GONE);
            holder.binding.llMain.setBackground(context.getDrawable(R.drawable.rounded_white_bg_5));

        }


        if(arrayList.get(position).getPrice().equalsIgnoreCase("0.00"))
        {
            holder.binding.btnSubscribe.setVisibility(View.GONE);
            holder.binding.rlPrice.setVisibility(View.GONE);

        }
        else {
            holder.binding.btnSubscribe.setVisibility(View.VISIBLE);
            holder.binding.rlPrice.setVisibility(View.VISIBLE);

        }


        holder.binding.btnSubscribe.setOnClickListener(view -> {
            if(totalPrice==0.0) totalPrice = Double.parseDouble(arrayList.get(position).getPrice());
            PreferenceConnector.writeString(context, PreferenceConnector.Duration_type, durationType);
            listener.callback(holder.itemView,arrayList.get(position).getPlanId(),totalPrice+"");
          //  context.startActivity(new Intent(context, SelectPayMethodAct.class)
           //         .putExtra("planId",arrayList.get(position).getPlanId()));
        });


        holder.binding.tvPrice.setOnClickListener(view -> {
            showDropDownDuration(view,holder.binding.tvPrice,durationArrayList,position);
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

    private void showDropDownDuration(View v, TextView textView, List<String> stringList,int position) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        for (int i = 0; i < stringList.size(); i++) {
            popupMenu.getMenu().add(stringList.get(i));
        }
        popupMenu.setOnMenuItemClickListener(menuItem -> {
           // textView.setText(menuItem.getTitle());
            for (int i = 0; i < stringList.size(); i++) {
                if (stringList.get(i).equalsIgnoreCase(menuItem.getTitle().toString())) {
                   if(stringList.get(i).equalsIgnoreCase("Week")){
                        totalPrice = Double.parseDouble(arrayList.get(position).getPrice());
                       durationType ="Week";
                       textView.setText("$"+totalPrice + "/" + durationType);

                   }

                   else if(stringList.get(i).equalsIgnoreCase("Month")){
                        totalPrice = Double.parseDouble(arrayList.get(position).getPrice()) * 4;
                       durationType ="Month";
                       textView.setText("$"+totalPrice + "/" + durationType);

                    }


                   else if(stringList.get(i).equalsIgnoreCase("Annual")){
                        totalPrice = Double.parseDouble(arrayList.get(position).getPrice()) * 52;
                       durationType ="Annual";
                       textView.setText("$"+totalPrice + "/" + durationType);


                   }


                }
            }
            return true;
        });
        popupMenu.show();
    }
}
