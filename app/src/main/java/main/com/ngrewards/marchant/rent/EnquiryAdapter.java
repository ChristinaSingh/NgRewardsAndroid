package main.com.ngrewards.marchant.rent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import main.com.ngrewards.Models.PropertyListModel;
import main.com.ngrewards.R;
import main.com.ngrewards.databinding.ItemEnquiryBinding;
import main.com.ngrewards.databinding.ItemPropertyBinding;



public class EnquiryAdapter extends RecyclerView.Adapter<EnquiryAdapter.MyViewHolder> {
    Context context;
    ArrayList<PropertyEnquiryModel.Datum> arrayList;


    public EnquiryAdapter(Context context, ArrayList<PropertyEnquiryModel.Datum> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEnquiryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_enquiry, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.tvName.setText(arrayList.get(position).getProperty().getTitle());
        holder.binding.tvDes.setText(arrayList.get(position).getProperty().getDescription());
        holder.binding.tvPrice.setText("$" + arrayList.get(position).getProperty().getPrice());

        Glide.with(context)
                .load(arrayList.get(position).getProperty()
                        .getFiles().get(0).getFileUrl())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.binding.ivProperty);




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemEnquiryBinding binding;

        public MyViewHolder(@NonNull ItemEnquiryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }
    }




}
