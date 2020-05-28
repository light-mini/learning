package com.light_mini.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.light_mini.mobilesafe.Util.MyToast;
import com.light_mini.mobilesafe.Util.StreamUtlis;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.light_mini.mobilesafe.Util.Constant.ENTER_MAIN;
import static com.light_mini.mobilesafe.Util.Constant.UPDATE;

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
                    showUpdateDialog();
                    break;
                    case ENTER_MAIN:
                        enterMain();
                    break;
            }
        }
    };

    private void showUpdateDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("新版本发布");
        builder.setMessage(mVersionDer);
        builder.setPositiveButton("确定更新",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadAPK();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterMain();
            }
        });
        builder.show();
    }


    private String TAG="Splash";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initUI();
    }

    private void initUI() {
        TextView tv_ver=findViewById(R.id.versionName);
        tv_ver.setText("版本："+getVersionName());
        initData();
    }

    private void initData() {
        final Message msg=new Message();
        final long time=System.currentTimeMillis();
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
                            msg.what=UPDATE;
                        }else{
                            msg.what=ENTER_MAIN;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    msg.what=ENTER_MAIN;
                    MyToast.showShortMeg(SplashActivity.this,"MalformedURLException");
                } catch (IOException e) {
                    e.printStackTrace();
                    msg.what=ENTER_MAIN;
                    MyToast.showShortMeg(SplashActivity.this,"IOException");
                } catch (JSONException e) {
                    e.printStackTrace();
                    msg.what=ENTER_MAIN;
                    MyToast.showShortMeg(SplashActivity.this,"JSONException");
                }finally {
                    long currTime=System.currentTimeMillis()-time;
                    if (currTime<4000){
                        try {
                            Thread.sleep(4000-currTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.sendEmptyMessage(msg.what);
                }
            }
        }).start();
    }

    private void enterMain() {
        Intent it=new Intent(SplashActivity.this,MainActivity.class);
        startActivity(it);
        finish();
    }

    private void downloadAPK() {
        Log.d(TAG, "updateAPK: 正在下载");
        String dPath= Environment.getDataDirectory()+ File.separator+"update"+mVersionCode+".apk";
        HttpUtils utils=new HttpUtils();
        utils.download(mDownloadUrl, dPath, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                File result = responseInfo.result;
                Log.d(TAG, "onSuccess: 下载成功");
                installAPK(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.d(TAG, "onFailure: 下载失败");
            }
        });
    }

    private void installAPK(File result) {
        Intent it=new Intent("android.intent.action.View");
        it.setDataAndType(Uri.fromFile(result),"application/vnd.android.package-archive");
        startActivityForResult(it,UPDATE);
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
    public String getVersionName() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
