package com.itheima.mobilesafe;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.itheima.mobilesafe.setting.SettingActivity;

public class MainActivity extends Activity
{

		private String[] mTitles;
		private int[] mDrawableIds;

		private GridView gv;
		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);
				initUI();
				initData();
		}

		private void initData()
		{
				mTitles = new String[]
				{"手机防盗","通信卫士","软件管理","进程管理","流量管理","手机","缓存清理","高级工具","设置中心"};
				mDrawableIds = new int[]
				{
						R.drawable.home_safe,R.drawable.home_callmsgsafe,R.drawable.home_apps,
						R.drawable.home_taskmanager,R.drawable.home_netmanager,R.drawable.home_trojan,
						R.drawable.home_sysoptimize,R.drawable.home_tools,R.drawable.home_settings
				};
				gv.setAdapter(new MyAdapter());
				gv.setOnItemClickListener(new OnItemClickListener(){

								@Override
								public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
								{
										Intent it=null;
										switch (p3)
										{
												case 0:
														it = new Intent(MainActivity.this,SplashActivity.class );
														break;
												case 1:
														it = new Intent(MainActivity.this,SplashActivity.class );
														break;
												case 2:
														it = new Intent(MainActivity.this,SplashActivity.class );
														break;
												case 3:
														it = new Intent(MainActivity.this, SplashActivity.class);
														break;
												case 4:
														it = new Intent(MainActivity.this,SplashActivity.class );
														break;
												case 5:
														it = new Intent(MainActivity.this, SplashActivity.class);
														break;
												case 6:
														it = new Intent(MainActivity.this,SplashActivity.class );
														break;
												case 7:
														it = new Intent(MainActivity.this,SplashActivity.class );
														break;
												case 8:
														it = new Intent(MainActivity.this,SettingActivity.class );
														break;
												default:
														break;
										}
										startActivity(it);
								}
						});

		}

		private void initUI()
		{
				gv = findViewById(R.id.gv_main);
		}
    private class MyAdapter extends BaseAdapter
		{

				@Override
				public int getCount()
				{
						return mTitles.length;
				}

				@Override
				public Object getItem(int p1)
				{
						return null;
				}

				@Override
				public long getItemId(int p1)
				{
						return p1;
				}

				@Override
				public View getView(int p1, View p2, ViewGroup p3)
				{
						View v=null;
						if (p2 == null)
						{
								v = getLayoutInflater().from(getApplicationContext()).inflate(R.layout.item, null);

						}
						else
						{
								v = p2;
						}
						ImageView icon=v.findViewById(R.id.iv_icon);
						TextView title=v.findViewById(R.id.tv_title);
						icon.setImageResource(mDrawableIds[p1]);
						title.setText(mTitles[p1]);
						return v;
				}


		}
}
