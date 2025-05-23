package main.com.ngrewards;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blikoon.qrcodescanner.camera.CameraManager;
import com.blikoon.qrcodescanner.decode.DecodeImageCallback;
import com.blikoon.qrcodescanner.decode.DecodeImageThread;
import com.blikoon.qrcodescanner.decode.DecodeManager;
import com.blikoon.qrcodescanner.decode.InactivityTimer;
import com.blikoon.qrcodescanner.view.QrCodeFinderView;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import main.com.ngrewards.Utils.LocaleHelper;
import main.com.ngrewards.activity.ManualActivity;
import main.com.ngrewards.constant.MySession;
import main.com.ngrewards.qrcodes.CaptureActivityHandler;

public class QrCodeActivity extends AppCompatActivity implements Callback, OnClickListener {
    public static final int MSG_DECODE_SUCCEED = 1;
    public static final int MSG_DECODE_FAIL = 2;
    private static final int REQUEST_SYSTEM_PICTURE = 0;
    private static final int REQUEST_PICTURE = 1;
    private static final float BEEP_VOLUME = 0.10f;
    private static final long VIBRATE_DURATION = 200L;
    private final DecodeManager mDecodeManager = new DecodeManager();
    private final String GOT_RESULT = "com.blikoon.qrcodescanner.got_qr_scan_relult";
    private final String ERROR_DECODING_IMAGE = "com.blikoon.qrcodescanner.error_decoding_image";
    private final String LOGTAG = "QRScannerQRCodeActivity";
    private final DecodeImageCallback mDecodeImageCallback = new DecodeImageCallback() {
        @Override
        public void decodeSucceed(Result result) {
            //Got scan result from scaning an image loaded by the user
            Log.d(LOGTAG, "Decoded the image successfully :" + result.getText());
            Intent data = new Intent();
            data.putExtra(GOT_RESULT, result.getText());
            setResult(Activity.RESULT_OK, data);
            finish();
        }

        @Override
        public void decodeFail(int type, String reason) {
            Log.d(LOGTAG, "Something went wrong decoding the image :" + reason);
            Intent data = new Intent();
            data.putExtra(ERROR_DECODING_IMAGE, reason);
            setResult(Activity.RESULT_CANCELED, data);
            finish();
        }
    };
    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener mBeepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
    private RelativeLayout backlay;
    private CaptureActivityHandler mCaptureActivityHandler;
    private boolean mHasSurface;
    private boolean mPermissionOk;
    private InactivityTimer mInactivityTimer;
    private QrCodeFinderView mQrCodeFinderView;
    private SurfaceView mSurfaceView;
    private View mLlFlashLight;
    private MediaPlayer mMediaPlayer;
    private boolean mPlayBeep;
    private boolean mVibrate;
    private boolean mNeedFlashLightOpen = true;
    private ImageView mIvFlashLight;
    private TextView mTvFlashLightText;
    private Executor mQrCodeExecutor;
    private Handler mHandler;
    private Context mApplicationContext;
    private MySession mySession;

    private static Intent createIntent(Context context) {
        Intent i = new Intent(context, QrCodeActivity.class);
        return i;
    }

