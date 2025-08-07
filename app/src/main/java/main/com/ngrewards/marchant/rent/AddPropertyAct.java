package main.com.ngrewards.marchant.rent;

import static android.content.ContentValues.TAG;
import static main.com.ngrewards.marchant.draweractivity.MerchantBaseActivity.reqcounft;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import main.com.ngrewards.R;
import main.com.ngrewards.Utils.Tools;
import main.com.ngrewards.constant.BaseUrl;
import main.com.ngrewards.constant.MultipartUtility;
import main.com.ngrewards.constant.MySession;
import main.com.ngrewards.constant.Myapisession;
import main.com.ngrewards.databinding.ActivityAddPropertyBinding;
import main.com.ngrewards.marchant.activity.StartYourListing;
import main.com.ngrewards.marchant.merchantbottum.MerchantBottumAct;
import main.com.ngrewards.marchant.merchantbottum.MultiPhotoSelectActivity;
import main.com.ngrewards.marchant.merchantbottum.MultiPhotoSelectActivity2;
import main.com.ngrewards.restapi.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPropertyAct extends AppCompatActivity implements onAmenitiesListener {
    ActivityAddPropertyBinding binding;
    public static ArrayList<String> ImagePathArrayList= new ArrayList<>();
    public  ArrayList<RentCategoryModel.Datum> categoryArrayList;
    public  ArrayList<PropertyAmenitiesModel.Datum> amenitiesArrayList;

    HorizontalAdapter horizontalAdapter;
    AmenitiesAdapter amenitiesAdapter;
    String categoryId="",amenitiesString="",userId="",date="",address="",latitude="",longitude="",email="",contactNumber="";
    RecyclerView rvAmenities;

    File[] filearray;
    MySession mySession;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this, R.layout.activity_add_property);
       initViews();
    }

    private void initViews() {

        mySession = new MySession(this);
        filearray = new File[0];
        String user_log_data = mySession.getKeyAlldata();
        if (user_log_data == null) {

        } else {
            try {
                JSONObject jsonObject = new JSONObject(user_log_data);
                String message = jsonObject.getString("status");
                if (message.equalsIgnoreCase("1")) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                    userId = jsonObject1.getString("id");

                    // stripe_account_id = jsonObject1.getString("stripe_account_id");


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        categoryArrayList = new ArrayList<>();
        amenitiesArrayList = new ArrayList<>();

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(AddPropertyAct.this, LinearLayoutManager.HORIZONTAL, false);
        binding.addProductList.setLayoutManager(horizontalLayoutManagaer);


        horizontalAdapter = new HorizontalAdapter(ImagePathArrayList);
        binding.addProductList.setAdapter(horizontalAdapter);
        horizontalAdapter.notifyDataSetChanged();

        binding.uploadimg.setOnClickListener(v -> {
            if (ImagePathArrayList.size() == 10) {
                Toast.makeText(AddPropertyAct.this, "Only 10 images Uploaded", Toast.LENGTH_LONG).show();
            } else if (ImagePathArrayList.size() < 10) {
                selectImage();
            }
        });


        binding.tvCategory.setOnClickListener(v -> {
           showDropDownCategory(v,binding.tvCategory,categoryArrayList);
        });

        binding.tvAmenities.setOnClickListener(v -> {
            dialogAmenities(amenitiesArrayList);
        });


        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.btnSubmit.setOnClickListener(v -> validation());


        binding.edDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddPropertyAct.this,
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        // Note: Month is 0-based in DatePicker
                        calendar.set(selectedYear, selectedMonth, selectedDay);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        date = sdf.format(calendar.getTime());
                        binding.edDate.setText(date);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });


        getCategory();
        getPropertyAmenities();
        new GetProfile().execute();
    }


    private class GetProfile extends AsyncTask<String, String, String> {
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
            try {
                String postReceiverUrl = BaseUrl.baseurl + "merchant_profile.php?";
                URL url = new URL(postReceiverUrl);
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("merchant_id", userId);
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
                Log.e(" MER BOTTEM GetProfile Response", ">>>>>>>>>>>>" + response);
                return response;
            } catch (IOException e1) {

                e1.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null) {
            } else if (result.isEmpty()) {

            } else {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    // Log.e("TAG", "JSONObjectJSONObjectJSONObjectJSONObject: "+jsonObject.toString() );
                    String message = jsonObject.getString("status");
                    if (message.equalsIgnoreCase("1")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                        address = jsonObject1.getString("address");
                         latitude = jsonObject1.getString("latitude");
                         longitude = jsonObject1.getString("longitude");
                         email = jsonObject1.getString("email");
                        contactNumber = jsonObject1.getString("contact_number");






                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }



    private void validation() {

        if (binding.propertyTitle.getText().toString().isEmpty()) {
            Toast.makeText(AddPropertyAct.this, getResources().getString(R.string.entertite), Toast.LENGTH_LONG).show();
        } else if ( categoryId.equalsIgnoreCase("")) {
            Toast.makeText(AddPropertyAct.this, getResources().getString(R.string.selectcat), Toast.LENGTH_LONG).show();
        } else if ( binding.descriptionEt.getText().toString().isEmpty()) {
            Toast.makeText(AddPropertyAct.this, getResources().getString(R.string.enterdesc), Toast.LENGTH_LONG).show();

        } else if ( amenitiesString.equalsIgnoreCase("")) {
            Toast.makeText(AddPropertyAct.this, getResources().getString(R.string.select_amenities), Toast.LENGTH_LONG).show();

        } else if (binding.edUnitNumber.getText().toString().isEmpty()) {
            Toast.makeText(AddPropertyAct.this, getResources().getString(R.string.enter_unit_number), Toast.LENGTH_LONG).show();

        }

        else if (binding.edFloorLevel.getText().toString().isEmpty()) {
            Toast.makeText(AddPropertyAct.this, getResources().getString(R.string.enter_floor_level), Toast.LENGTH_LONG).show();

        }

        else if (binding.edPrice.getText().toString().isEmpty()) {
            Toast.makeText(AddPropertyAct.this, getResources().getString(R.string.enterprice), Toast.LENGTH_LONG).show();

        }
        else if (binding.edArea.getText().toString().isEmpty()) {
            Toast.makeText(AddPropertyAct.this, getResources().getString(R.string.enter_property_area), Toast.LENGTH_LONG).show();

        }

        else if ( date.equalsIgnoreCase("")) {
            Toast.makeText(AddPropertyAct.this, getString(R.string.enter_date), Toast.LENGTH_LONG).show();

        }

        else if (ImagePathArrayList == null || ImagePathArrayList.isEmpty() || ImagePathArrayList.size() == 0) {
            Toast.makeText(AddPropertyAct.this, getResources().getString(R.string.selectphoto), Toast.LENGTH_LONG).show();

        } else {
            Log.e("ImagePathArrayList size", " > " + ImagePathArrayList.size());
            filearray = new File[ImagePathArrayList.size()];
            Log.e("filearray size", " > " + filearray.length);

            for (int i = 0; i < ImagePathArrayList.size(); i++) {
                Log.e("Image", " > " + ImagePathArrayList.get(i));

                File ImageFile = new File(ImagePathArrayList.get(i));
                filearray[i] = ImageFile;
            }

            new AddPropertyAsc().execute();
        }

    }


    private void selectImage() {
        final Dialog dialogSts = new Dialog(AddPropertyAct.this, R.style.DialogSlideAnim);
        dialogSts.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSts.setCancelable(false);
        dialogSts.setContentView(R.layout.select_img_lay);
        dialogSts.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final Button camera = (Button) dialogSts.findViewById(R.id.camera);
        Button gallary = (Button) dialogSts.findViewById(R.id.gallary);
        TextView cont_find = (TextView) dialogSts.findViewById(R.id.cont_find);
        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSts.dismiss();
                if (Build.VERSION.SDK_INT >= 33) {
                    Intent i = new Intent(AddPropertyAct.this, MultiPhotoSelectActivity2.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(AddPropertyAct.this, MultiPhotoSelectActivity.class);
                    startActivity(i);

                }

            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSts.dismiss();
               /* ContextWrapper cw = new ContextWrapper(StartYourListing.this);
              //  File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File file = new File(Environment.getExternalStorageDirectory(), "/imageDir/a" + "/photo_" + timeStamp + ".png");
                imageUri = Uri.fromFile(file);*/


               /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, PICTURE_RESULT);*/

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //   cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(cameraIntent, 2);


            }
        });
        cont_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSts.dismiss();
            }
        });
        dialogSts.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MultiPhotoSelectActivity.image == null) {

        } else if (MultiPhotoSelectActivity.image.isEmpty()) {

        } else {
            for (int i = 0; i < MultiPhotoSelectActivity.image.size(); i++) {
                if (ImagePathArrayList.size() < 10) {
                    Log.e("Select Photo ", " > " + MultiPhotoSelectActivity.image.get(i));
                    ImagePathArrayList.add(MultiPhotoSelectActivity.image.get(i));
                    Log.e("Select Photo add", " > " + ImagePathArrayList.get(i));

                }

            }
            MultiPhotoSelectActivity.image = null;
            binding.addProductList.setVisibility(View.VISIBLE);
            horizontalAdapter = new HorizontalAdapter(ImagePathArrayList);
            binding.addProductList.setAdapter(horizontalAdapter);
            horizontalAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Uri selectedImage = data.getData();
                    String ImagePath = getPath(selectedImage);
                    ImagePathArrayList.add(ImagePath);
                    //  decodeFile(ImagePath);
                    binding.addProductList.setVisibility(View.VISIBLE);
                    horizontalAdapter = new HorizontalAdapter(ImagePathArrayList);
                    binding.addProductList.setAdapter(horizontalAdapter);
                    horizontalAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    String cameraPath = saveToInternalStorage(photo);
                    Log.e("PATH Camera", "" + cameraPath);
                    //  String ImagePath = getPath(selectedImage);

                   /* Bitmap thumbnail = null;
                    try {
                        thumbnail = MediaStore.Images.Media.getBitmap(
                                getContentResolver(), imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String newcampath = getRealPathFromURI(imageUri);*/
                    ImagePathArrayList.add(cameraPath);

                    binding.addProductList.setVisibility(View.VISIBLE);
                    horizontalAdapter = new HorizontalAdapter(ImagePathArrayList);
                    binding.addProductList.setAdapter(horizontalAdapter);
                    horizontalAdapter.notifyDataSetChanged();
                    //  decodeFile(cameraPath);
                    break;

            }
        }

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
        String ppath = "";
        if (Build.VERSION.SDK_INT >= 33) {
            File tempfile = Tools.persistImage(bitmapImage, getApplicationContext());
            ppath = tempfile.getAbsolutePath();

        } else {

            Date today = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String dateToStr = format.format(today);
            ContextWrapper cw = new ContextWrapper(AddPropertyAct.this);
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File mypath = new File(directory, "profile_" + dateToStr + ".PNG");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                Bitmap.createScaledBitmap(bitmapImage, 1000, 1000, true);
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                //

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ppath = mypath.getAbsolutePath();
        }
        return ppath;
    }



    private class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private final ArrayList<String> ImagePathArrayList_adp;
        private ArrayList<Bitmap> horizontalList;

        public HorizontalAdapter(ArrayList<String> ImagePathArrayList_adp) {
            this.horizontalList = horizontalList;
            this.ImagePathArrayList_adp = ImagePathArrayList_adp;
        }

        @Override
        public HorizontalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.horizontal_list_item, parent, false);

            return new HorizontalAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
            if (ImagePathArrayList_adp.get(position) != null) {
                if (Build.VERSION.SDK_INT >= 33) {

                    Log.e("TAG", "onBindViewHolder: ---------- " + ImagePathArrayList_adp.get(position));
                    //  File tempfile = Tools.persistImage(bitmapImage, getApplicationContext());
                    //  ppath = tempfile.getAbsolutePath();
                    //   holder.ProductImageImagevies.setImageURI(Uri.fromFile(Tools.persistImage()));
                    holder.ProductImageImagevies.setImageURI(Uri.fromFile(new File(ImagePathArrayList_adp.get(position))));

                } else {
                    holder.ProductImageImagevies.setImageURI(Uri.fromFile(new File(ImagePathArrayList_adp.get(position))));

                }

            }
            holder.removeimages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ImagePathArrayList != null && !ImagePathArrayList.isEmpty()) {
                        ImagePathArrayList.remove(position);

                        horizontalAdapter = new HorizontalAdapter(ImagePathArrayList);
                        binding.addProductList.setAdapter(horizontalAdapter);
                        horizontalAdapter.notifyDataSetChanged();
                        if (ImagePathArrayList == null || ImagePathArrayList.isEmpty()) {
                            binding.addProductList.setVisibility(View.GONE);
                        }
                    }

                }
            });

        }

        @Override
        public int getItemCount() {
            return ImagePathArrayList_adp == null ? 0 : ImagePathArrayList_adp.size();

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public ImageView ProductImageImagevies, removeimages;
            //   RelativeLayout RLRemovePhoto;

            public MyViewHolder(View view) {
                super(view);

                ProductImageImagevies = (ImageView) view.findViewById(R.id.productimage);
                removeimages = (ImageView) view.findViewById(R.id.removeimages);
                //    RLRemovePhoto = (RelativeLayout) view.findViewById(R.id.RLRemovePhoto);

            }
        }
    }


    private void showDropDownCategory(View v, TextView textView, List<RentCategoryModel.Datum> stringList) {
        PopupMenu popupMenu = new PopupMenu(AddPropertyAct.this, v);
        for (int i = 0; i < stringList.size(); i++) {
            popupMenu.getMenu().add(stringList.get(i).getName());
        }


        popupMenu.setOnMenuItemClickListener(menuItem -> {
            //   textView.setText(menuItem.getTitle());
            for (int i = 0; i < stringList.size(); i++) {
                if (stringList.get(i).getName().equalsIgnoreCase(menuItem.getTitle().toString())) {
                    categoryId = stringList.get(i).getName();
                    textView.setText(stringList.get(i).getName());



                }
            }
            return true;
        });
        popupMenu.show();
    }



    private void getCategory() {
        binding.progresbar.setVisibility(View.VISIBLE);
        Call<ResponseBody> call = ApiClient.getApiInterface().getPropertyCategoryApi();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progresbar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("get category response >", " >" + responseData);
                        if (object.getBoolean("status")) {
                            RentCategoryModel successData = new Gson().fromJson(responseData, RentCategoryModel.class);
                            categoryArrayList.clear();
                            categoryArrayList.addAll(successData.getData());

                        }


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
                binding.progresbar.setVisibility(View.GONE);

                Log.e("TAG", t.toString());
            }
        });
    }


    private void getPropertyAmenities() {
        binding.progresbar.setVisibility(View.VISIBLE);
        Call<ResponseBody> call = ApiClient.getApiInterface().getPropertyAmenitiesApi();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progresbar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("get property amenities response >", " >" + responseData);
                        if (object.getBoolean("status")) {
                            PropertyAmenitiesModel successData = new Gson().fromJson(responseData, PropertyAmenitiesModel.class);
                            amenitiesArrayList.clear();
                            amenitiesArrayList.addAll(successData.getData());

                        }


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
                binding.progresbar.setVisibility(View.GONE);

                Log.e("TAG", t.toString());
            }
        });
    }

    private void dialogAmenities(ArrayList<PropertyAmenitiesModel.Datum>arrayList) {
        try {
            final Dialog dialogAmenities = new Dialog(AddPropertyAct.this, R.style.DialogSlideAnim);
            dialogAmenities.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogAmenities.setCancelable(false);
            dialogAmenities.setContentView(R.layout.dialog_amenities);
            dialogAmenities.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView ivClose =  dialogAmenities.findViewById(R.id.ivClose);
            Button btnSave =  dialogAmenities.findViewById(R.id.btnSave);


             rvAmenities =  dialogAmenities.findViewById(R.id.rvAmenities);

             amenitiesAdapter = new AmenitiesAdapter(AddPropertyAct.this,arrayList,AddPropertyAct.this);
             rvAmenities.setAdapter(amenitiesAdapter);

            ivClose.setOnClickListener(v -> dialogAmenities.dismiss());

            btnSave.setOnClickListener(v -> {
                if(amenitiesString.equalsIgnoreCase("")){
                    Toast.makeText(this,getString(R.string.please_select_amenities),Toast.LENGTH_LONG).show();
                }
                else {

                    binding.tvAmenities.setText(amenitiesString);
                    dialogAmenities.dismiss();
                }
            });


            dialogAmenities.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onAmenities(int position, String Type, PropertyAmenitiesModel.Datum data) {
        if(data.isChk()){
            amenitiesArrayList.get(position).setChk(false);
            amenitiesAdapter.notifyList(amenitiesArrayList);
        }
        else {
            amenitiesArrayList.get(position).setChk(true);
            amenitiesAdapter.notifyList(amenitiesArrayList);
        }

        amenitiesString = getCheckedAmenitiesCommaSeparated(amenitiesArrayList);
    }


    public static String getCheckedAmenitiesCommaSeparated(ArrayList<PropertyAmenitiesModel.Datum> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        for (PropertyAmenitiesModel.Datum item : list) {
            if (item.isChk()) {
                if (result.length() > 0) {
                    result.append(", ");
                }
                result.append(item.getName()); // Use getId() if needed
            }
        }

        return result.toString();
    }



    public class AddPropertyAsc extends AsyncTask<String, String, String> {
        String Jsondata;

        protected void onPreExecute() {
            try {
                super.onPreExecute();
                binding.progresbar.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String charset = "UTF-8";
            String requestURL = BaseUrl.baseurl + "add_property.php?";
            Log.e("requestURL >>", requestURL);
            try {





                MultipartUtility multipart = new MultipartUtility(requestURL, charset);
                multipart.addFormField("title", binding.propertyTitle.getText().toString());
                multipart.addFormField("merchant_id", userId);
                multipart.addFormField("category", categoryId);
                multipart.addFormField("description", binding.descriptionEt.getText().toString());
                multipart.addFormField("unit_number", binding.edUnitNumber.getText().toString());
                multipart.addFormField("floor_level", binding.edFloorLevel.getText().toString());
                multipart.addFormField("price", binding.edPrice.getText().toString());
                multipart.addFormField("square_footage", binding.edArea.getText().toString());
                multipart.addFormField("availability_date", date);
                multipart.addFormField("amenities[]", amenitiesString);
                multipart.addFormField("address",address );
                multipart.addFormField("latitude", latitude);
                multipart.addFormField("longitude",longitude);
                multipart.addFormField("contact_number", contactNumber);
                multipart.addFormField("email", email);





                if (ImagePathArrayList == null || ImagePathArrayList.isEmpty()) {
//["+k+"]
                } else {
                    for (int k = 0; k < filearray.length; k++) {
                        multipart.addFilePart("files[]", filearray[k]);
                    }

                    // multipart.addFilePart("member_image[]", filearray);
                }
                List<String> response = multipart.finish();

                for (String line : response) {
                    Jsondata = line;
                }
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
            binding.progresbar.setVisibility(View.GONE);
            Log.e("Add property response  ", " >> " + result);
            if (result == null) {
            } else if (result.isEmpty()) {

            } else {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getBoolean("status")) {
                        Toast.makeText(AddPropertyAct.this, getResources().getString(R.string.your_property_added_sucessfully), Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }


    }







}
