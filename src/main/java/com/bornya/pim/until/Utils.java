package com.bornya.pim.until;

import org.jsoup.Connection;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Utils {

    //网络请求解析工具类
    public static String parsingResponse(Connection.Response response) throws IOException {
        if(response==null){
            return "error";
        }
        //请求成功
        String result = "";
        BufferedInputStream inputStream=response.bodyStream();
        //按照字节数组的方式进行读取操作，此处我并没有按照单个字节方式进行读取。
        byte[] buf = new byte[4096];
        int len;
        while(( len = inputStream.read(buf)) != -1 ) {
            result=result+new String(buf,0,len);
        }
        return result;
//        if(response.statusCode()==200){
//            //请求成功
//            String result = "";
//            BufferedInputStream inputStream=response.bodyStream();
//            //按照字节数组的方式进行读取操作，此处我并没有按照单个字节方式进行读取。
//            byte[] buf = new byte[4096];
//            int len;
//            while(( len = inputStream.read(buf)) != -1 ) {
//                result=result+new String(buf,0,len);
//            }
//            return result;
//        }else{
//            return "error";
//        }
    }
    //读取Token文件
    public static String getToken(String filePath) {
        List<String> list = TxtUtils.read(new File(filePath));
        System.out.println(list.get(0));
        return list.get(0);
    }
    //将Token写入到文件中
    public static void setToken(String filePath,String token) {
        File file  = new File(filePath);
        file.delete();
        TxtUtils.write(filePath,token);
    }
    //获取距离当前的指定小时时间
    public static String getTime(int interval){
        Calendar calendar = Calendar.getInstance();
        //设置小时
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - interval);
        //格式化时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(calendar.getTime());
    }
}
