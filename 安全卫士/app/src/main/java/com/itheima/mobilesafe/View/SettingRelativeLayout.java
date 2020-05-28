package com.itheima.mobilesafe.View;
import android.widget.RelativeLayout;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.itheima.mobilesafe.R;
import android.widget.TextView;
import android.widget.CheckBox;

public class SettingRelativeLayout extends RelativeLayout
{

		private View view;

		private TextView tv_title;

		private TextView tv_der;

		private CheckBox checkbox;
    public SettingRelativeLayout(Context ctx)
		{
				this(ctx,null);
		}
		public SettingRelativeLayout(Context ctx,AttributeSet attr)
		{
				this(ctx,attr,0);
		}
		public SettingRelativeLayout(Context ctx,AttributeSet attr,int deftype)
		{
				super(ctx,attr,deftype);
				view=View.inflate(ctx,R.layout.setting_item_view,this);
				
				tv_title=view.findViewById(R.id.settingitemview_tv_title);
				tv_der=view.findViewById(R.id.settingitemview_tv_der);
				checkbox=view.findViewById(R.id.settingitemviewCheckBox);
		}
		public boolean isChecked()
		{
				return checkbox.isChecked();
		}
		public void setCheck(boolean ischeck)
		{
				checkbox.setChecked(ischeck);
				tv_der.setText(ischeck?"已开启":"已关闭");
		}
}
