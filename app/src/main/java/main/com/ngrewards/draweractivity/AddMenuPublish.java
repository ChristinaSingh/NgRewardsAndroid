package main.com.ngrewards.draweractivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.androidnetworking.AndroidNetworking;
//import com.androidnetworking.common.Priority;
//import com.androidnetworking.error.ANError;
//import com.androidnetworking.interfaces.JSONObjectRequestListener;
//import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import main.com.ngrewards.R;
import main.com.ngrewards.activity.AlertDailoge;
import main.com.ngrewards.activity.ItemOrderPaySuccessFully;
import main.com.ngrewards.activity.MerchantMenuSetting;
import main.com.ngrewards.constant.BaseUrl;
import main.com.ngrewards.constant.MultipartUtility;
import main.com.ngrewards.constant.MySession;
import main.com.ngrewards.constant.Myapisession;
import main.com.ngrewards.restapi.ApiClient;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMenuPublish extends AppCompatActivity implements View.OnClickListener {
    private String TAG ="AddMenuPublish";
    private final int GALLERY = 1;
    private String id_item_string;
    private String name_item_string;
    private TextView add_menu_txt;
    private ImageView uploadimg;
    private EditText tital_name_et, offer_desc, offer_price;
    private Button publish_product;
    private RelativeLayout backlay;
    private String ImagePath;
    private String tital_name_str;
    private String offer_desc_str;
    private String offer_price_str;
    private ProgressBar progresbar;
    private MySession mySession;
    private Myapisession myapisession;
    private String user_id;
    private String update_string;
    private String requestURL;
    private String filePathString;
    private File file;
    private String extension;
    private String timeStamp;
    private byte[] imageBytes;
    private Uri selectedImage;
    private String publish_string;
    private String discription_string;
    private String title_item_string;
    private String price_item_string;
    private String image_item_string;
    private String complete_image;
    private String menu_id_string;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_item_add);

        mySession = new MySession(this);
        myapisession = new Myapisession(this);

        String user_log_data = mySession.getKeyAlldata();

        if (user_log_data == null) {

        } else {

            try {
                JSONObject jsonObject = new JSONObject(user_log_data);
                String message = jsonObject.getString("status");
                if (message.equalsIgnoreCase("1")) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                    user_id = jsonObject1.getString("id");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (savedInstanceState == null) {

            Bundle extras = getIntent().getExtras();

            if (extras == null) {

                id_item_string = null;


            } else {

                id_item_string = extras.getString("id_item");
                name_item_string = extras.getString("name_item");
                update_string = extras.getString("update");
                publish_string = extras.getString("publish");
                discription_string = extras.getString("discription");
                title_item_string = extras.getString("title_item");
                price_item_string = extras.getString("price_item");
                image_item_string = extras.getString("image_item");
                menu_id_string = extras.getString("menu_id");

            }

        } else {

            name_item_string = (String) savedInstanceState.getSerializable("name_item");
            id_item_string = (String) savedInstanceState.getSerializable("id_item");
        }

        SetupUi();
    }

    private void SetupUi() {

        add_menu_txt = (TextView) findViewById(R.id.add_menu_txt);
        tital_name_et = (EditText) findViewById(R.id.tital_name_et);
        offer_desc = (EditText) findViewById(R.id.offer_desc);
        uploadimg = (ImageView) findViewById(R.id.uploadimg);
        offer_price = (EditText) findViewById(R.id.offer_price);
        progresbar = (ProgressBar) findViewById(R.id.progresbar);
        backlay = (RelativeLayout) findViewById(R.id.backlay);


        add_menu_txt.setText(name_item_string);
        publish_product = (Button) findViewById(R.id.publish_product);
        uploadimg.setOnClickListener(this);
        publish_product.setOnClickListener(this);
        backlay.setOnClickListener(this);

        if (!title_item_string.equals("")) {
            tital_name_et.setText(title_item_string);
        }

        if (!discription_string.equals("")) {
            offer_desc.setText(discription_string);
        }

        if (!price_item_string.equals("")) {
            offer_price.setText(price_item_string);
        }

        if (!image_item_string.equals("")) {

            complete_image = BaseUrl.image_baseurl + image_item_string;
            Glide.with(getApplicationContext()).load(complete_image).into(uploadimg);
        }

        if (publish_string.equals("edit")) {

            publish_product.setText("Update");
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.uploadimg:

                selectImage();

                break;

            case R.id.publish_product:


                if (publish_string.equals("edit")) {

                    ApiCallEdit();

                } else {

                    ApiCall();
                }

                break;


            case R.id.backlay:

                finish();

                break;
        }
    }

    private void ApiCallEdit() {

        tital_name_str = tital_name_et.getText().toString();
        offer_desc_str = offer_desc.getText().toString();
        offer_price_str = offer_price.getText().toString();

        ApiUpdateCall(tital_name_str, offer_desc_str, offer_price_str);

    }


    public void ApiUpdateCall(String tital_name_str, String offer_desc_str, String offer_price_str) {
        progresbar.setVisibility(View.GONE);
        MultipartBody.Part filePart;
        if (file!=null) {
            filePart = MultipartBody.Part.createFormData("menu_image",
                    file.getName(), RequestBody.create(MediaType.parse("menu_image/*"), file));
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
            filePart = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }
      /*  if (!ThumbnailPath.equalsIgnoreCase("")) {
            File file = new File(ThumbnailPath);
            filePart = MultipartBody.Part.createFormData("video_thumb_img",
                    file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
            filePart = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }*/
        Log.e("SENDER ", "ID" + user_id);
        Log.e("SENDER-- IDtype", id_item_string);
        Log.e("SENDER-- IDreceiver_id", tital_name_str);
        Log.e("SENDER-- IDchat_message", offer_desc_str);
        Log.e("SENDER-- IDtimezone", offer_price_str);
        Log.e("SENDER-- IDdate", menu_id_string);

        RequestBody merchant_id = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), id_item_string);
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), tital_name_str);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), offer_desc_str);
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), offer_price_str);
        RequestBody menu_id = RequestBody.create(MediaType.parse("text/plain"), menu_id_string);



        Call<ResponseBody> loginCall = ApiClient.getApiInterface().updateCallApi(merchant_id, id, title, description, price, menu_id, filePart);
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {     
                progresbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseData);
                        Log.e("update call Response", "" + responseData);
                        status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            Intent in = new Intent(AddMenuPublish.this, MerchantMenuSetting.class);
                            startActivity(in);
                            finish();
                            Toast.makeText(AddMenuPublish.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                        } else {



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
                progresbar.setVisibility(View.GONE);

                call.cancel();
                Log.e(TAG, "Test Response :" + t.getCause());
                Log.e(TAG, "Test Response :" + t.getLocalizedMessage());
                Log.e(TAG, "Test Response :" + t.getMessage());
            }
        });
    }





