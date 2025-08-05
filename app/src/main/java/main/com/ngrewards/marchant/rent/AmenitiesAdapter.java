package main.com.ngrewards.marchant.rent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import main.com.ngrewards.R;
import main.com.ngrewards.databinding.ItemAmenitiesBinding;


public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.MyViewHolder> {
    Context context;
    ArrayList<PropertyAmenitiesModel.Datum> arrayList;
    onAmenitiesListener listener;

    public AmenitiesAdapter(Context context, ArrayList<PropertyAmenitiesModel.Datum> arrayList, onAmenitiesListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAmenitiesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_amenities, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.tvName.setText(arrayList.get(position).getName());
        if(arrayList.get(position).isChk()) holder.binding.ivCheck.setImageResource(R.drawable.check);
        else holder.binding.ivCheck.setImageResource(R.drawable.ic_gray_circuler);

        holder.itemView.setOnClickListener(v -> {
            listener.onAmenities(position,"click",arrayList.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemAmenitiesBinding binding;

        public MyViewHolder(@NonNull ItemAmenitiesBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }


    public void  notifyList(ArrayList<PropertyAmenitiesModel.Datum> list){
        arrayList = list;
        notifyDataSetChanged();
    }


}
