package com.light_mini.mobilesafe;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.light_mini.mobilesafe.Util.StreamUtlis;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends Activity {
    private String mVersionName;
    private String mVersionCode;
    private String mVersionDer;
    private String mDownloadUrl;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case UPDATE:

                    break;
                    case ENTER_MAIN:
                    break;
            }
        }
    };
    private  final int UPDATE=0;
    private final int ENTER_MAIN=1;
    private String TAG="Splash";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initUI();
    }

    private void initUI() {
        initData();
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url=new URL("http://192.168.2.105:8080/version.json");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(4000);
                    int code =connection.getResponseCode();
                    Log.d(TAG, "来女王成功"+code);
                    if (code==200){
                        InputStream in = connection.getInputStream();
                        String result = StreamUtlis.streamToString(in);
                        Log.d(TAG, result);
                        JSONObject jos=new JSONObject(result);
                        mVersionName = jos.getString("versionName");
                        mVersionCode=jos.getString("versionCode");
                        mVersionDer=jos.getString("versionDer");
                        mDownloadUrl=jos.getString("versionUrl");
                        if (getVersionCode()<Integer.parseInt(mVersionCode)){

                        }else{

                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.d(TAG, "MAL来女王成功");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "IO来女王成功");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "JSON来女王成功");
                }
            }
        }).start();
    }

    public int getVersionCode() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
