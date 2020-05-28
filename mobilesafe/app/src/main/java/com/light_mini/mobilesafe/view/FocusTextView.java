package com.light_mini.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class FocusTextView extends androidx.appcompat.widget.AppCompatTextView {

    public FocusTextView(Context context) {
        this(context,null);
    }
    public FocusTextView(Context context, AttributeSet attr) {
        this(context,attr,0);
    }
    public FocusTextView(Context context,AttributeSet attr,int deftype) {
        super(context,attr,deftype);
    }

    @Override
    public boolean isFocused() {
        return  true;
    }
}
