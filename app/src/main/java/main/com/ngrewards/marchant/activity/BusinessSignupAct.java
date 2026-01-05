package main.com.ngrewards.marchant.activity;

import static main.com.ngrewards.Utils.Tools.ToolsShowDialog;
import static main.com.ngrewards.constant.MySession.KEY_LANGUAGE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import main.com.ngrewards.Models.CountryBeanList;
import main.com.ngrewards.R;
import main.com.ngrewards.Utils.Tools;
import main.com.ngrewards.activity.SplashActivity;
import main.com.ngrewards.beanclasses.CategoryBean;
import main.com.ngrewards.beanclasses.CategoryBeanList;
import main.com.ngrewards.constant.BaseUrl;
import main.com.ngrewards.constant.CountryBean;
import main.com.ngrewards.constant.GPSTracker;
import main.com.ngrewards.constant.MultipartUtility;
import main.com.ngrewards.constant.MySession;
import main.com.ngrewards.constant.Myapisession;
import main.com.ngrewards.drawlocation.MyTask;
import main.com.ngrewards.drawlocation.WebOperations;
import main.com.ngrewards.marchant.MarchantLogin;
import main.com.ngrewards.marchant.merchantbottum.MerchantBottumAct;
import main.com.ngrewards.restapi.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessSignupAct extends AppCompatActivity {
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 0; // in Milliseconds
    private static final int LOCATION_PERMISSION_REQUEST = 101;



    private static final int RC_SIGN_IN = 007;
    private final Integer THRESHOLD = 2;
    GPSTracker gpsTracker;
    CircleImageView merchant_img;
    LocationManager locationManager;
    Location location;
    SignInButton google_signin;
    CountryListAdapter countryListAdapter;
    CategoryAdpters categoryAdpters;
    CountryAdpters countryAdpters;
    View v;
    boolean sts;
    private int count = 0;
    private double longitude = 0.0, latitude = 0.0;
    private GoogleApiClient mGoogleApiClient;
    private EditText businessname, phone_number, address, zipcode,businessEmail;
    private ProgressBar progresbar;
    private ArrayList<CountryBean> countryBeanArrayList, statelistbean, citylistbean;
    private Spinner state_spn, country_spn, city_spn, category_spinner, select_country_spinner;
    private AutoCompleteTextView streetaddress;
    private ArrayList<CategoryBeanList> categoryBeanListArrayList;
    private ArrayList<CountryBeanList> categoryBeanListArrayList2;
    private Myapisession myapisession;
    private MySession mySession;
    private String ImagePath="",firbaseId="",merchantId="",mobilenum_str="",password_str="";
    private String cameraPath;
    private CheckBox check_box;
    private TextView btnUpdate;

    public static String add_merchant_in_member = "NO";
    public static String  bus_category_id = "", selected_country = "", selected_country_name = "", mer_address_two = "", country_str = "", mer_email = "", mer_pass = "", mer_tnc_cheched = "", mer_fullname = "", mer_reward = "6", mer_who_invite = "", mer_image = "", mer_businessname = "", mer_phone_number = "", mer_address = "", mer_city = "", mer_state = "", mer_zipcode = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mer_business);
        if(getIntent()!=null){
            merchantId = getIntent().getStringExtra("merchantId");
        }

       /* locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(BusinessSignupAct.this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            {
                return null;
            }
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATES, MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, new MyLocationListener());
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);*/
        getLocation();
        myapisession = new Myapisession(this);
        mySession = new MySession(this);
        displayFirebaseRegId();

        checkGps();
        idint();
        new GetProfile().execute();
        autocompleteView();
        getCategoryType();
        getCountryList();
    }



    private void getLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Check permission
        if (ActivityCompat.checkSelfPermission(
                BusinessSignupAct.this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                BusinessSignupAct.this,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {

            // Request permission
            ActivityCompat.requestPermissions(
                    BusinessSignupAct.this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    LOCATION_PERMISSION_REQUEST
            );
            return; // IMPORTANT: stop execution
        }

        // Permission GRANTED → safe to access location
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Permission granted → call location again
                getLocation();
            } else {
                // Permission denied
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
/*
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Uri selectedImage = data.getData();
                getPath(selectedImage);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String FinalPath = cursor.getString(columnIndex);
                cursor.close();
                String ImagePath = getPath(selectedImage);

                decodeFile(ImagePath);
            }
        }
*/
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    if (Build.VERSION.SDK_INT >= 33) {
                        if (data == null) return;
                        // Get photo picker response for single select.
                        final Uri selectedImage = data.getData();
                        try {
                            assert selectedImage != null;
                            try (final InputStream stream = getContentResolver().openInputStream(selectedImage)) {
                                final Bitmap bitmap = BitmapFactory.decodeStream(stream);
                                // user_img.setImageBitmap(bitmap);
                                merchant_img.setImageBitmap(bitmap);
                                File tempfile = Tools.persistImage(bitmap, BusinessSignupAct.this);
                                ImagePath = tempfile.getAbsolutePath();
                                Log.e("ImagePath", "onActivityResult: " + ImagePath);
                                MerchantSignupSlider.ImagePath = ImagePath;
                            }
                        } catch (IOException e) {
                            ToolsShowDialog(BusinessSignupAct.this, e.getLocalizedMessage());
                        }
                    } else {
                        try {
                            Uri selectedImage = data.getData();
                            //getPath(selectedImage);
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};
                            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String FinalPath = cursor.getString(columnIndex);
                            cursor.close();
                            ImagePath = getPath(selectedImage);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        decodeFile(ImagePath);
                    }


                    break;

                case 2:

                    try {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        cameraPath = saveToInternalStorage(photo);
                        Log.e("PATH Camera", "" + cameraPath);

                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                    //  String ImagePath = getPath(selectedImage);
                    decodeFile(cameraPath);
                    break;

            }
        }

        //Uri returnUri;
        //returnUri = data.getData();
    }




    private void displayFirebaseRegId() {
        FirebaseApp.initializeApp(BusinessSignupAct.this);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            firbaseId= task.getResult();
           // SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
         //   SharedPreferences.Editor editor = pref.edit();
          //  editor.putString("regId", task.getResult());
          //  editor.commit();
            Log.e("deviceTokenUpdate()=>", "deviceTokenUpdate" +firbaseId);

        });
    }






    private void autocompleteView() {
        Log.e("CALL THIS METH", "INNER");

        streetaddress.setThreshold(THRESHOLD);
        streetaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sts = true;

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s != null && s.length() > 0) {
                    Log.e("CALL THIS LOAD", "INNER");
                    //clear_pick_ic.setVisibility(View.VISIBLE);
                    loadData(streetaddress.getText().toString());
                } else {
                    //clear_pick_ic.setVisibility(View.GONE);
                }
            }
        });
    }

    private void loadData(String s) {
        Log.e("CALL THIS", "OUT");

        try {
            if (count == 0) {
                List<String> l1 = new ArrayList<>();
                if (s == null) {

                } else {
                    Log.e("CALL THIS", "INNER");
                    l1.add(s);
                    sts = true;
                   GeoAutoCompleteAdapter ga = new GeoAutoCompleteAdapter(BusinessSignupAct.this, l1, "" + latitude, "" + longitude);
                    streetaddress.setAdapter(ga);
                    if (ga == null) {

                    } else {
                        ga.notifyDataSetChanged();
                    }

                }

            }
            count++;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void idint() {
        category_spinner = findViewById(R.id.category_spinner);
        select_country_spinner = findViewById(R.id.select_country_spinner);
        streetaddress = findViewById(R.id.streetaddress);
        state_spn = findViewById(R.id.state_spn);
        country_spn = findViewById(R.id.country_spn);
        city_spn = findViewById(R.id.city_spn);
        progresbar = findViewById(R.id.progresbar);
        merchant_img = findViewById(R.id.merchant_img);
        businessname = findViewById(R.id.businessname);
        phone_number = findViewById(R.id.phone_number);
        address = findViewById(R.id.address);
        check_box = findViewById(R.id.check_box);

        businessEmail = findViewById(R.id.businessEmail);
        btnUpdate = findViewById(R.id.btnUpdate);



        check_box.setOnClickListener(v1 -> {
            if (check_box.isChecked()) {

                add_merchant_in_member = "add_member_list";

                //  Toast.makeText(getActivity(), "true!!!", Toast.LENGTH_SHORT).show();
            } else {

                add_merchant_in_member = "remove_member_list";
                //Toast.makeText(getActivity(), "false!!!", Toast.LENGTH_SHORT).show();
            }
        });


        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (categoryBeanListArrayList != null && !categoryBeanListArrayList.isEmpty()) {
                    if (categoryBeanListArrayList.get(position).getCategoryId().equalsIgnoreCase("0")) {

                    } else {
                        bus_category_id = categoryBeanListArrayList.get(position).getCategoryId();
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        select_country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (categoryBeanListArrayList2 != null && !categoryBeanListArrayList2.isEmpty()) {
                    if (categoryBeanListArrayList2.get(position).getId().equalsIgnoreCase("0")) {

                    } else {
                        selected_country = categoryBeanListArrayList2.get(position).getId();
                        selected_country_name = categoryBeanListArrayList2.get(position).getName();


                        Log.e("TAG", "onItemSelected: " + MerchantSignupSlider.selected_country);
                        Log.e("TAG", "onItemSelected: " + MerchantSignupSlider.selected_country_name);
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        zipcode = findViewById(R.id.zipcode);
        merchant_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                cameraIntent.setType("image*//*");
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, 1000);
                }*/
                selectImage();
            }
        });

        country_spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (countryBeanArrayList != null && !countryBeanArrayList.isEmpty()) {
                }
                if (countryBeanArrayList.get(position).getId() == null || countryBeanArrayList.get(position).getId().equalsIgnoreCase("0")) {

                } else {
                    country_str = countryBeanArrayList.get(position).getName();
                    selected_country = countryBeanArrayList.get(position).getId();
                    new GetStateList().execute(countryBeanArrayList.get(position).getId());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        state_spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (statelistbean != null && !statelistbean.isEmpty()) {
                    if (statelistbean.get(position).getId() == null || statelistbean.get(position).getId().equalsIgnoreCase("0")) {

                    } else {
                        mer_state = statelistbean.get(position).getName();
                        new GetCityList().execute(statelistbean.get(position).getId());
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        city_spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (citylistbean != null && !citylistbean.isEmpty()) {
                    if (citylistbean.get(position).getId() == null || citylistbean.get(position).getId().equalsIgnoreCase("0")) {

                    } else {
                        mer_city = citylistbean.get(position).getName();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        businessname.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0) {
                    mer_businessname = "";
                } else {
                    mer_businessname = s.toString();
                    Log.e("sa >.", "> " + mer_businessname);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        phone_number.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0) {
                    mer_phone_number = "";
                } else {
                    mer_phone_number = s.toString();
                    Log.e("sa >.", "> " + mer_phone_number);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        address.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0) {
                    mer_address_two = "";
                } else {
                    mer_address_two = s.toString();
                    Log.e("sa >.", "> " + mer_address_two);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        zipcode.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0) {
                    mer_zipcode = "";
                } else {
                    mer_zipcode = s.toString();
                    Log.e("sa >.", "> " + mer_zipcode);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });


       btnUpdate.setOnClickListener(view -> {
           if (businessname.getText().toString().equalsIgnoreCase("")
                   || phone_number.getText().toString().equalsIgnoreCase("")
                 /* &&  && bus_category_id != null && selected_country != null
                   !selected_country.equalsIgnoreCase("") &&
                   !bus_category_id.equalsIgnoreCase("") &&
                   !selected_country.equalsIgnoreCase("0") &&
                   !bus_category_id.equalsIgnoreCase("0")
                   && mer_address != null &&
                   mer_zipcode != null && !mer_zipcode.equalsIgnoreCase("")*/) {

               Toast.makeText(BusinessSignupAct.this, getResources().getString(R.string.filldetail), Toast.LENGTH_LONG).show();

           } else {
              new UpdateProfile().execute();

           }
       });



    }

    @SuppressLint("Range")
    public String getPath(Uri uri) {
        String path = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        if (cursor.moveToFirst()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            //  Log.e("image_path.===..", "" + path);
        }
        cursor.close();
        return path;
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        String dateToStr = format.format(today);
        ContextWrapper cw = new ContextWrapper(BusinessSignupAct.this);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, "profile_" + dateToStr + ".JPEG");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }

    public void decodeFile(String filePath) {

        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 1024;
            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeFile(filePath, o2);
            ImagePath = saveToInternalStorage(bitmap);
            Log.e("DECODE PATH", "ff " + ImagePath);
            merchant_img.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void selectImage() {
        try {

            final Dialog dialogSts = new Dialog(BusinessSignupAct.this, R.style.DialogSlideAnim);
            dialogSts.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogSts.setCancelable(false);
            dialogSts.setContentView(R.layout.select_img_lay);
            dialogSts.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Button camera = (Button) dialogSts.findViewById(R.id.camera);
            Button gallary = (Button) dialogSts.findViewById(R.id.gallary);
            TextView cont_find = (TextView) dialogSts.findViewById(R.id.cont_find);
            gallary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogSts.dismiss();
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1);

                }
            });

            camera.setOnClickListener(v -> {
                dialogSts.dismiss();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 2);

            });

            cont_find.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogSts.dismiss();
                }
            });
            dialogSts.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkGps() {
        gpsTracker = new GPSTracker(BusinessSignupAct.this);
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            if (latitude == 0.0) {
                latitude = SplashActivity.latitude;
                longitude = SplashActivity.longitude;

            }
        } else {

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

            } else {
                latitude = SplashActivity.latitude;
                longitude = SplashActivity.longitude;
                Log.e("LAT", "" + latitude);
                Log.e("LON", "" + longitude);

            }
        }


    }

    private void getCategoryType() {
        progresbar.setVisibility(View.VISIBLE);
        categoryBeanListArrayList = new ArrayList<>();
        CategoryBeanList categoryBeanList = new CategoryBeanList();
        categoryBeanList.setCategoryId("0");
        categoryBeanList.setCategoryName(getString(R.string.selectcat));
        categoryBeanList.setCategory_name_spanish(getString(R.string.selectcat));
        categoryBeanList.setCategory_name_hindi(getString(R.string.selectcat));
        categoryBeanListArrayList.add(categoryBeanList);
        Call<ResponseBody> call = ApiClient.getApiInterface().getBusnessCategory();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progresbar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("loginCall >", " >" + responseData);
                        if (object.getString("status").equals("1")) {
                            myapisession.setMerchantcat(responseData);
                            CategoryBean successData = new Gson().fromJson(responseData, CategoryBean.class);
                            categoryBeanListArrayList.addAll(successData.getResult());
                        }
                        categoryAdpters = new CategoryAdpters(BusinessSignupAct.this, categoryBeanListArrayList);
                        category_spinner.setAdapter(categoryAdpters);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                t.printStackTrace();
                progresbar.setVisibility(View.GONE);

                Log.e("TAG", t.toString());
            }
        });
    }

    private void getCountryList() {
        progresbar.setVisibility(View.VISIBLE);
        categoryBeanListArrayList2 = new ArrayList<>();
        CountryBeanList categoryBeanList = new CountryBeanList();
        categoryBeanList.setId("0");
        categoryBeanList.setName(getString(R.string.selectcountry));
        categoryBeanListArrayList2.add(categoryBeanList);
        Call<ResponseBody> call = ApiClient.getApiInterface().getBusnessCountry();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progresbar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("loginCall >", " >" + responseData);
                        if (object.getString("status").equals("1")) {
                            myapisession.setMerchantcountry(responseData);

                            main.com.ngrewards.beanclasses.CountryBean successData = new Gson().fromJson(responseData, main.com.ngrewards.beanclasses.CountryBean.class);
                            categoryBeanListArrayList2.addAll(successData.getResult());

                        }



                        countryAdpters = new CountryAdpters(BusinessSignupAct.this, categoryBeanListArrayList2);
                        select_country_spinner.setAdapter(countryAdpters);
                        setCountrySelection(selected_country_name);



                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                t.printStackTrace();
                progresbar.setVisibility(View.GONE);

                Log.e("TAG", t.toString());
            }
        });
    }


    private void setCountrySelection(String countryName) {
        if (countryName == null || countryName.isEmpty()) return;

        for (int i = 0; i < categoryBeanListArrayList2.size(); i++) {
            if (categoryBeanListArrayList2.get(i).getName()
                    .equalsIgnoreCase(countryName)) {

                select_country_spinner.setSelection(i);
                selected_country = categoryBeanListArrayList2.get(i).getId();
                break;
            }
        }
    }



    private class GetStateList extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progresbar.setVisibility(View.VISIBLE);
            statelistbean = new ArrayList<>();
            CountryBean countryListBean = new CountryBean();
            countryListBean.setId("0");
            countryListBean.setName("State");
            countryListBean.setSortname("");
            statelistbean.add(countryListBean);
            try {
                super.onPreExecute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
//https://international.myngrewards.com/wp-content/plugins/webservice/state_lists.php?country_id=101
            try {
                String postReceiverUrl = BaseUrl.baseurl + "state_lists.php?";
                URL url = new URL(postReceiverUrl);
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("country_id", strings[0]);
                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(urlParameters);
                writer.flush();
                String response = "";
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                writer.close();
                reader.close();
                Log.e("Json Country Response", ">>>>>>>>>>>>" + response);
                return response;
            } catch (UnsupportedEncodingException e1) {

                e1.printStackTrace();
            } catch (IOException e1) {

                e1.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progresbar.setVisibility(View.GONE);
            if (result == null) {
            } else if (result.isEmpty()) {

            } else {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String message = jsonObject.getString("message");
                    if (message.equalsIgnoreCase("successful")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            CountryBean countryBean = new CountryBean();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            countryBean.setId(jsonObject1.getString("id"));
                            countryBean.setName(jsonObject1.getString("name"));
                            statelistbean.add(countryBean);
                        }

                        countryListAdapter = new CountryListAdapter(BusinessSignupAct.this, statelistbean);
                        state_spn.setAdapter(countryListAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }
    }

    private class GetCityList extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progresbar.setVisibility(View.VISIBLE);
            citylistbean = new ArrayList<>();
            CountryBean countryListBean = new CountryBean();
            countryListBean.setId("0");
            countryListBean.setName("City");
            countryListBean.setSortname("");
            citylistbean.add(countryListBean);
            try {
                super.onPreExecute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
//https://international.myngrewards.com/wp-content/plugins/webservice/city_lists.php?state_id=21
            try {
                String postReceiverUrl = BaseUrl.baseurl + "city_lists.php?";
                URL url = new URL(postReceiverUrl);
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("state_id", strings[0]);
                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(urlParameters);
                writer.flush();
                String response = "";
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                writer.close();
                reader.close();
                Log.e("Json CIty Response", ">>>>>>>>>>>>" + response);
                return response;
            } catch (UnsupportedEncodingException e1) {

                e1.printStackTrace();
            } catch (IOException e1) {

                e1.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progresbar.setVisibility(View.GONE);
            if (result == null) {
            } else if (result.isEmpty()) {

            } else {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String message = jsonObject.getString("message");
                    if (message.equalsIgnoreCase("successful")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            CountryBean countryBean = new CountryBean();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            countryBean.setId(jsonObject1.getString("id"));
                            countryBean.setName(jsonObject1.getString("name"));
                            citylistbean.add(countryBean);
                        }

                        countryListAdapter = new CountryListAdapter(BusinessSignupAct.this, citylistbean);
                        city_spn.setAdapter(countryListAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }
    }

    public class CountryListAdapter extends BaseAdapter {
        private final ArrayList<CountryBean> values;
        Context context;
        LayoutInflater inflter;

        public CountryListAdapter(Context applicationContext, ArrayList<CountryBean> values) {
            this.context = applicationContext;
            this.values = values;

            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {

            return values == null ? 0 : values.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.spinner_lay, null);

            TextView names = (TextView) view.findViewById(R.id.name_tv);
            //  TextView countryname = (TextView) view.findViewById(R.id.countryname);


            names.setText(values.get(i).getName());


            return view;
        }
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }

    class GeoAutoCompleteAdapter extends BaseAdapter implements Filterable {

        private final Activity context;
        private final LayoutInflater layoutInflater;
        private final WebOperations wo;
        private final String lat;
        private final String lon;
        private List<String> l2 = new ArrayList<>();

        public GeoAutoCompleteAdapter(Activity context, List<String> l2, String lat, String lon) {
            this.context = context;
            this.l2 = l2;
            this.lat = lat;
            this.lon = lon;
            layoutInflater = LayoutInflater.from(context);
            wo = new WebOperations(context);
        }

        @Override
        public int getCount() {

            //  return l2.size();
            return l2 == null ? 0 : l2.size();
        }

        @Override
        public Object getItem(int i) {
            return l2.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            view = layoutInflater.inflate(R.layout.geo_search_result, viewGroup, false);
            TextView geo_search_result_text = (TextView) view.findViewById(R.id.geo_search_result_text);
            try {
                geo_search_result_text.setText(l2.get(i));
                geo_search_result_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);

                        if (l2 == null || l2.isEmpty()) {

                        } else {
                            if (l2.get(i) == null) {

                            } else {
                                streetaddress.setText("" + l2.get(i));
                                streetaddress.dismissDropDown();

                                MerchantSignupSlider.mer_address = streetaddress.getText().toString();
                                if (MerchantSignupSlider.mer_address == null || MerchantSignupSlider.mer_address.equalsIgnoreCase("")) {
                                } else {
                                    new GetPickUp().execute();
                                }
                            }

                        }


                    }
                });

            } catch (Exception e) {

            }

            return view;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        wo.setUrl("https://maps.googleapis.com/maps/api/place/autocomplete/json?key="+getString(R.string.googlekey)+"&input=" + constraint.toString().trim().replaceAll(" ", "+") + "&location=" + lat + "," + lon + "+&radius=20000&types=geocode&sensor=true");
                        String result = null;
                        try {
                            result = new MyTask(wo, 3).execute().get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        parseJson(result);


                        // Assign the data to the FilterResults
                        filterResults.values = l2;
                        filterResults.count = l2.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    if (results != null && results.count != 0) {
                        l2 = (List) results.values;
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }

        private void parseJson(String result) {
            try {
                l2 = new ArrayList<>();
                JSONObject jk = new JSONObject(result);

                JSONArray predictions = jk.getJSONArray("predictions");
                for (int i = 0; i < predictions.length(); i++) {
                    JSONObject js = predictions.getJSONObject(i);
                    l2.add(js.getString("description"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private List<Address> findLocations(Context context, String query_text) {

            List<Address> geo_search_results = new ArrayList<Address>();

            Geocoder geocoder = new Geocoder(context, context.getResources().getConfiguration().locale);
            List<Address> addresses = null;

            try {
                // Getting a maximum of 15 Address that matches the input text
                addresses = geocoder.getFromLocationName(query_text, 15);


            } catch (IOException e) {
                e.printStackTrace();
            }

            return addresses;
        }
    }

    private class GetPickUp extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            try {
                super.onPreExecute();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String address = MerchantSignupSlider.mer_address.trim().replaceAll(" ", "+");
            Log.e("Murchant Location >>>", "" + MerchantSignupSlider.mer_address);
            String postReceiverUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key="+getString(R.string.googlekey);

            try {
                //  String postReceiverUrl = "https://api.ctlf.co.uk/";
                URL url = new URL(postReceiverUrl);
                Map<String, Object> params = new LinkedHashMap<>();


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

                writer.write(urlParameters);
                writer.flush();

                String response = "";
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                writer.close();
                reader.close();
                Log.e("JsonShyam", ">>>>>>>>>>>>" + response);


                return response;

            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null) {

            } else if (result.equalsIgnoreCase("")) {

            } else {
                JSONObject location = null;
                try {
                    location = new JSONObject(result).getJSONArray("results")
                            .getJSONObject(0).getJSONObject("geometry")
                            .getJSONObject("location");


                    //    pickup_lat_str,pickup_lon_str,drop_lat_str,drop_lon_str,
                    MerchantSignupSlider.mer_latitude = location.getDouble("lat");
                    MerchantSignupSlider.mer_longitude = location.getDouble("lng");
                    Log.e("event_lat >>>>>>>", "" + MerchantSignupSlider.mer_latitude);
                    Log.e("event_lon >>>>>>>", "" + MerchantSignupSlider.mer_longitude);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    public class CategoryAdpters extends BaseAdapter {
        private final ArrayList<CategoryBeanList> categoryBeanLists;
        Context context;
        LayoutInflater inflter;

        public CategoryAdpters(Context applicationContext, ArrayList<CategoryBeanList> categoryBeanLists) {
            this.context = applicationContext;
            this.categoryBeanLists = categoryBeanLists;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return categoryBeanLists == null ? 0 : categoryBeanLists.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.spinner_layout, null);
            TextView names = (TextView) view.findViewById(R.id.name_tv);
            ImageView country_flag = (ImageView) view.findViewById(R.id.country_flag);
            //  TextView countryname = (TextView) view.findViewById(R.id.countryname);
            if (mySession.getValueOf(KEY_LANGUAGE).equalsIgnoreCase("es")) {
                names.setText(categoryBeanLists.get(i).getCategory_name_spanish());
            } else if (mySession.getValueOf(KEY_LANGUAGE).equalsIgnoreCase("hi")) {
                names.setText(categoryBeanLists.get(i).getCategory_name_hindi());
            } else {
                names.setText(categoryBeanLists.get(i).getCategoryName());
            }
            return view;
        }
    }

    public class CountryAdpters extends BaseAdapter {
        private final ArrayList<CountryBeanList> categoryBeanLists2;
        Context context;
        LayoutInflater inflter;

        public CountryAdpters(Context applicationContext, ArrayList<CountryBeanList> categoryBeanLists) {
            this.context = applicationContext;
            this.categoryBeanLists2 = categoryBeanLists;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return categoryBeanLists2 == null ? 0 : categoryBeanLists2.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.spinner_layout, null);
            TextView names = (TextView) view.findViewById(R.id.name_tv);
            ImageView country_flag = (ImageView) view.findViewById(R.id.country_flag);
            //  TextView countryname = (TextView) view.findViewById(R.id.countryname);
            names.setText(categoryBeanLists2.get(i).getName());
            return view;
        }
    }



    public class UpdateProfile extends AsyncTask<String, String, String> {
        String Jsondata;

        protected void onPreExecute() {
            try {
                super.onPreExecute();
                progresbar.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();

            }

        }

        @Override
        protected String doInBackground(String... strings) {
            String charset = "UTF-8";
            String requestURL = BaseUrl.baseurl + "update_merchant_profile.php?";
            Log.e("requestURL >>", requestURL + "merchant_id=");

            /*try {
                MultipartUtility multipart = new MultipartUtility(requestURL, charset);
                multipart.addFormField("merchant_id", user_id);
                multipart.addFormField("email", busines_email_str);
                multipart.addFormField("contact_number", bus_number_str);
                multipart.addFormField("business_no", business_number);

                multipart.addFormField("business_description", businessdescription_str);
                multipart.addFormField("business_name", businessname_str);
                multipart.addFormField("contact_name", contact_name_str);
                multipart.addFormField("account_no", "");
               *//* multipart.addFormField("longitude", "");
                multipart.addFormField("latitude", "");*//*

                multipart.addFormField("zip_code", zipcode_str);
                multipart.addFormField("address", streetaddress_str);
                multipart.addFormField("address_two", address_tv_str);
                multipart.addFormField("country", country_str);
                multipart.addFormField("state", state_str);
                multipart.addFormField("city", city_str);
                multipart.addFormField("reward_percent", reward_per_str);
                multipart.addFormField("website_url", bus_website_str);
                multipart.addFormField("facebook_url", facebooklink_str);
                multipart.addFormField("twitter_url", twittelink_str);
                multipart.addFormField("linkdin_url", linkedinlink_str);
                multipart.addFormField("google_plus_url", googlelink_str);
                multipart.addFormField("instagram_url", instalink_str);
                multipart.addFormField("youtube_url", youtubelink_str);
                multipart.addFormField("business_category", category_id);
                multipart.addFormField("latitude", "" + mer_latitude);
                multipart.addFormField("longitude", "" + mer_longitude);
                multipart.addFormField("day_name", day_name_str);
                multipart.addFormField("opening_status", open_close_str);
                multipart.addFormField("opening_time", opening_hour_str);
                multipart.addFormField("closing_time", closing_hour_str);
                multipart.addFormField("fullday_open_status", full_day_opne_status);
                multipart.addFormField("keyword", businesskeywords_str);

                Log.e("open_close_str dd>>", " >> " + open_close_str);
                Log.e("day_name_str dd>>", " >> " + day_name_str);

//gallery_image
                if (ImagePathArrayList == null || ImagePathArrayList.isEmpty()) {
//["+k+"]
                } else {
                    for (int k = 0; k < filearray.length; k++) {
                        if (k < 7) {
                            multipart.addFilePart("gallery_image[]", filearray[k]);
                        }

                    }

                    // multipart.addFilePart("member_image[]", filearray);
                }
                if (ImagePath == null || ImagePath.equalsIgnoreCase("")) {

                } else {
                    File ImageFile = new File(ImagePath);
                    multipart.addFilePart("merchant_image", ImageFile);
                }
                List<String> response = multipart.finish();

                for (String line : response) {
                    Jsondata = line;
                }
                JSONObject object = new JSONObject(Jsondata);*/



                try {
                    MultipartUtility multipart = new MultipartUtility(requestURL, charset);
                    multipart.addFormField("merchant_id", merchantId);
                    multipart.addFormField("email", businessEmail.getText().toString());
                    multipart.addFormField("contact_number", phone_number.getText().toString());
                    multipart.addFormField("business_no", mobilenum_str);

                    multipart.addFormField("business_description","" );
                    multipart.addFormField("business_name", businessname.getText().toString());
                    multipart.addFormField("contact_name", mer_fullname);
                    multipart.addFormField("account_no", "");




                    multipart.addFormField("zip_code", mer_zipcode);
                    multipart.addFormField("address", mer_address);
                    multipart.addFormField("address_two", mer_address_two);
                    multipart.addFormField("country", selected_country);
                  //  multipart.addFormField("country_id", selected_country);
                    multipart.addFormField("state", mer_state);
                    multipart.addFormField("city", mer_city);

                   // multipart.addFormField("device_token", firebase_regid);

                    multipart.addFormField("reward_percent", mer_reward);
                    multipart.addFormField("website_url", "");
                    multipart.addFormField("facebook_url", "");
                    multipart.addFormField("twitter_url", "");
                    multipart.addFormField("linkdin_url", "");
                    multipart.addFormField("google_plus_url", "");
                    multipart.addFormField("instagram_url", "");
                    multipart.addFormField("youtube_url", "");

                    multipart.addFormField("business_category", "");
                    multipart.addFormField("latitude", "" + latitude);
                    multipart.addFormField("longitude", "" + longitude);





                    multipart.addFormField("day_name", "Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday");
                    multipart.addFormField("opening_time", "0:0,0:0,0:0,0:0,0:0,0:0,0:0");
                    multipart.addFormField("closing_time", "0:0,0:0,0:0,0:0,0:0,0:0,0:0");
                    multipart.addFormField("opening_status", "OPEN,OPEN,OPEN,OPEN,OPEN,OPEN,OPEN");
                    multipart.addFormField("fullday_open_status", "DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT");
                    multipart.addFormField("keyword", "");



                    if (ImagePath == null || ImagePath.equalsIgnoreCase("")) {
                    } else {
                        File ImageFile = new File(ImagePath);
                        multipart.addFilePart("merchant_image", ImageFile);
                    }
                    List<String> response = multipart.finish();
                    for (String line : response) {
                        Jsondata = line;
                    }
                    Log.e("SIGNUParems ", " >> " + "business_category" + bus_category_id);
                    Log.e("SIGNUParems ", " >> " + "zip_code" + mer_zipcode);
                    Log.e("SIGNUParems ", " >> " + "address" + mer_address);
                    Log.e("SIGNUParems ", " >> " + "address_two" + mer_address_two);
                    Log.e("SIGNUParems ", " >> " + "country" + selected_country_name);
                    Log.e("SIGNUParems ", " >> " + "country_id" + selected_country);
                    Log.e("SIGNUParems ", " >> " + "state" + mer_state);
                    Log.e("SIGNUParems ", " >> " + "city" + mer_city);
                    Log.e("SIGNUParems ", " >> " + "device_token" + "");
                    Log.e("SIGNUParems ", " >> " + "reward_percent" + mer_reward);
                    Log.e("SIGNUParems ", " >> " + "how_invite_you" + mer_who_invite);
                    Log.e("SIGNUParems ", " >> " + "sales_agent_key" + "1");
                    Log.e("SIGNUParems ", " >> " + "sales_agent_no" + "1");
                    Log.e("SIGNUParems ", " >> " + "sales_agent_name" + "1");
                    Log.e("SIGNUParems ", " >> " + "website_url" + "");
                    Log.e("SIGNUParems ", " >> " + "facebook_url" + "");
                    Log.e("SIGNUParems ", " >> " + "twitter_url" + "");
                    Log.e("SIGNUParems ", " >> " + "linkdin_url" + "");
                    Log.e("SIGNUPRESPONE ", " >> " + response);
                    JSONObject object = new JSONObject(Jsondata);
                    return Jsondata;

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progresbar.setVisibility(View.GONE);
            Log.e("Update Mer Prof", ">" + result);
            if (result == null) {
            } else if (result.isEmpty()) {

            } else {
                JSONObject jsonObject = null;
                try {

                    jsonObject = new JSONObject(result);
                    JSONObject resultObject = jsonObject.getJSONObject("result");

                   // jsonObject = new JSONObject(result);
                    String result_chk = jsonObject.getString("status");
                    if (result_chk.equalsIgnoreCase("1")) {
                        try {
                            // responseString = your API response



                            mobilenum_str = resultObject.getString("business_no");
                            password_str = resultObject.getString("password");

                            Log.d("API_DATA", "Business No: " + mobilenum_str);
                            Log.d("API_DATA", "Password: " + password_str);
                            new LoginAsc().execute();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }


    }




    private class GetProfile extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progresbar.setVisibility(View.VISIBLE);

            try {
                super.onPreExecute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String postReceiverUrl = BaseUrl.baseurl + "merchant_profile.php?";
                URL url = new URL(postReceiverUrl);
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("merchant_id", merchantId);
                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }

                String urlParameters = postData.toString();
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(urlParameters);
                writer.flush();
                String response = "";
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                writer.close();
                reader.close();
                Log.e("  MER HOME GetProfile Response", ">>>>>>>>>>>>" + response);

                return response;

            } catch (Exception e1) {

                e1.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progresbar.setVisibility(View.GONE);

            if (result == null) {

            } else if (result.isEmpty()) {

            } else {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    String message = jsonObject.getString("status");
                    Log.e("profile data>>>", jsonObject.toString());


                    if (message.equalsIgnoreCase("1")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("result");

                        String business_name = jsonObject1.getString("business_name");
                        String contact_name = jsonObject1.getString("contact_name");
                        selected_country_name = jsonObject1.getString("country");

                     /*   if (business_name == null || business_name.equalsIgnoreCase("")) {

                            businessname.setText("" + contact_name);
                        } else {
                            businessname.setText("" + business_name);
                        }
*/
                       // business_number = jsonObject1.getString("business_no");

                        businessname.setText("" + jsonObject1.getString("business_no"));
                        businessEmail.setText("" + jsonObject1.getString("email"));
                        phone_number.setText("" + jsonObject1.getString("contact_number"));
                        //businessdescription.setText("" + jsonObject1.getString("business_description"));


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }




    private class LoginAsc extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progresbar.setVisibility(View.VISIBLE);

            try {
                super.onPreExecute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String postReceiverUrl = BaseUrl.baseurl + "merchant_login.php?";
                URL url = new URL(postReceiverUrl);
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("business_no", mobilenum_str);
                params.put("password", password_str);
                params.put("latitude", latitude);
                params.put("longitude", longitude);
                params.put("country", selected_country_name);
                params.put("country_id", selected_country);
                params.put("device_token", firbaseId);
                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(urlParameters);
                writer.flush();
                String response = "";
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                writer.close();
                reader.close();
                Log.e("Json Login Response", ">>>>>>>>>>>>" + response);
                return response;
            } catch (UnsupportedEncodingException e1) {

                e1.printStackTrace();
            } catch (IOException e1) {

                e1.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progresbar.setVisibility(View.GONE);
            if (result == null) {
            } else if (result.isEmpty()) {

            } else {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    String result_chk = jsonObject.getString("status");
                    if (result_chk.equalsIgnoreCase("1")) {
                        Log.e("", "jsonObjectjsonObjectjsonObjectjsonObject:------ " + result);

                        mySession.setlogindata(result);
                        mySession.signinusers(true);

                        JSONObject resultObj = jsonObject.getJSONObject("result");
                        SharedPreferences prefs = getSharedPreferences("merchant_login_prefs", MODE_PRIVATE);
                        prefs.edit().putString("user_id", resultObj.getString("id")).apply();


                        Intent i = new Intent(BusinessSignupAct.this, MerchantBottumAct.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i);
                    } else {
                        Toast.makeText(BusinessSignupAct.this, "Invalid Credential", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }
    }




}

