package com.light_mini.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.light_mini.mobilesafe.R;

public class SettingRelativeLayout extends RelativeLayout {

    private  TextView tv_title;
    private  TextView tv_der;
    private  CheckBox checkbox;
    private static  final String NAMESPACE="http://schemas.android.com/apk/res/com.light_mini.mobilesafe";
    private String mDesTitle;
    private String mDesOn;
    private String mDesOff;

    public SettingRelativeLayout(Context context) {
        super(context);
        initUI(context);
    }

    private void initUI(Context ctx) {
        View view =View.inflate(ctx, R.layout.setting_item_view,this);
        tv_title=view.findViewById(R.id.setting_tv_title);
        tv_der=view.findViewById(R.id.tv_der);
        checkbox=view.findViewById(R.id.checkBox);
        tv_title.setText(mDesTitle);
    }

    public SettingRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initUI(context);
    }

    private void initAttrs(AttributeSet attrs) {
        mDesTitle=attrs.getAttributeValue(NAMESPACE,"desTitle");
        mDesOff=attrs.getAttributeValue(NAMESPACE,"desOff");
        mDesOn=attrs.getAttributeValue(NAMESPACE,"desOn");

    }

    public SettingRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI(context);
    }
    public SettingRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(attrs);
        initUI(context);
    }
    public boolean isChecked(){
        return checkbox.isChecked();
    }
    public void setCheck(boolean isCheck){
        checkbox.setChecked(isCheck);
        tv_der.setText(isCheck?mDesOn:mDesOff);
    }
}