/*
    private void ApiUpdateCall(String tital_name_str, String offer_desc_str, String offer_price_str) {
        AndroidNetworking.upload("https://myngrewards.com/wp-content/plugins/webservice/update_item.php")
                .addMultipartFile("menu_image", file)

                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {

                        progresbar.setVisibility(View.VISIBLE);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            status = response.getString("status");


                            if (status.equals("1")) {

                                progresbar.setVisibility(View.GONE);


                                Intent in = new Intent(AddMenuPublish.this, MerchantMenuSetting.class);
                                startActivity(in);
                                finish();

                                Toast.makeText(AddMenuPublish.this, "Update Successfully", Toast.LENGTH_SHORT).show();


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError error) {

                        progresbar.setVisibility(View.GONE);
                        // handle error
                    }
                });

    }
*/

    private void ApiCall() {

        tital_name_str = tital_name_et.getText().toString();
        offer_desc_str = offer_desc.getText().toString();
        offer_price_str = offer_price.getText().toString();

        if (tital_name_str == null || tital_name_str.equalsIgnoreCase("")) {
            Toast.makeText(AddMenuPublish.this, getResources().getString(R.string.entertite), Toast.LENGTH_LONG).show();
        } else if (offer_desc_str == null || offer_desc_str.equalsIgnoreCase("")) {
            Toast.makeText(AddMenuPublish.this, getResources().getString(R.string.enterdesc), Toast.LENGTH_LONG).show();

        } else if (file == null || file.equals("")) {
            Toast.makeText(AddMenuPublish.this, getResources().getString(R.string.selectimage), Toast.LENGTH_LONG).show();

        } else if (offer_price_str == null || offer_price_str.equalsIgnoreCase("")) {
            Toast.makeText(AddMenuPublish.this, getResources().getString(R.string.enterprice), Toast.LENGTH_LONG).show();

        } else {

            new AddOfferProdAsc().execute();
        }
    }

    private void selectImage() {

        try {

            final Dialog dialogSts = new Dialog(AddMenuPublish.this, R.style.DialogSlideAnim);
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
                    chooseFile();

                }
            });

            camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogSts.dismiss();
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chooseFile() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
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
        ContextWrapper cw = new ContextWrapper(AddMenuPublish.this);
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

    public void decodeFile(String filePath, String fileName) {

        //Decode image size
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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == AddMenuPublish.RESULT_CANCELED) {

            return;
        }

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case 1:

                    if (data != null) {

                        Uri selectedImage = data.getData();

                        try {

                            String[] filePathColumn = {MediaStore.Images.Media.DATA};
                            android.database.Cursor cursor = AddMenuPublish.this.getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);

                            if (cursor == null)
                                return;

                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            filePathString = cursor.getString(columnIndex);
                            cursor.close();

                            uploadimg.setImageURI(selectedImage);
                            file = new File(filePathString);

                            Log.e("file1234", String.valueOf(file));
                            String fileName = file.getName();
                            Log.e("file", fileName);

                            String[] filenameArray = fileName.split("\\.");
                            extension = filenameArray[filenameArray.length - 1];
                            Log.e("fileName", extension);
                            Long tsLong = System.currentTimeMillis() / 1000;
                            timeStamp = tsLong.toString();
                            imageBytes = convertImageToByte(selectedImage, extension);
                            Log.e("fileName", "" + imageBytes.length);


                        } catch (Exception e) {

                            e.printStackTrace();

                        }

                    }

                    break;

                case 2:

                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    String cameraPath = saveToInternalStorage(photo);

                    Log.e("PATH Camera", "" + cameraPath);
                    //  String ImagePath = getPath(selectedImage);

                    decodeFile(cameraPath, "");
                    break;

            }
        }
    }

    public byte[] convertImageToByte(Uri uri, String extension) {
        byte[] data = null;

        try {
            ContentResolver cr = AddMenuPublish.this.getBaseContext().getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (extension.equals("jpg")) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            } else if (extension.equals("png")) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            }
            data = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return data;
    }

    public class AddOfferProdAsc extends AsyncTask<String, String, String> {

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

            requestURL = BaseUrl.baseurl + "add_item.php?";

            Log.e("file!!!", String.valueOf(file));


            try {

                MultipartUtility multipart = new MultipartUtility(requestURL, charset);
                multipart.addFormField("menu_id", id_item_string);
                multipart.addFormField("title", tital_name_str);
                multipart.addFormField("description", offer_desc_str);
                multipart.addFormField("price", offer_price_str);
                multipart.addFormField("merchant_id", user_id);

                if (file == null || file.equals("")) {

                } else {

                    multipart.addFilePart("menu_image", file);
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
            progresbar.setVisibility(View.GONE);
            Log.e("Add Product ", " >> " + result);

            if (result == null) {
            } else if (result.isEmpty()) {

            } else {

                try {

                    JSONObject jsonObject = new JSONObject(result);

                    if (jsonObject.getString("status").equalsIgnoreCase("1")) {

                        Toast.makeText(AddMenuPublish.this, "Add Item Sucessfully", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(AddMenuPublish.this, MerchantMenuSetting.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }

}
