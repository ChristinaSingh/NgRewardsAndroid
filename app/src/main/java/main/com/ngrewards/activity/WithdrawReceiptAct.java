package main.com.ngrewards.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import main.com.ngrewards.R;
import main.com.ngrewards.constant.MySession;

public class WithdrawReceiptAct extends AppCompatActivity {
    private TextView  order_id,date_tv,type_head,total_amt_tv,ngcashredeem;
    RelativeLayout backlay;

    private MySession mySession;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_receipt);
        mySession = new MySession(this);

        initViews();
    }

    private void initViews() {
        order_id = findViewById(R.id.order_id);
        date_tv = findViewById(R.id.date_tv);
        type_head = findViewById(R.id.type_head);
        total_amt_tv = findViewById(R.id.total_amt_tv);
        ngcashredeem = findViewById(R.id.ngcashredeem);
        backlay = findViewById(R.id.backlay);


        if(getIntent()!=null){
            order_id.setText("#"+getIntent().getStringExtra("orderId"));
            date_tv.setText( getString(R.string.date1)+" :-"+getIntent().getStringExtra("dateTime"));
            type_head.setText( getString(R.string.transfer_of)+" "+mySession.getValueOf(MySession.CurrencySign)+getIntent().getStringExtra("total") + " " + getString(R.string.to_stripe_bank_account));
            total_amt_tv.setText(getString(R.string.total)+ " :-" + mySession.getValueOf(MySession.CurrencySign) +getIntent().getStringExtra("total"));
            ngcashredeem.setText(getString(R.string.ngcashredeem) + " :-" + mySession.getValueOf(MySession.CurrencySign) +getIntent().getStringExtra("originalAmt"));

        }



        backlay.setOnClickListener(v -> finish());

    }
}
