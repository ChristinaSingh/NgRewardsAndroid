package main.com.ngrewards.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import main.com.ngrewards.R;
import main.com.ngrewards.activity.CardClickListener;
import main.com.ngrewards.beanclasses.CardBean;
import main.com.ngrewards.beanclasses.GiftCertificateModel;
import main.com.ngrewards.databinding.ItemGiftBalanceBinding;

public class GiftCertificateAdapter extends RecyclerView.Adapter<GiftCertificateAdapter.MyViewHolder> {
    Context context;
    ArrayList<GiftCertificateModel.Result> arrayList;


    public GiftCertificateAdapter(Context context, ArrayList<GiftCertificateModel.Result>arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGiftBalanceBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_gift_balance,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.binding.tvNo.setText("" + arrayList.get(position).getGiftCodes().getCurrentActiveCode());
        holder.binding.tvBalance.setText("$" + arrayList.get(position).getRemainingBalance());





    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemGiftBalanceBinding binding;
        public MyViewHolder(@NonNull ItemGiftBalanceBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }
    }
}