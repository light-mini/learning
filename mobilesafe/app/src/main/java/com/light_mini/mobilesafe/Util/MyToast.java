package com.light_mini.mobilesafe.Util;

import android.content.Context;
import android.widget.Toast;

public class MyToast {
    private static Toast toast;

    private MyToast(){}
    public  static void showShortMeg(Context ctx,String text){
        if (toast==null){
            toast=new Toast(ctx);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }
    public  static void showLongMeg(Context ctx,String text){
        if (toast==null){
            toast=new Toast(ctx);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.setText(text);
        toast.show();
    }
}
