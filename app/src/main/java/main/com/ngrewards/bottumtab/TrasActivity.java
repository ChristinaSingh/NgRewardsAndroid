package main.com.ngrewards.bottumtab;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import main.com.ngrewards.R;
import main.com.ngrewards.Utils.LocaleHelper;
import main.com.ngrewards.Utils.Tools;
import main.com.ngrewards.activity.MyDividerItemDecoration;
import main.com.ngrewards.activity.PreferenceConnector;
import main.com.ngrewards.activity.PurchasedItemDetailAct;
import main.com.ngrewards.activity.WebViewCalled;
import main.com.ngrewards.activity.WithdrawReceiptAct;
import main.com.ngrewards.beanclasses.OrderAct;
import main.com.ngrewards.beanclasses.OrderBean;
import main.com.ngrewards.constant.MySession;
import main.com.ngrewards.placeorderclasses.ReceiptActivity;
import main.com.ngrewards.placeorderclasses.TransferRequestDetActivity;
import main.com.ngrewards.restapi.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrasActivity extends Fragment {
    Context mContext;
    ActivityRecAdp activityRecAdp;
    View root;
    private RecyclerView activity_list;
    private MySession mySession;
    private String user_id = "",filterType="";
    private ArrayList<OrderBean> orderBeanArrayList;
    private SwipeRefreshLayout swipeToRefresh;
    private String order_cart_id;
    private EditText search_et_home;
    private TextView tvPayBill,tvItems,tvTransfer,tvPayouts,tvPlans;




    private String craete_profile;

    protected void attachBaseContext(Context base) {
        super.onAttach(LocaleHelper.onAttach(base));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_tras, container, false);
        Tools.reupdateResources(requireActivity());
        mySession = new MySession(requireActivity());
        mContext = requireContext();
        String user_log_data = mySession.getKeyAlldata();

        if (user_log_data == null) {

        } else {

            try {
                JSONObject jsonObject = new JSONObject(user_log_data);
                String message = jsonObject.getString("status");
                if (message.equalsIgnoreCase("1")) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                    user_id = jsonObject1.getString("id");
                    Toast.makeText(requireActivity(), user_id, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        idinits();
        clickevent();
        idinitui1();
        return root;
    }

    private void idinitui1() {

        orderBeanArrayList = new ArrayList<>();

        craete_profile = PreferenceConnector.readString(requireActivity(), PreferenceConnector.Create_Profile, "");

        if (!craete_profile.equals("craete_profile")) {
            // dialogSts.dismiss();

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getOrderActivity();
    }

    private void clickevent() {

    }

    private void idinits() {

        swipeToRefresh = root.findViewById(R.id.swipeToRefresh);
        activity_list = root.findViewById(R.id.activity_list);
        SearchManager searchManager = (SearchManager) requireActivity().getSystemService(Context.SEARCH_SERVICE);
        search_et_home = root.findViewById(R.id.search_et_home);
        tvPayBill = root.findViewById(R.id.tvPayBill);
        tvItems = root.findViewById(R.id.tvItems);
        tvTransfer = root.findViewById(R.id.tvTransfer);
        tvPayouts = root.findViewById(R.id.tvPayouts);
        tvPlans = root.findViewById(R.id.tvPlans);


        search_et_home.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activityRecAdp.filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        activity_list.setLayoutManager(horizontalLayoutManagaer);

        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrderActivity();
            }
        });


        tvPayBill.setOnClickListener(v -> {
            filterType = "Paybill";
            activityRecAdp.filterByType(filterType);
        });

        tvItems.setOnClickListener(v -> {
            filterType = "Item";
            activityRecAdp.filterByType(filterType);
        });


        tvTransfer.setOnClickListener(v -> {
            filterType = "Transfer";
           // Log.e("challa===",orderBeanArrayList.size()+"");
            activityRecAdp.filterByType(filterType);
        });


        tvPayouts.setOnClickListener(v -> {
            filterType = "order";
           activityRecAdp.filterByType(filterType);
        });

        tvPlans.setOnClickListener(v -> {
            filterType = "membership_purchase";
            activityRecAdp.filterByType(filterType);
        });






    }

    private void getOrderActivity() {
        try {


            //   Log.e("user_idd", user_id);

            swipeToRefresh.setRefreshing(true);
            Call<ResponseBody> call = ApiClient.getApiInterface().getMemberOrder(user_id);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()) {

                        swipeToRefresh.setRefreshing(false);

                        try {

                            String responseData = response.body().string();
                            JSONObject object = new JSONObject(responseData);

                            if (object.getString("status").equals("1")) {
                                OrderAct successData = new Gson().fromJson(responseData, OrderAct.class);
                                orderBeanArrayList.clear();
                                orderBeanArrayList.addAll(successData.getResult());
                            }

                            Log.e("responseDataaaaa >> ", " >> " + responseData);
                            activityRecAdp = new ActivityRecAdp(requireActivity(), orderBeanArrayList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(requireActivity());
                            activity_list.addItemDecoration(new MyDividerItemDecoration(requireActivity(),
                                    DividerItemDecoration.VERTICAL, 36));
                            activity_list.setLayoutManager(mLayoutManager);
                            activity_list.setItemAnimator(new DefaultItemAnimator());
                            activity_list.setAdapter(activityRecAdp);
                            activityRecAdp.notifyDataSetChanged();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {

                        swipeToRefresh.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("TAG", t.toString());
                }
            });
        } catch (Exception e) {
        }
    }

    public class ActivityRecAdp extends RecyclerView.Adapter<ActivityRecAdp.MyViewHolder> {
        String OrderTime;
        Context context;
        ArrayList<OrderBean> orderBeanArrayList11;
        ArrayList<OrderBean> searchmerchantListBeanArrayList;
        MySession mySession;

        public ActivityRecAdp(Context myContacts, ArrayList<OrderBean> orderBeanArrayList) {
            this.context = myContacts;
            this.orderBeanArrayList11 = orderBeanArrayList;
            this.searchmerchantListBeanArrayList = new ArrayList<>();
            searchmerchantListBeanArrayList.addAll(orderBeanArrayList11);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_activity_item_lay, parent, false);
            MyViewHolder holder = new MyViewHolder(itemView);
            return holder;
        }

        public void filter(String charText) {

            if (charText != null) {

                orderBeanArrayList11.clear();

                if (charText.isEmpty()) {

                    orderBeanArrayList11.addAll(searchmerchantListBeanArrayList);

                } else {

                    charText = charText.toLowerCase(Locale.getDefault());

                    for (OrderBean wp : searchmerchantListBeanArrayList) {

                        try {

                            if (wp.getSearch_id().toLowerCase().startsWith(charText) ||
                                    wp.getB_name().toLowerCase().startsWith(charText) ||
                                    wp.getTotal_amount().toLowerCase().startsWith(charText) ||
                                    wp.getCreated_date().toLowerCase().startsWith(charText) || wp.getMemberDetail().get(0).getB_name().toLowerCase().startsWith(charText)) {
                                orderBeanArrayList11.add(wp);

                            }

                        } catch (Exception e3) {

                            e3.printStackTrace();
                        }

                        notifyDataSetChanged();
                    }
                }

                notifyDataSetChanged();
            }
        }

        public void filterByType(String typeText) {
            if (typeText != null) {

                orderBeanArrayList11.clear();

                if (typeText.isEmpty()) {
                    // Show all items when input is empty
                    orderBeanArrayList11.addAll(searchmerchantListBeanArrayList);
                } else {

                    typeText = typeText.toLowerCase(Locale.getDefault());
                    Log.e("challll===",typeText);
                    Log.e("challll===",searchmerchantListBeanArrayList.size()+"");

                    for (OrderBean wp : searchmerchantListBeanArrayList) {
                        try {
                            if (wp.getType() != null && wp.getType().toLowerCase(Locale.getDefault()).equals(typeText)) {
                                orderBeanArrayList11.add(wp);
                                Log.e("challll===","");
                            }
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                }

                notifyDataSetChanged();
            }
        }




        @Override
        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            Log.e("TAG", "onBindViewHolder:orderBeanArrayList11.get(position).getType" + orderBeanArrayList11.get(position).getType());
            mySession = new MySession(context);
            if (orderBeanArrayList11.get(position).getType() != null &&
                    orderBeanArrayList11.get(position).getType().equalsIgnoreCase("Paybill")) {
                holder.total_order_price.setText(mySession.getValueOf(MySession.CurrencySign)
                        + orderBeanArrayList11.get(position).getTotal_amount());
                holder.total_order_price.setTextColor(getResources().getColor(R.color.black));
                holder.order_id.setText(orderBeanArrayList11.get(position).getSearch_id());
                holder.order_category.setText("" + orderBeanArrayList11.get(position).getType());
                holder.date_tv.setText(orderBeanArrayList11.get(position).getCreated_date());
           /*     try {
                    Log.e(TAG, "onBindViewHolder: ", );
                    String mytime = orderBeanArrayList.get(position).getCreated_date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date myDate = null;
                    myDate = dateFormat.parse(mytime);

                    SimpleDateFormat timeFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
                    String finalDate = timeFormat.format(myDate);

                    holder.date_tv.setText("Date:- " + mytime);
                    holder.time_tv.setText("Time :- " + orderBeanArrayList.get(position).getOrder_Time());
                    System.out.println(finalDate);

                } catch (Exception e) {
                    Log.e("EXC TRUE", " RRR");
                    holder.date_tv.setText("Date:- " + orderBeanArrayList.get(position).getCreated_date());
                    holder.time_tv.setText("Time :- " + orderBeanArrayList.get(position).getOrder_Time());
                }*/

                holder.paidamount_bycard.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList11.get(position).getPaid_by_card());
                if (orderBeanArrayList11.get(position).getNgcash() == null || orderBeanArrayList11.get(position).getNgcash().equalsIgnoreCase("0") || orderBeanArrayList11.get(position).getNgcash().equalsIgnoreCase("")) {
                    holder.ngcash.setText(mySession.getValueOf(MySession.CurrencySign) + "0.00");
                } else {
                    holder.ngcash.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList11.get(position).getNgcash());
                }

                //String mername = orderBeanArrayList.get(position).getMerchantDetail().get(0).getBusinessName();
                String mername = orderBeanArrayList11.get(position).getB_name();

                if (mername == null || mername.equalsIgnoreCase("")) {
                    holder.merchant_member_name.setText("" + getResources().getString(R.string.staticmerchantname));

                } else {

                    holder.merchant_member_name.setText("" + mername);
                }

            }
            else if (orderBeanArrayList11.get(position).getType() != null &&
                    orderBeanArrayList11.get(position).getType().equalsIgnoreCase("Transfer")) {

                holder.total_order_price.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList11.get(position).getAmount());

                if (orderBeanArrayList11.get(position).getTransferRequestUserId().equalsIgnoreCase(user_id)) {
                    holder.total_order_price.setTextColor(getResources().getColor(R.color.green));
                } else {
                    holder.total_order_price.setTextColor(getResources().getColor(R.color.red));
                }

                holder.order_id.setText(orderBeanArrayList11.get(position).getSearch_id());
                holder.order_category.setText("" + orderBeanArrayList11.get(position).getType());

                try {
                    String mytime = orderBeanArrayList11.get(position).getCreated_date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date myDate = null;
                    myDate = dateFormat.parse(mytime);

                    SimpleDateFormat timeFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
                    String finalDate = timeFormat.format(myDate);
                    holder.date_tv.setText("Date:- " + mytime);
                    holder.time_tv.setText("Time :- " + orderBeanArrayList11.get(position).getOrder_Time());
                    System.out.println(finalDate);

                } catch (Exception e) {
                    Log.e("EXC TRUE", " RRR");
                    holder.date_tv.setText("Date:- " + orderBeanArrayList11.get(position).getCreated_date());
                    holder.time_tv.setText("Time :- " + orderBeanArrayList11.get(position).getOrder_Time());
                }

                holder.paidamount_bycard.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList11.get(position).getAmount_by_card());

                if (orderBeanArrayList11.get(position).getNgcash() == null || orderBeanArrayList11.get(position).getNgcash().equalsIgnoreCase("0") ||
                        orderBeanArrayList11.get(position).getNgcash().equalsIgnoreCase("")) {
                    holder.ngcash.setText(mySession.getValueOf(MySession.CurrencySign) + "0.00");

                } else {
                    holder.ngcash.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList11.get(position).getNgcash());
                }
                String mername = orderBeanArrayList11.get(position).getB_name();

                if (mername == null || mername.equalsIgnoreCase("")) {
                    holder.merchant_member_name.setText("");
                    // holder.merchant_member_name.setText("" + getResources().getString(R.string.staticmerchantname));
                } else {
                    holder.merchant_member_name.setText("" + mername);
                }

            }


            else if (orderBeanArrayList11.get(position).getType() != null &&
                    orderBeanArrayList11.get(position).getType().equalsIgnoreCase("ngcash_withdraw")) {

                holder.merchant_member_name.setText(getString(R.string.withdraw_ng_cash));
                holder.total_order_price.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList11.get(position).getAmount());
                holder.order_id.setText("WN"+orderBeanArrayList11.get(position).getId());
                holder.paidamount_bycard.setText(getString(R.string.wallet));
                holder.ngcash.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList11.get(position).getAmount());
                holder.order_category.setText(getString(R.string.withdraw));

                //holder.order_category.setText("" + orderBeanArrayList.get(position).getType());

                try {
                    String mytime = orderBeanArrayList11.get(position).getCreated_date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date myDate = null;
                    myDate = dateFormat.parse(mytime);

                    SimpleDateFormat timeFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
                    String finalDate = timeFormat.format(myDate);
                    holder.date_tv.setText("Date:- " + mytime);
                    System.out.println(finalDate);

                } catch (Exception e) {
                    Log.e("EXC TRUE", " RRR");
                    holder.date_tv.setText("Date:- " + orderBeanArrayList11.get(position).getCreated_date());
                }

            }


            else if (orderBeanArrayList11.get(position).getType() != null &&
                    orderBeanArrayList11.get(position).getType().equalsIgnoreCase("Order")) {
                //holder.total_order_price.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList.get(position).getTotal_amount());
                holder.total_order_price.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList11.get(position).getTotal_amount());
                holder.order_id.setText(orderBeanArrayList11.get(position).getSearch_id());
                holder.order_category.setText("" + orderBeanArrayList11.get(position).getType());
                holder.total_order_price.setTextColor(getResources().getColor(R.color.black));
                try {
                    String mytime = orderBeanArrayList11.get(position).getCreated_date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date myDate = null;
                    myDate = dateFormat.parse(mytime);

                    SimpleDateFormat timeFormat = new SimpleDateFormat("MMM dd, yyyy");
                    String finalDate = timeFormat.format(myDate);
                    holder.date_tv.setText("Date:- " + finalDate + " " + orderBeanArrayList11.get(position).getOrder_Time());
                    holder.time_tv.setText("Time :- " + orderBeanArrayList11.get(position).getOrder_Time());
                    System.out.println(finalDate);

                } catch (Exception e) {
                    Log.e("EXC TRUE", " RRR");
                    holder.date_tv.setText("Date:- " + orderBeanArrayList11.get(position).getCreated_date() + " " + orderBeanArrayList11.get(position).getOrder_Time());
                    holder.time_tv.setText("Time :- " + orderBeanArrayList11.get(position).getOrder_Time());
                }

                holder.paidamount_bycard.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList11.get(position).getPaid_by_card());

                if (orderBeanArrayList11.get(position).getNgcash() == null || orderBeanArrayList11.get(position).getNgcash().equalsIgnoreCase("0") || orderBeanArrayList11.get(position).getNgcash().equalsIgnoreCase("")) {
                    holder.ngcash.setText(mySession.getValueOf(MySession.CurrencySign) + "0.00");
                } else {
                    holder.ngcash.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList11.get(position).getNgcash());
                }

                String mername = orderBeanArrayList11.get(position).getB_name();

                if (mername == null || mername.equalsIgnoreCase("")) {
                    holder.merchant_member_name.setText("");
                } else {
                    holder.merchant_member_name.setText("" + mername);
                }

            }

            else if (orderBeanArrayList11.get(position).getType() != null &&
                    orderBeanArrayList11.get(position).getType().equalsIgnoreCase("membership_purchase")) {

                holder.merchant_member_name.setText(orderBeanArrayList11.get(position).getPlanDisplayName());
                holder.total_order_price.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList11.get(position).getAmount());
                holder.order_id.setText("MN"+orderBeanArrayList11.get(position).getId());
             //   holder.paidamount_bycard.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList11.get(position).getAmount());
              //  holder.ngcash.setText(mySession.getValueOf(MySession.CurrencySign) + "0.00");



                if (orderBeanArrayList11.get(position).getStripe_amount() == null || orderBeanArrayList11.get(position).getStripe_amount().equalsIgnoreCase("0") || orderBeanArrayList11.get(position).getStripe_amount().equalsIgnoreCase("")) {
                    //holder.ngcash.setText(mySession.getValueOf(MySession.CurrencySign) + "0.00");
                    holder.paidamount_bycard.setText(mySession.getValueOf(MySession.CurrencySign) + "0.00");

                } else {
                    holder.paidamount_bycard.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList11.get(position).getStripe_amount());

                }





                if (orderBeanArrayList11.get(position).getNgCash22() == null || orderBeanArrayList11.get(position).getNgCash22().equalsIgnoreCase("0") || orderBeanArrayList11.get(position).getNgCash22().equalsIgnoreCase("")) {
                    holder.ngcash.setText(mySession.getValueOf(MySession.CurrencySign) + "0.00");
                } else {
                    Double amount = Double.parseDouble(orderBeanArrayList11.get(position).getNgCash22());

                    String formattedAmount = String.format("%05.2f", amount);

                    holder.ngcash.setText(mySession.getValueOf(MySession.CurrencySign) + formattedAmount);
                }



                holder.order_category.setText(getString(R.string.subscription));

                //holder.order_category.setText("" + orderBeanArrayList.get(position).getType());

                holder.date_tv.setText("Date:- " + orderBeanArrayList11.get(position).getCreatedAt());


            }



            else {

                holder.total_order_price.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList11.get(position).getTotal_amount());
                holder.order_id.setText(orderBeanArrayList11.get(position).getSearch_id());
                holder.order_category.setText("" + orderBeanArrayList11.get(position).getType());

                try {

                    String mytime = orderBeanArrayList11.get(position).getCreated_date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date myDate = null;
                    myDate = dateFormat.parse(mytime);

                    SimpleDateFormat timeFormat = new SimpleDateFormat("MMM dd, yyyy");
                    String finalDate = timeFormat.format(myDate);
                    if (orderBeanArrayList11.get(position).getOrder_Time() != null) {
                        holder.date_tv.setText("Date:- " + finalDate + " " + orderBeanArrayList11.get(position).getOrder_Time());

                    } else {
                        //  holder.date_tv.setText("Date:- " + finalDate +  " " +position+":00"+":"+
                        //      "00");
                        holder.date_tv.setText("" + orderBeanArrayList11.get(position).getOrderDate());
                    }
                    holder.time_tv.setText("Time :- " + orderBeanArrayList11.get(position).getOrder_Time());

                    System.out.println(finalDate);

                } catch (Exception e) {
                    Log.e("EXC TRUE", " RRR");
                    if (orderBeanArrayList11.get(position).getOrder_Time() != null) {
                        holder.date_tv.setText("Date:- " + orderBeanArrayList11.get(position).getCreated_date() + " " + orderBeanArrayList11.get(position).getOrder_Time());

                    } else {
                        holder.date_tv.setText("Date:- " + orderBeanArrayList11.get(position).getCreated_date() + " " + position + ":00" + ":" + "00");
                    }
                    holder.time_tv.setText("Time :- " + orderBeanArrayList11.get(position).getOrder_Time());
                }

                holder.total_order_price.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList11.get(position).getTotal_price_with_shipping());

                holder.total_order_price.setTextColor(getResources().getColor(R.color.black));
                holder.paidamount_bycard.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList11.get(position).getPaid_by_card());
                if (orderBeanArrayList11.get(position).getNgcash() == null || orderBeanArrayList11.get(position).getNgcash().equalsIgnoreCase("0")) {
                    holder.ngcash.setText(mySession.getValueOf(MySession.CurrencySign) + "0.00");
                } else {
                    holder.ngcash.setText(mySession.getValueOf(MySession.CurrencySign) + orderBeanArrayList11.get(position).getNgcash());
                }

                String mername = orderBeanArrayList11.get(position).getB_name();
                holder.merchant_member_name.setText("" + mername);
            }

            holder.img_plus.setOnClickListener(v -> {
                holder.lesslay.setVisibility(View.VISIBLE);
                holder.img_plus.setVisibility(View.GONE);
                holder.img_minus.setVisibility(View.VISIBLE);
            });

            holder.img_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.lesslay.setVisibility(View.GONE);
                    holder.img_plus.setVisibility(View.VISIBLE);
                    holder.img_minus.setVisibility(View.GONE);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                private String getSearch_id;

                @Override
                public void onClick(View v) {

                    Log.e("TYPE >> ", " >> " + orderBeanArrayList11.get(position).getType());
                    Log.e("TYPE >> ", " >> orderBeanArrayList.get(position).getOrder_cart_id()---------" + orderBeanArrayList11.get(position).getOrder_cart_id());

                    if (orderBeanArrayList11.get(position).getType() != null
                            && orderBeanArrayList11.get(position).getType().equalsIgnoreCase("Paybill")) {

                        order_cart_id = orderBeanArrayList11.get(position).getOrder_cart_id();
                        OrderTime = orderBeanArrayList11.get(position).getNgcash();

                        Toast.makeText(requireActivity(), "empnme" + orderBeanArrayList11.get(position).getEmployeeName(), Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(requireActivity(), ReceiptActivity.class);

                        orderBeanArrayList11.get(position).getMerchantDetail().get(0).getBusinessName();

                        Log.e("getBusinessName", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getAffiliateName());
                        i.putExtra("merchant_name", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getBusinessName());
                        i.putExtra("member_name", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getBusinessNo());
                        i.putExtra("merchant_id", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getId());
                        i.putExtra("merchant_number", orderBeanArrayList11.get(position).getMerchant_no());
                        i.putExtra("merchant_contact_name", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getContactName());
                        i.putExtra("address", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getAddress());
                        i.putExtra("address_2", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getAddressTwo());
                        i.putExtra("merchant_img_str", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getMerchantImage());
                        i.putExtra("date_tv", orderBeanArrayList11.get(position).getCreated_date());
                        i.putExtra("order_id", "" + orderBeanArrayList11.get(position).getId());
                        i.putExtra("cardnumber_tv", "" + orderBeanArrayList11.get(position).getCardNumber());
                        i.putExtra("cardbrand", "" + orderBeanArrayList11.get(position).getCardBrand());
                        i.putExtra("total_amt_tv_str", "" + orderBeanArrayList11.get(position).getTotal_amount());
                        i.putExtra("due_amt_tv_str", "" + orderBeanArrayList11.get(position).getAmount());
                        i.putExtra("ngcash_str", "" + orderBeanArrayList11.get(position).getNgcash());
                        i.putExtra("tip_str", "" + orderBeanArrayList11.get(position).getTip_amount());
                        i.putExtra("employee_name", "" + orderBeanArrayList11.get(position).getEmployeeName());
                        i.putExtra("mdate", "" + orderBeanArrayList11.get(position).getOrderDate2());
                        i.putExtra("time", "" + orderBeanArrayList11.get(position).getOrder_Time());
                        i.putExtra("Order_guset_No", "" + orderBeanArrayList11.get(position).getOrder_guset_No());
                        i.putExtra("Order_Table_No", "" + orderBeanArrayList11.get(position).getOrder_Table_No());
                        i.putExtra("reciept_url", "" + orderBeanArrayList11.get(position).getReciept_url());
                        i.putExtra("order_special", "" + orderBeanArrayList11.get(position).getOrder_special_request());
                        i.putExtra("order_cart_id", "" + orderBeanArrayList11.get(position).getOrder_cart_id());
                        i.putExtra("type123", orderBeanArrayList11.get(position).getType());
                        startActivity(i);

                    }
                    else if (orderBeanArrayList11.get(position).getType() != null &&
                            orderBeanArrayList11.get(position).getType().equalsIgnoreCase("Transfer")) {
                        Intent i = new Intent(requireActivity(), TransferRequestDetActivity.class);
                        i.putExtra("member_id", orderBeanArrayList11.get(position).getMember_id());
                        i.putExtra("reciept_url", orderBeanArrayList11.get(position).getReciept_url());
                        i.putExtra("amount_trans_by_card", orderBeanArrayList11.get(position).getAmount_by_card());
                        i.putExtra("total_amt_tv_str", "" + orderBeanArrayList11.get(position).getAmount());
                        i.putExtra("due_amt_tv_str", "" + orderBeanArrayList11.get(position).getAmount());
                        i.putExtra("comment", orderBeanArrayList11.get(position).getComment());
                        i.putExtra("transfer_request_user_id", orderBeanArrayList11.get(position).getTransferRequestUserId());
                        i.putExtra("type", "" + orderBeanArrayList11.get(position).getType());
                        i.putExtra("date_tv", orderBeanArrayList11.get(position).getCreated_date());
                        i.putExtra("ngcash_str", "" + orderBeanArrayList11.get(position).getNgcash());
                        startActivity(i);

                    }
                    else if (orderBeanArrayList11.get(position).getType() != null &&
                            orderBeanArrayList11.get(position).getType().equalsIgnoreCase("ngcash_withdraw")) {
                        Intent i = new Intent(requireActivity(), WithdrawReceiptAct.class);
                        i.putExtra("orderId", orderBeanArrayList11.get(position).getId());
                        i.putExtra("total", "" + orderBeanArrayList11.get(position).getAmount());
                        i.putExtra("originalAmt", "" + orderBeanArrayList11.get(position).getReqAmount());
                        i.putExtra("dateTime", orderBeanArrayList11.get(position).getCreated_date());
                        startActivity(i);

                    }

                    else if (orderBeanArrayList11.get(position).getType() != null &&
                            orderBeanArrayList11.get(position).getType().equalsIgnoreCase("Request")) {
                        Intent i = new Intent(requireActivity(), TransferRequestDetActivity.class);
                        i.putExtra("member_user_name", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getAffiliateName());
                        i.putExtra("member_id", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getId());
                        i.putExtra("member_fullname_number", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getFullname());
                        i.putExtra("comment", orderBeanArrayList11.get(position).getComment());
                        i.putExtra("member_img_str", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getMerchantImage());
                        i.putExtra("date_tv", orderBeanArrayList11.get(position).getCreated_date() + " " + orderBeanArrayList11.get(position).getOrder_Time());
                        i.putExtra("member_name", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getBusinessNo());
                        i.putExtra("order_id", "" + orderBeanArrayList11.get(position).getId());
                        i.putExtra("cardnumber_tv", "" + orderBeanArrayList11.get(position).getCardNumber());
                        i.putExtra("cardbrand", "" + orderBeanArrayList11.get(position).getCardBrand());
                        i.putExtra("total_amt_tv_str", "" + orderBeanArrayList11.get(position).getAmount());
                        i.putExtra("due_amt_tv_str", "" + orderBeanArrayList11.get(position).getAmount());
                        i.putExtra("ngcash_str", "" + orderBeanArrayList11.get(position).getNgcash());
                        i.putExtra("tip_str", "" + orderBeanArrayList11.get(position).getTip_amount());
                        i.putExtra("type", "" + orderBeanArrayList11.get(position).getType());
                        i.putExtra("mdate", "" + orderBeanArrayList11.get(position).getOrderDate2());
                        i.putExtra("time", "" + orderBeanArrayList11.get(position).getOrder_Time());
                        i.putExtra("Order_guset_No", "" + orderBeanArrayList11.get(position).getOrder_guset_No());
                        i.putExtra("Order_Table_No", "" + orderBeanArrayList11.get(position).getOrder_Table_No());
                        i.putExtra("reciept_url", "" + orderBeanArrayList11.get(position).getReciept_url());
                        i.putExtra("order_special", "" + orderBeanArrayList11.get(position).getOrder_special_request());
                        i.putExtra("employee_name", "" + orderBeanArrayList11.get(position).getEmployeeName());
                        startActivity(i);

                    }
                    else if (orderBeanArrayList11.get(position).getType() != null &&
                            orderBeanArrayList11.get(position).getType().equalsIgnoreCase("Order")) {


                        order_cart_id = orderBeanArrayList11.get(position).getOrder_special_request();

                        Log.e("orderarraylist123", "" + orderBeanArrayList11);
                        Toast.makeText(requireActivity(), "" + order_cart_id, Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(requireActivity(), ReceiptActivity.class);
                        i.putExtra("merchant_name", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getBusinessName());
                        i.putExtra("merchant_id", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getId());
                        i.putExtra("member_name", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getBusinessNo());
                        i.putExtra("merchant_number", orderBeanArrayList11.get(position).getMerchant_no());
                        i.putExtra("merchant_contact_name", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getContactName());
                        i.putExtra("address", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getAddress());
                        i.putExtra("address_2", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getAddressTwo());
                        i.putExtra("merchant_img_str", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getMerchantImage());
                        i.putExtra("date_tv", orderBeanArrayList11.get(position).getCreated_date() + " " + orderBeanArrayList11.get(position).getOrder_Time());
                        i.putExtra("order_id", "" + orderBeanArrayList11.get(position).getId());
                        i.putExtra("cardnumber_tv", "" + orderBeanArrayList11.get(position).getCardNumber());
                        i.putExtra("cardbrand", "" + orderBeanArrayList11.get(position).getCardBrand());
                        i.putExtra("total_amt_tv_str", "" + orderBeanArrayList11.get(position).getTotal_amount());
                        i.putExtra("due_amt_tv_str", "" + orderBeanArrayList11.get(position).getAmount());
                        i.putExtra("ngcash_str", "" + orderBeanArrayList11.get(position).getNgcash());
                        i.putExtra("tip_str", "" + orderBeanArrayList11.get(position).getTip_amount());
                        i.putExtra("employee_name", "" + orderBeanArrayList11.get(position).getEmployeeName());
                        i.putExtra("mdate", "" + orderBeanArrayList11.get(position).getOrderDate2());
                        i.putExtra("time", "" + orderBeanArrayList11.get(position).getOrder_Time());
                        i.putExtra("Order_guset_No", "" + orderBeanArrayList11.get(position).getOrder_guset_No());
                        i.putExtra("Order_Table_No", "" + orderBeanArrayList11.get(position).getOrder_Table_No());
                        i.putExtra("reciept_url", "" + orderBeanArrayList11.get(position).getReciept_url());
                        i.putExtra("order_special", "" + orderBeanArrayList11.get(position).getOrder_special_request());
                        i.putExtra("order_cart_id", "" + orderBeanArrayList11.get(position).getOrder_cart_id());
                        i.putExtra("type123", orderBeanArrayList11.get(position).getType());
                        startActivity(i);

                    }

                    else if (orderBeanArrayList11.get(position).getType() != null &&
                            orderBeanArrayList11.get(position).getType().equalsIgnoreCase("membership_purchase")) {

                        if(orderBeanArrayList11.get(position).getReceiptUrl()!=null){
                            context.startActivity(new Intent(context, WebViewCalled.class)
                                    .putExtra("reciept_url", orderBeanArrayList11.get(position).getReceiptUrl()));
                        }

                       /* order_cart_id = orderBeanArrayList.get(position).getOrder_special_request();

                        Log.e("orderarraylist123", "" + orderBeanArrayList);
                        Toast.makeText(requireActivity(), "" + order_cart_id, Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(requireActivity(), ReceiptActivity.class);
                        i.putExtra("merchant_name", orderBeanArrayList.get(position).getMerchantDetail().get(0).getBusinessName());
                        i.putExtra("merchant_id", orderBeanArrayList.get(position).getMerchantDetail().get(0).getId());
                        i.putExtra("member_name", orderBeanArrayList.get(position).getMerchantDetail().get(0).getBusinessNo());
                        i.putExtra("merchant_number", orderBeanArrayList.get(position).getMerchant_no());
                        i.putExtra("merchant_contact_name", orderBeanArrayList.get(position).getMerchantDetail().get(0).getContactName());
                        i.putExtra("address", orderBeanArrayList.get(position).getMerchantDetail().get(0).getAddress());
                        i.putExtra("address_2", orderBeanArrayList.get(position).getMerchantDetail().get(0).getAddressTwo());
                        i.putExtra("merchant_img_str", orderBeanArrayList.get(position).getMerchantDetail().get(0).getMerchantImage());
                        i.putExtra("date_tv", orderBeanArrayList.get(position).getCreated_date() + " " + orderBeanArrayList.get(position).getOrder_Time());
                        i.putExtra("order_id", "" + orderBeanArrayList.get(position).getId());
                        i.putExtra("cardnumber_tv", "" + orderBeanArrayList.get(position).getCardNumber());
                        i.putExtra("cardbrand", "" + orderBeanArrayList.get(position).getCardBrand());
                        i.putExtra("total_amt_tv_str", "" + orderBeanArrayList.get(position).getTotal_amount());
                        i.putExtra("due_amt_tv_str", "" + orderBeanArrayList.get(position).getAmount());
                        i.putExtra("ngcash_str", "" + orderBeanArrayList.get(position).getNgcash());
                        i.putExtra("tip_str", "" + orderBeanArrayList.get(position).getTip_amount());
                        i.putExtra("employee_name", "" + orderBeanArrayList.get(position).getEmployeeName());
                        i.putExtra("mdate", "" + orderBeanArrayList.get(position).getOrderDate2());
                        i.putExtra("time", "" + orderBeanArrayList.get(position).getOrder_Time());
                        i.putExtra("Order_guset_No", "" + orderBeanArrayList.get(position).getOrder_guset_No());
                        i.putExtra("Order_Table_No", "" + orderBeanArrayList.get(position).getOrder_Table_No());
                        i.putExtra("reciept_url", "" + orderBeanArrayList.get(position).getReciept_url());
                        i.putExtra("order_special", "" + orderBeanArrayList.get(position).getOrder_special_request());
                        i.putExtra("order_cart_id", "" + orderBeanArrayList.get(position).getOrder_cart_id());
                        i.putExtra("type123", orderBeanArrayList.get(position).getType());
                        startActivity(i);*/

                    }

                    else {

                        Intent i = new Intent(requireActivity(), PurchasedItemDetailAct.class);
                        i.putExtra("product_name", orderBeanArrayList11.get(position).getProductName());
                        i.putExtra("size", orderBeanArrayList11.get(position).getSize());
                        i.putExtra("color", orderBeanArrayList11.get(position).getColor());
                        i.putExtra("quantity", orderBeanArrayList11.get(position).getQuantity());
                        i.putExtra("member_name", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getBusinessNo());
                        i.putExtra("merchant_name", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getBusinessName());
                        i.putExtra("merchant_contact_name",
                                orderBeanArrayList11.get(position).getMerchantDetail().get(0).getBusinessNo());
                        i.putExtra("merchant_img_str", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getMerchantImage());
                        i.putExtra("mainprice", orderBeanArrayList11.get(position).getTotalproductprice());
                        i.putExtra("order_id", "" + orderBeanArrayList11.get(position).getOrderId());
                        i.putExtra("saledate", orderBeanArrayList11.get(position).getOrderDate());
                        i.putExtra("upspackage", orderBeanArrayList11.get(position).getDeliveryDate());
                        i.putExtra("shipaddress_1", orderBeanArrayList11.get(position).getShippingAddress1());
                        i.putExtra("shipaddress_2", orderBeanArrayList11.get(position).getShippingAddress2());
                        i.putExtra("shipping_name", orderBeanArrayList11.get(position).getShippingFirstName());
                        i.putExtra("shipping_price", orderBeanArrayList11.get(position).getShipping_price());
                        i.putExtra("merchant_id", orderBeanArrayList11.get(position).getMerchantDetail().get(0).getId());
                        i.putExtra("product_id", orderBeanArrayList11.get(position).getId());
                        i.putExtra("product_img_str", orderBeanArrayList11.get(position).getThumbnailImage());
                        i.putExtra("average_rating", orderBeanArrayList11.get(position).getAverage_rating());
                        i.putExtra("review", orderBeanArrayList11.get(position).getReview());
                        i.putExtra("review_status", orderBeanArrayList11.get(position).getReviewstatus());
                        i.putExtra("mdate", "" + orderBeanArrayList11.get(position).getOrderDate2());
                        i.putExtra("time", "" + orderBeanArrayList11.get(position).getOrder_Time());
                        i.putExtra("employee_name", "" + orderBeanArrayList11.get(position).getEmployeeName());
                        i.putExtra("Order_guset_No", "" + orderBeanArrayList11.get(position).getOrder_guset_No());
                        i.putExtra("Order_Table_No", "" + orderBeanArrayList11.get(position).getOrder_Table_No());
                        i.putExtra("reciept_url", "" + orderBeanArrayList11.get(position).getReciept_url());
                        i.putExtra("order_special", "" + orderBeanArrayList11.get(position).getOrder_special_request());
                        i.putExtra("split_invoice", "" + orderBeanArrayList11.get(position).getSplit_invoice());
                        i.putExtra("split_date", "" + orderBeanArrayList11.get(position).getSplit_date());
                        i.putExtra("payment_made_by_emi", "" + orderBeanArrayList11.get(position).getPayment_made_by_emi());
                        i.putExtra("split_payment", "" + orderBeanArrayList11.get(position).getSplit_payment());
                        i.putExtra("split_amount", "" + orderBeanArrayList11.get(position).getSplit_amount());
                        i.putExtra("cart_id", "" + orderBeanArrayList11.get(position).getCart_id());

                        startActivity(i);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {

            return orderBeanArrayList11.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public RelativeLayout backlay;
            LinearLayout adapter_transaction_linear;
            ImageView img_plus, img_minus;
            LinearLayout lesslay, click_plus_minus;
            TextView order_category, merchant_member_name, ngcash, paidamount_bycard, date_tv, time_tv, order_id, total_order_price;

            public MyViewHolder(View view) {
                super(view);
                img_plus = itemView.findViewById(R.id.img_plus);
                order_category = itemView.findViewById(R.id.order_category);
                paidamount_bycard = itemView.findViewById(R.id.paidamount_bycard);
                ngcash = itemView.findViewById(R.id.ngcash);
                date_tv = itemView.findViewById(R.id.date_tv);
                time_tv = itemView.findViewById(R.id.time_tv);
                order_id = itemView.findViewById(R.id.order_id);
                total_order_price = itemView.findViewById(R.id.total_order_price);
                merchant_member_name = itemView.findViewById(R.id.merchant_member_name);
                img_minus = itemView.findViewById(R.id.img_minus);
                lesslay = itemView.findViewById(R.id.lesslay);
                click_plus_minus = itemView.findViewById(R.id.click_plus_minus);
                adapter_transaction_linear = itemView.findViewById(R.id.adapter_transaction_linear);

            }
        }
    }
}
