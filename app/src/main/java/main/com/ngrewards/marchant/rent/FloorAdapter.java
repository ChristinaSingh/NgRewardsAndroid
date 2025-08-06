package main.com.ngrewards.marchant.rent;

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
import main.com.ngrewards.Models.PropertyListModel;
import main.com.ngrewards.R;
import main.com.ngrewards.activity.DetailAct;
import main.com.ngrewards.activity.SelectPayMethodAct;
import main.com.ngrewards.databinding.ItemFloorBinding;
import main.com.ngrewards.databinding.ItemMembershipBinding;


public class FloorAdapter extends RecyclerView.Adapter<FloorAdapter.MyViewHolder> {
    Context context;
    ArrayList<PropertyListModel.Datum> arrayList;
    // ChatOnListener listener;

    public FloorAdapter(Context context, ArrayList<PropertyListModel.Datum> arrayList/*, ChatOnListener listener*/) {
        this.context = context;
        this.arrayList = arrayList;
        // this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFloorBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_floor, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.tvName.setText(arrayList.get(position).getTitle());
        holder.binding.tvArea.setText(arrayList.get(position).getSquareFootage()+ " Sq.ft");
        holder.binding.tvPrice.setText("$" + arrayList.get(position).getPrice());

        holder.binding.tvLevel.setText(context.getString(R.string.level) + " : " + arrayList.get(position).getFloorLevel());
        holder.binding.tvAvailable.setText(context.getString(R.string.available) + " : " + arrayList.get(position).getAvailabilityDate());

        Glide.with(context)
                .load(arrayList.get(position)
                        .getFiles().get(0).getFileUrl())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.binding.productImg);


        holder.binding.btnApply.setOnClickListener(view -> {
            context.startActivity(new Intent(context, DetailAct.class)
                    .putExtra("propertyData", arrayList.get(position)));
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemFloorBinding binding;

        public MyViewHolder(@NonNull ItemFloorBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }
    }
}
