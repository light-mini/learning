package com.itheima.mobilesafe.Utils;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamUtils
{



    public static String streamToString(InputStream in,int len)
    {
				ByteArrayOutputStream bao=new ByteArrayOutputStream(len);
				try
				{
						byte[] bytes=new byte[1024];
						int tmp;
						while((tmp = in.read(bytes))!=-1)
						{
								bao.write(bytes,0,tmp);
						}
						return bao.toString();
				}
				catch (IOException e)
				{e.printStackTrace();}
        return null;
    }
}
