package main.com.ngrewards.marchant.rent;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.com.ngrewards.R;
import main.com.ngrewards.Utils.Tools;
import main.com.ngrewards.databinding.ActivityAddPropertyBinding;
import main.com.ngrewards.marchant.activity.StartYourListing;
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
    String categoryId="",amenitiesString="";
    RecyclerView rvAmenities;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this, R.layout.activity_add_property);
       initViews();
    }

    private void initViews() {

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




        getCategory();
        getPropertyAmenities();
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

}
