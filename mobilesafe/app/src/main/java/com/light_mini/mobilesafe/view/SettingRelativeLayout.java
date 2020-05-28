package com.light_mini.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.light_mini.mobilesafe.R;

public class SettingRelativeLayout extends RelativeLayout {
    private final TextView tv_title;
    private final TextView tv_der;
    private final CheckBox checkbox;

    public SettingRelativeLayout(Context context) {
        this(context,null);
    }

    public SettingRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public SettingRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        View view =View.inflate(context, R.layout.setting_item_view,this);
        tv_title=view.findViewById(R.id.setting_tv_title);
        tv_der=view.findViewById(R.id.tv_der);
        checkbox=view.findViewById(R.id.checkBox);
    }
    public boolean isChecked(){
        return checkbox.isChecked();
    }
    public void setCheck(boolean isCheck){
        checkbox.setChecked(isCheck);
        tv_der.setText(isCheck?"已开启":"已关闭");
    }
}