    public static void launch(Context context) {
        Intent i = createIntent(context);
        context.startActivity(i);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        mySession = new MySession(this);
        initView();
        initData();
        mApplicationContext = getApplicationContext();
        backlay = findViewById(R.id.backlay);
        backlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkPermission() {
        boolean hasHardware = checkCameraHardWare(this);
        if (hasHardware) {
            if (!hasCameraPermission()) {
                findViewById(R.id.qr_code_view_background).setVisibility(View.VISIBLE);
                mQrCodeFinderView.setVisibility(View.GONE);
                mPermissionOk = false;
            } else {
                mPermissionOk = true;
            }
        } else {
            mPermissionOk = false;
            finish();
        }
    }

    private void initView() {
        TextView tvPic = (TextView) findViewById(R.id.qr_code_header_black_pic);
        mIvFlashLight = (ImageView) findViewById(R.id.qr_code_iv_flash_light);
        mTvFlashLightText = (TextView) findViewById(R.id.qr_code_tv_flash_light);
        mQrCodeFinderView = (QrCodeFinderView) findViewById(R.id.qr_code_view_finder);
        mSurfaceView = (SurfaceView) findViewById(R.id.qr_code_preview_view);
        mLlFlashLight = findViewById(R.id.qr_code_ll_flash_light);
        mHasSurface = false;
        mIvFlashLight.setOnClickListener(this);
        tvPic.setOnClickListener(this);
    }

    private void initData() {
        CameraManager.init(this);
        mInactivityTimer = new InactivityTimer(QrCodeActivity.this);
        mQrCodeExecutor = Executors.newSingleThreadExecutor();
        mHandler = new WeakHandler(this);
    }

    private boolean hasCameraPermission() {
        PackageManager pm = getPackageManager();
        return PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.CAMERA", getPackageName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
        if (!mPermissionOk) {
            mDecodeManager.showPermissionDeniedDialog(this);
            return;
        }
        SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
        turnFlashLightOff();
        if (mHasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        mPlayBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            mPlayBeep = false;
        }
        initBeepSound();
        mVibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCaptureActivityHandler != null) {
            mCaptureActivityHandler.quitSynchronously();
            mCaptureActivityHandler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        if (null != mInactivityTimer) {
            mInactivityTimer.shutdown();
        }
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.qr_code_camera_not_found), Toast.LENGTH_SHORT).show();
            finish();
            return;
        } catch (RuntimeException re) {
            re.printStackTrace();
            mDecodeManager.showPermissionDeniedDialog(this);
            return;
        }
        mQrCodeFinderView.setVisibility(View.VISIBLE);
        mSurfaceView.setVisibility(View.VISIBLE);
        mLlFlashLight.setVisibility(View.VISIBLE);
        findViewById(R.id.qr_code_view_background).setVisibility(View.GONE);
        if (mCaptureActivityHandler == null) {
            mCaptureActivityHandler = new CaptureActivityHandler(QrCodeActivity.this);
        }
    }

    private void restartPreview() {
        if (null != mCaptureActivityHandler) {
            mCaptureActivityHandler.restartPreviewAndDecode();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    private boolean checkCameraHardWare(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!mHasSurface) {
            mHasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mHasSurface = false;
    }

    public Handler getCaptureActivityHandler() {
        return mCaptureActivityHandler;
    }

    private void initBeepSound() {
        if (mPlayBeep && mMediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(mBeepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mMediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mMediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                mMediaPlayer = null;
            }
        }
    }

    private void playBeepSoundAndVibrate() {
        if (mPlayBeep && mMediaPlayer != null) {
            mMediaPlayer.start();
        }
        if (mVibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.qr_code_iv_flash_light) {
            if (mNeedFlashLightOpen) {
                turnFlashlightOn();
            } else {
                turnFlashLightOff();
            }

        } else if (v.getId() == R.id.qr_code_header_black_pic) {
            if (!hasCameraPermission()) {
                mDecodeManager.showPermissionDeniedDialog(this);
            } else {
                openSystemAlbum();
            }

        }

    }

    private void openSystemAlbum() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_SYSTEM_PICTURE);
    }

    private void turnFlashlightOn() {
        mNeedFlashLightOpen = false;
        mTvFlashLightText.setText(getString(R.string.qr_code_close_flash_light));
        mIvFlashLight.setBackgroundResource(R.drawable.flashlight_turn_off);
        CameraManager.get().setFlashLight(true);
    }

    private void turnFlashLightOff() {
        mNeedFlashLightOpen = true;
        mTvFlashLightText.setText(getString(R.string.qr_code_open_flash_light));
        mIvFlashLight.setBackgroundResource(R.drawable.flashlight_turn_on);
        CameraManager.get().setFlashLight(false);
    }

    private void handleResult(String resultString) {
        if (TextUtils.isEmpty(resultString)) {
            mDecodeManager.showCouldNotReadQrCodeFromScanner(this, new DecodeManager.OnRefreshCameraListener() {
                @Override
                public void refresh() {
                    restartPreview();
                }
            });
        } else {
            //Got result from scanning QR Code with users camera
            Log.d(LOGTAG, "Got scan result from user loaded image :" + resultString);
            Intent data = new Intent();
            data.putExtra(GOT_RESULT, resultString);
            setResult(Activity.RESULT_OK, data);
            finish();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_PICTURE:
                finish();
                break;
            case REQUEST_SYSTEM_PICTURE:
                Uri uri = data.getData();
                String imgPath = getPathFromUri(uri);
                if (imgPath != null && !TextUtils.isEmpty(imgPath) && null != mQrCodeExecutor) {
                    mQrCodeExecutor.execute(new DecodeImageThread(imgPath, mDecodeImageCallback));
                }
                break;
        }
    }

    public String getPathFromUri(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    public void handleDecode(Result result) {
        mInactivityTimer.onActivity();
        Log.e("SCAN RST", " >> " + result.getText());
        playBeepSoundAndVibrate();
        if (null == result) {
            mDecodeManager.showCouldNotReadQrCodeFromScanner(this, () -> restartPreview());
        } else {
            String resultString = result.getText();
            Log.e("FETCHEDSCANDATA ", " " + resultString);

            //JSONObject jsonObject = null;
            if (resultString.contains("Member")) {
                Toast.makeText(QrCodeActivity.this, "Wrong QR Code!!!", Toast.LENGTH_SHORT).show();

       /*         try {
                    if (resultString != null && !resultString.equalsIgnoreCase("")) {
                        Log.e("TAG", "onActivityResultresultresultresult: " + result);
                        *//*{"murchant_name":"REACH","merchant_number":"ed58126","merchant_id":"3"}*//*
                        String arr[] = resultString.split(",");
                        //   JSONObject jsonObject = new JSONObject(result);
                        //   String murchant_name = jsonObject.getString("murchant_name");
                        String member_id = arr[4];
                        String usernameauto = arr[1];
                        String memname = (arr[2]);

                        Intent i = new Intent(QrCodeActivity.this, ManualActivity.class);
                        i.putExtra("merchant_id", member_id);
                        i.putExtra("merchant_name", memname);
                        i.putExtra("merchant_number", usernameauto);
                        i.putExtra("type", "paybill");
                        startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

            } else {
                try {

                    Log.e("TAG", "onActivityResultresultresultresult: " + result);
                    /*{"murchant_name":"REACH","merchant_number":"ed58126","merchant_id":"3"}*/
                    String[] arr = resultString.split(",");
                    //   JSONObject jsonObject = new JSONObject(result);
                    //   String murchant_name = jsonObject.getString("murchant_name");
                    Log.e("COME IN JSON", "onActivityResultresultresultresult" + arr.length);
                    String country_id = "";
                    String amount = "";
                    String member_id = arr[2];
                    String usernameauto = arr[1];
                    String memname = (arr[0]);
                    if (arr.length > 3) {
                        amount = arr[3];
                        Log.e("COME IN JSON", " <<amountamount" + amount);
                    }
                    if (arr.length > 4) {
                        country_id = arr[4];
                        Log.e("COME IN JSON", " <<country_id" + country_id);
                    }
                    //  jsonObject = new JSONObject(resultString);
                    Log.e("COME IN JSON", " <<" + resultString);
             /*   String mername = jsonObject.getString("murchant_name");
                if (jsonObject.getString("murchant_name") != null && !jsonObject.getString("murchant_name").equalsIgnoreCase("")) {
                    mername = mername.replace("?", "'");
                }*/
                    Log.e("COME IN JSON", " <<country_idcountry_idcountry_id" + country_id);
                    Log.e("COME IN JSON", " <<country_idcountry_idcountry_idmySession.getValueOf(MySession.CountryId) " + mySession.getValueOf(MySession.CountryId));

                    if (country_id.equalsIgnoreCase("")) {
                        Intent i = new Intent(QrCodeActivity.this, ManualActivity.class);
                        i.putExtra("merchant_id", member_id);
                        i.putExtra("merchant_name", memname);
                        i.putExtra("merchant_number", usernameauto);
                        i.putExtra("country_id", country_id);
                        i.putExtra("total_amount_due", amount);
                        i.putExtra("type", "paybill");
                        startActivity(i);
                    } else if (country_id.equalsIgnoreCase(mySession.getValueOf(MySession.CountryId))) {
                        Intent i = new Intent(QrCodeActivity.this, ManualActivity.class);
                        i.putExtra("merchant_id", member_id);
                        i.putExtra("merchant_name", memname);
                        i.putExtra("merchant_number", usernameauto);
                        //   i.putExtra("country_id", country_id);
                        i.putExtra("total_amount_due", amount);
                        i.putExtra("type", "paybill");
                        startActivity(i);
                    } else {
                        onBackPressed();
                        Toast.makeText(QrCodeActivity.this, "Invalid Country!!!", Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(QrCodeActivity.this, "Wrong QR Code!!!", Toast.LENGTH_SHORT).show();

              /*  if (resultString != null && !resultString.equalsIgnoreCase("")) {
                    List<String> datalist = Arrays.asList(resultString.split(","));
                    if (datalist != null && !datalist.isEmpty() && datalist.size() == 3) {
                        String mername = datalist.get(0);
                        if (datalist.get(0) != null && !datalist.get(0).equalsIgnoreCase("")) {
                            mername = mername.replace("?", "'");
                        }
                        Intent i = new Intent(QrCodeActivity.this, ManualActivity.class);
                        i.putExtra("merchant_id", datalist.get(2));
                        i.putExtra("merchant_name", mername);
                        i.putExtra("merchant_number", datalist.get(1));
                        i.putExtra("type","paybill");
                        startActivity(i);
                    }
                }
                e.printStackTrace();*/
                  /*  if (resultString != null && !resultString.equalsIgnoreCase("")) {
                        Log.e("TAG", "onActivityResultresultresultresult: " + result);
                        *//*{"murchant_name":"REACH","merchant_number":"ed58126","merchant_id":"3"}*//*
                        String arr[] = resultString.split(",");
                        //   JSONObject jsonObject = new JSONObject(result);
                        //   String murchant_name = jsonObject.getString("murchant_name");
                        String member_id = arr[4];
                        String usernameauto = arr[1];
                        String memname = (arr[2]);

                        Intent i = new Intent(QrCodeActivity.this, ManualActivity.class);
                        i.putExtra("merchant_id", member_id);
                        i.putExtra("merchant_name", memname);
                        i.putExtra("merchant_number", usernameauto);
                        i.putExtra("type", "paybill");
                        startActivity(i);
                    }*/


                    //  handleResult(resultString);

                }
            }


            //  handleResult(resultString);
        }
    }

    private static class WeakHandler extends Handler {
        private final WeakReference<QrCodeActivity> mWeakQrCodeActivity;
        private final DecodeManager mDecodeManager = new DecodeManager();

        public WeakHandler(QrCodeActivity imagePickerActivity) {
            super();
            this.mWeakQrCodeActivity = new WeakReference<>(imagePickerActivity);
        }

        // code to get result
        @Override
        public void handleMessage(Message msg) {
            Log.e("Scan Result", " >> " + msg);
            QrCodeActivity qrCodeActivity = mWeakQrCodeActivity.get();
            switch (msg.what) {
                case MSG_DECODE_SUCCEED:
                    Result result = (Result) msg.obj;
                    if (null == result) {
                        mDecodeManager.showCouldNotReadQrCodeFromPicture(qrCodeActivity);
                    } else {
                        String resultString = result.getText();
                        handleResult(resultString);

                    }
                    break;
                case MSG_DECODE_FAIL:
                    mDecodeManager.showCouldNotReadQrCodeFromPicture(qrCodeActivity);
                    break;
            }
            super.handleMessage(msg);
        }

        private void handleResult(String resultString) {
            Log.e("RESULT 2", " >>" + resultString);
            QrCodeActivity imagePickerActivity = mWeakQrCodeActivity.get();
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(resultString);
                Intent i = new Intent(imagePickerActivity, ManualActivity.class);
                i.putExtra("merchant_id", jsonObject.getString("user_id"));
                i.putExtra("merchant_name", jsonObject.getString("murchant_name"));
                i.putExtra("merchant_number", jsonObject.getString("merchant_number"));
                imagePickerActivity.startActivity(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}