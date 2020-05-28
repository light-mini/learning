package com.itheima.mobilesafe;
 
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
import android.widget.Toast;
import com.itheima.mobilesafe.Utils.StreamUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.SharedPreferences;

public class SplashActivity extends Activity
{
		private static final int UPDATE = 1;

		private static final int ENTER_MAIN = 0;

		private static final int JSON_ERORR = 2;

		private static final int IO_ERROR = 3;
		
		private String mDownloadUrl;

		private String versionDers;
		

    private TextView tv_version; 
		private Handler handler=new Handler()
		{

				@Override
				public void handleMessage(Message msg)
				{
						switch(msg.what)
						{
								case UPDATE:
										showUpdateDialog();
										break;
								case JSON_ERORR:
										enterMain();
										Toast.makeText(getApplication(), "jsonerorr", Toast.LENGTH_LONG).show();
										finish();
										break;
								case IO_ERROR:
										Toast.makeText(getApplication(), "ioerorr", Toast.LENGTH_LONG).show();
										finish();
										enterMain();
										break;
								case ENTER_MAIN:
										enterMain();
										finish();
										break;
						}
				}

				

				private void showUpdateDialog()
				{
						AlertDialog.Builder builder=new AlertDialog.Builder(SplashActivity.this);
						builder.setIcon(R.drawable.ic_launcher);
						builder.setTitle("发现新版本");
						builder.setMessage(versionDers);
						builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener(){

										@Override
										public void onClick(DialogInterface p1, int p2)
										{
												//下载新版本
												downloadApk();
										}

										
								});
						builder.setNeutralButton("下次再说", new DialogInterface.OnClickListener(){

										@Override
										public void onClick(DialogInterface p1, int p2)
										{
												//跳转到mainact
												enterMain();
												finish();
										}
								});
						builder.show();
				}

		};

		private SharedPreferences prefrences;
		private void downloadApk()
		{
				String path=Environment.getExternalStorageDirectory() + File.separator + "update.apk";
				HttpUtils utils=new HttpUtils();
				utils.download(mDownloadUrl, path, new RequestCallBack(){

								@Override
								public void onSuccess(ResponseInfo p1)
								{
										Log.d("Download","成功");
										File file=(File) p1.result;
										installAPK(file);
								}

								

								@Override
								public void onFailure(HttpException p1, String p2)
								{
										Log.d("Download","失败");
										Toast.makeText(SplashActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
										enterMain();
								}
								
						});
		}
		private void installAPK(File file)
		{
				Intent install=new Intent("android.intent.action.View");
				install.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
				startActivityForResult(install,1);
		}

		private void enterMain()
		{
				startActivity(new Intent(SplashActivity.this, MainActivity.class));
		}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //第一种方法去头
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
				prefrences=getSharedPreferences("setting",MODE_PRIVATE);
        initUI();
    }

    private void initUI()
    {
        initData();
    }

    private void initData()
    {
				if(prefrences.getBoolean("autoupdate",true)){
        tv_version=findViewById(R.id.tv_version_name);
        //获取版本号
        String versionName = getVersionName();
				tv_version.setText("版本号:" + versionName);
        //检查更新
        getUpdate();
				}else{
						enterMain();
						finish();
				}
    }

    private void getUpdate()
    {
				final Message msg=new Message();
        new Thread(new Runnable(){
                @Override
                public void run()
                {
										long starttime=System.currentTimeMillis();
                    try
                    {
                        URL url=new URL("http://localhost:8080/version.json");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        
                        connection.setConnectTimeout(2000);
                        //默认就是get
                        connection.setRequestMethod("GET");
                        
                        int code=connection.getResponseCode();
                        if(code==200)
                        {
														int len=connection.getContentLength();
                            String result= StreamUtils.streamToString(connection.getInputStream(),len);
														Log.d("json",result);
														
														JSONObject jso=new JSONObject(result);
														//jsonexception
														String versionName=jso.getString("versionName");
														String versionCode=jso.getString("versionCode");
														versionDers=jso.getString("versionDers");
														mDownloadUrl=jso.getString("versionUrl");
														if (getVersionCode() < Integer.parseInt(versionCode))
														{
																msg.what=UPDATE;
														}else
														{
																msg.what=ENTER_MAIN;
																
														}
                        }
                    }
										catch(JSONException je){
												je.printStackTrace();
												msg.what=JSON_ERORR;
										}
                    catch (IOException e)
                    {
												e.printStackTrace();
												msg.what=IO_ERROR;
										}finally{
												//计算网络请求时长是否小于4秒实现延迟4秒进入
												long curtime=System.currentTimeMillis()-starttime;
												if(curtime<4000)
												{
														try
														{
																Thread.sleep(4000 - curtime);
														}
														catch (InterruptedException e)
														{e.printStackTrace();}
												}
												handler.sendEmptyMessage(msg.what);
										}
                }
            }).start();
    }
    /**
    *@return 返回空则失败
    */
    private String getVersionName()
    {
        PackageManager pmanager=getPackageManager();
        try
        {
            PackageInfo pkginfo=pmanager.getPackageInfo(getPackageName(), 0);
            return pkginfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {e.printStackTrace();}
        return null;
    }
		private int getVersionCode()
    {
        PackageManager pmanager=getPackageManager();
        try
        {
            PackageInfo pkginfo=pmanager.getPackageInfo(getPackageName(), 0);
            return pkginfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {e.printStackTrace();}
        return 0;
    }

} 
