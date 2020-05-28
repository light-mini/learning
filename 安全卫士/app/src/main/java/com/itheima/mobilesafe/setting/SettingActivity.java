package com.itheima.mobilesafe.setting;
import android.app.Activity;
import android.os.Bundle;
import com.itheima.mobilesafe.R;
import android.view.View.OnClickListener;
import android.view.View;
import android.database.sqlite.SQLiteDatabase;
import android.database.DatabaseUtils;
import android.content.SharedPreferences;
import android.widget.Switch;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.CheckBox;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.Adapter;

public class SettingActivity extends Activity implements OnClickListener,OnCheckedChangeListener
{

		private int placestyle;

		private int toastposision;

		private boolean booupdate;

		private boolean booplace;

		private boolean boointercept;

		private boolean booapplock;

		@Override
		public void onCheckedChanged(CompoundButton p1, boolean p2)
		{
				switch(p1.getId())
				{
						case R.id.actsetting_update:
								
								prefrences.edit().putBoolean("autoupdate",p2).commit();
								break;
						case R.id.actsetting_place:
								
								prefrences.edit().putBoolean("place",p2).commit();
								break;
						case R.id.actsetting_intercapt:
								prefrences.edit().putBoolean("intercept",p2).commit();
								break;
						case R.id.actsetting_applock:
								prefrences.edit().putBoolean("applock",p2).commit();
								break;
				}
				initDateAndFlush();
		}

		

		private SharedPreferences prefrences;
		private TextView tv_update,tv_place,tv_placestyle,
		tv_toastposision,tv_intercept,tv_applock;
		private CheckBox supdate,splace,
		sintercept,sapplock;
		private ImageButton stoastposison,splacestyle;
		@Override
		public void onClick(View p1)
		{
				int id = p1.getId();
				if(id==R.id.actsetting_ib_selectplacestyle)
				{
						//Intent placestyle = new Intent(SettingActivity.this,);
						//startActivityForResult(placestyle, 2);
						AlertDialog.Builder dialog=new AlertDialog.Builder(this);
						dialog.setTitle("选择风格");
						dialog.setItems(new String[]{"透明","暗黑","骚白"}, new DialogInterface.OnClickListener(){

										@Override
										public void onClick(DialogInterface p1, int p2)
										{
												prefrences.edit().putInt("placestyle",p2).commit();
												initDateAndFlush();
										}
								});
						dialog.setCancelable(true);
						dialog.show();
				}
				else
				{
						//Intent toastposision = new Intent(SettingActivity.this,);
						//startActivityForResult(toastposision, 3);
						AlertDialog.Builder dialog=new AlertDialog.Builder(this);
						dialog.setTitle("选择提示框位置");
						dialog.setItems(new String[]{"底部","居中","顶部"}, new DialogInterface.OnClickListener(){

										@Override
										public void onClick(DialogInterface p1, int p2)
										{
												prefrences.edit().putInt("toastposision",p2).commit();
												initDateAndFlush();
										}
								});
						dialog.setCancelable(true);
						dialog.show();
				}
				
		}

		private void flushUI()
		{
				tv_update.setText(booupdate ?"已开启": "已关闭");
				supdate.setChecked(booupdate);
				tv_place.setText(booplace?"已开启":"已关闭");
				splace.setChecked(booplace);
				tv_intercept.setText(boointercept?"已开启":"已关闭");
				sintercept.setChecked(boointercept);
				tv_applock.setText(booapplock?"已开启":"已关闭");
				sapplock.setChecked(booapplock);
				
				String text="";
				switch (placestyle)
				{
						case 0:
								text="透明";
								break;
						case 1:
								text="酷黑";
								break;
						case 2:
								text="骚白";
								break;
				}
				tv_placestyle.setText(text);
				text="";
				switch (toastposision)
				{
						case 0:
								text="底部";
								break;
						case 1:
								text="居中";
								break;
						case 2:
								text="顶部";
								break;
				}
				tv_toastposision.setText(text);
		}
		

		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_setting);
				prefrences=getSharedPreferences("setting",MODE_PRIVATE);
				initUI();
				initDateAndFlush();
		}

		private void initDateAndFlush()
		{
				initData();
				flushUI();
		}

		private void initUI()
		{
				tv_update=findViewById(R.id.actsetting_tv_update);
				tv_place=findViewById(R.id.actsetting_tv_place);
				tv_placestyle=findViewById(R.id.actsetting_tv_placestyle);
				tv_toastposision=findViewById(R.id.actsetting_tv_toastposision);
				tv_intercept=findViewById(R.id.actsetting_tv_intercept);
				tv_applock=findViewById(R.id.actsetting_tv_applock);
				
				supdate=findViewById(R.id.actsetting_update);
				splace=findViewById(R.id.actsetting_place);
				splacestyle=findViewById(R.id.actsetting_ib_selectplacestyle);
				stoastposison=findViewById(R.id.activitysetting_ib_selectposision);
				sintercept=findViewById(R.id.actsetting_intercapt);
				sapplock=findViewById(R.id.actsetting_applock);
				
				supdate.setOnCheckedChangeListener(this);
				splace.setOnCheckedChangeListener(this);
				splacestyle.setOnClickListener(this);
				stoastposison.setOnClickListener(this);
				sintercept.setOnCheckedChangeListener(this);
				sapplock.setOnCheckedChangeListener(this);
				
		}

		private void initData()
		{
				booupdate  = prefrences.getBoolean("autoupdate", false);
				booplace=prefrences.getBoolean("place",false);
				boointercept=prefrences.getBoolean("intercept",false);
				booapplock=prefrences.getBoolean("applock",false);
				
				placestyle  = prefrences.getInt("placestyle", 0);
				toastposision  = prefrences.getInt("toastposision", 0);
		}

}
