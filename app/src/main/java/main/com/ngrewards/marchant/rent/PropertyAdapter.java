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
import main.com.ngrewards.databinding.ItemPropertyBinding;


public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.MyViewHolder> {
    Context context;
    ArrayList<PropertyListModel.Datum> arrayList;
    onPropertyListener listener;

    public PropertyAdapter(Context context, ArrayList<PropertyListModel.Datum> arrayList, onPropertyListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPropertyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_property, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.tvName.setText(arrayList.get(position).getTitle());
        holder.binding.tvDes.setText(arrayList.get(position).getDescription());
        holder.binding.tvPrice.setText("$" + arrayList.get(position).getPrice());

        if(arrayList.get(position).getStatus().equalsIgnoreCase("Published"))
            holder.binding.viewOverlay.setVisibility(View.GONE);
        else  holder.binding.viewOverlay.setVisibility(View.VISIBLE);

        Glide.with(context)
                .load(arrayList.get(position)
                        .getFiles().get(0).getFileUrl())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.binding.ivProperty);


        holder.binding.ivMore.setOnClickListener(view -> {


            if(arrayList.get(position).getStatus().equalsIgnoreCase("Published")){
                PopupMenu popupMenu = new PopupMenu(context, holder.binding.ivMore);
                popupMenu.getMenuInflater().inflate(R.menu.menu_property_options, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.menu_edit) {
                        listener.onProperty(position,"edit",arrayList.get(position));
                        popupMenu.dismiss();
                        // Handle Edit
                    } else if (itemId == R.id.menu_hide) {
                        // Handle Hide Property
                        listener.onProperty(position,"hide",arrayList.get(position));
                        popupMenu.dismiss();
                    } else if (itemId == R.id.menu_delete) {
                        // Handle Delete
                        listener.onProperty(position,"delete",arrayList.get(position));
                        popupMenu.dismiss();
                    }
                    return true;
                });
                popupMenu.show();
            }

            else {
                PopupMenu popupMenu = new PopupMenu(context, holder.binding.ivMore);
                popupMenu.getMenuInflater().inflate(R.menu.menu_property_options2, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.menu_edit) {
                        listener.onProperty(position,"edit",arrayList.get(position));
                        popupMenu.dismiss();
                        // Handle Edit
                    } else if (itemId == R.id.menu_publish) {
                        // Handle Hide Property
                        listener.onProperty(position,"publish",arrayList.get(position));
                        popupMenu.dismiss();
                    } else if (itemId == R.id.menu_delete) {
                        // Handle Delete
                        listener.onProperty(position,"delete",arrayList.get(position));
                        popupMenu.dismiss();
                    }
                    return true;
                });
                popupMenu.show();
            }



        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemPropertyBinding binding;

        public MyViewHolder(@NonNull ItemPropertyBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }
    }




}
