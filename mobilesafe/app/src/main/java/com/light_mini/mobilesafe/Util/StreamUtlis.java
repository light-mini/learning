package com.light_mini.mobilesafe.Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtlis {
    public static String streamToString(InputStream in) throws IOException {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte[] buff=new byte[1024];
        int len=0;
        while ((len=in.read(buff))!=-1){
            baos.write(buff,0,len);
        }
        String s = baos.toString();
        in.close();
        baos.close();
        return s;
    }
}
