package main.com.ngrewards.Adapter;

import static main.com.ngrewards.activity.CheckGiftBalanceAct.llGift;
import static main.com.ngrewards.activity.CheckGiftBalanceAct.tvNotAvailable;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import main.com.ngrewards.R;
import main.com.ngrewards.activity.CardClickListener;
import main.com.ngrewards.beanclasses.CardBean;
import main.com.ngrewards.beanclasses.GiftCertificateModel;
import main.com.ngrewards.beanclasses.OfferBeanList;
import main.com.ngrewards.databinding.ItemGiftBalanceBinding;

public class GiftCertificateAdapter extends RecyclerView.Adapter<GiftCertificateAdapter.MyViewHolder> {
    Context context;
    ArrayList<GiftCertificateModel.Result> arrayList;
    ArrayList<GiftCertificateModel.Result> searchArrayList;


  /*  public GiftCertificateAdapter(Context context, ArrayList<GiftCertificateModel.Result> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        searchArrayList = new ArrayList<>();
        searchArrayList.addAll(arrayList); // correct copy
    }*/

    public GiftCertificateAdapter(Context context, ArrayList<GiftCertificateModel.Result> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        searchArrayList = new ArrayList<>();
        searchArrayList.addAll(arrayList);
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

        holder.binding.tvName.setText("" + arrayList.get(position).getMerchantDetails().getBusinessName());
        holder.binding.tvExpiry.setText(arrayList.get(position).getExpirationDateFormatted());



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


    public void filter(String charText) {

        arrayList.clear();

        if (charText == null || charText.trim().length() == 0) {
            arrayList.addAll(searchArrayList);
        } else {

            charText = charText.toLowerCase();
            Log.e("SIZE", "Original size: " + searchArrayList.size());
            for (GiftCertificateModel.Result wp : searchArrayList) {

               /* if (wp.getGiftCodes().getCurrentActiveCode()
                        .toLowerCase()
                        .contains(charText)) {

                    arrayList.add(wp);
                }*/

                   String giftCode = wp.getGiftCodes().getCurrentActiveCode().toLowerCase();
                    String businessName = wp.getMerchantDetails().getBusinessName().toLowerCase();

                    if (giftCode.contains(charText) || businessName.contains(charText)) {
                        arrayList.add(wp);
                    }


            }
        }
        if(arrayList.size()==0) {
            llGift.setVisibility(View.GONE);
            tvNotAvailable.setVisibility(View.VISIBLE);

        }
          else {
              llGift.setVisibility(View.VISIBLE);
             tvNotAvailable.setVisibility(View.GONE);

        }

        notifyDataSetChanged();
    }


    public void updateList(ArrayList<GiftCertificateModel.Result> list){
        arrayList.clear();
     //   arrayList.addAll(list);


        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", java.util.Locale.ENGLISH);
        Date today = new Date();

        for (GiftCertificateModel.Result item : list) {

            try {

                Date expiryDate = sdf.parse(item.getExpirationDateFormatted());

                if (expiryDate != null && !expiryDate.before(today)) {
                    arrayList.add(item);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }



        searchArrayList.clear();
        searchArrayList.addAll(list);

        notifyDataSetChanged();
    }



}