package main.com.ngrewards.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import main.com.ngrewards.R;
import main.com.ngrewards.activity.CardClickListener;
import main.com.ngrewards.activity.UpdateMemberCard;
import main.com.ngrewards.beanclasses.CardBean;
import main.com.ngrewards.databinding.ItemCusCardBinding;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
Context context;
ArrayList<CardBean> arrayList;
CardClickListener listener;

public CardAdapter(Context context, ArrayList<CardBean>arrayList, CardClickListener listener) {
    this.context = context;
    this.arrayList = arrayList;
     this.listener = listener;
}

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemCusCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_cus_card,parent,false);
    return new MyViewHolder(binding);
}

@Override
public void onBindViewHolder(@NonNull MyViewHolder holder,  int position) {

    String cardbrandstr = arrayList.get(position).getBrand();
    String carnum = arrayList.get(position).getLast4();
    if (cardbrandstr.length() > 4) {
        cardbrandstr = cardbrandstr.substring(0, 4);
    }
    String stars = "**** ****";
    holder.binding.savedcardnumber.setText("" + cardbrandstr + " " + stars + " " + carnum);

    // savedcardnumber.setText(""+cardBeanArrayList.get(position).getSetfullcardnumber());


    holder.binding.validdate.setText("" + arrayList.get(position).getSetfullexpyearmonth());
    holder.binding.cardbrand.setText("" + arrayList.get(position).getBrand());
    holder.binding.cardtype.setText("" + arrayList.get(position).getFunding());
    holder.binding.cardtype.setAllCaps(true);



    holder.binding.creditcardRbut.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          //  card_id = arrayList.get(position).getId();
          //  customer_id = arrayList.get(position).getCustomer();
          //  card_number = arrayList.get(position).getLast4();
          //  card_brand = arrayList.get(position).getBrand();
          listener.onCardClick(arrayList.get(position).getId(),arrayList.get(position).getCustomer());

        }
    });
    holder.binding.updateCard.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            context.startActivity( new Intent(context, UpdateMemberCard.class)
                    .putExtra("cardnumber_str", arrayList.get(position).getSetfullcardnumber())
                    .putExtra("cardholder_name", arrayList.get(position).getCard_name())
                    .putExtra("expmonth", arrayList.get(position).getExp_month())
                    .putExtra("expyear", arrayList.get(position).getExp_year())
                    .putExtra("card_id", arrayList.get(position).getId())
                    .putExtra("customer_id", arrayList.get(position).getCustomer()));
        }
    });

}

@Override
public int getItemCount() {
    return arrayList.size();
}

public class MyViewHolder extends RecyclerView.ViewHolder {
    ItemCusCardBinding binding;
    public MyViewHolder(@NonNull ItemCusCardBinding itemView) {
        super(itemView.getRoot());
        binding = itemView;

    }
}
}





