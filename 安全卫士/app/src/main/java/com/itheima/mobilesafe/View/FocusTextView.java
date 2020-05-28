package com.itheima.mobilesafe.View;
import android.content.Context;
import android.widget.TextView;
import android.util.AttributeSet;

public class FocusTextView extends TextView
{
    public FocusTextView(Context context)
		{
				super(context);
		}
		public FocusTextView(Context context,AttributeSet attr)
		{
				super(context,attr);
		}
		public FocusTextView(Context context,AttributeSet attr,int defstyle)
		{
				super(context,attr,defstyle);
		}

		@Override
		public boolean isFocused()
		{
				return true;
		}
		
}
