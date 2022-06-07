package com.bornya.pim.until;

import org.jsoup.Connection;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        Map<String,String> map = readConfig(filePath);
        return map.get("token");
    }
    //将Token写入到文件中
    public static void setToken(String filePath,String token) {
        writeConfig(filePath,"token",token);
    }

    public static Map<String,String> readConfig(String filePath){
        List<String> list= TxtUtils.read(new File(filePath));
        Map<String,String> map=new HashMap<>();
        for(String s:list){
            String[] arr=s.split("###");
            map.put(arr[0],arr[1]);
        }
        return map;
    }

    public static void writeConfig(String filePath,String key,String value){
        List<String> list= TxtUtils.read(new File(filePath));
        List<String> list1=new ArrayList<>();
        for(String s:list){
            String[] arr=s.split("###");
            if(arr[0].equals(key)){
                list1.add(arr[0]+"###"+value);
            }else{
                list1.add(s);
            }
        }
        File file=new File(filePath);
        file.delete();
        TxtUtils.write(filePath,list1);
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

    public static void main(String[] args) throws IOException {
//        String url = "https://pi.pardot.com/api/prospect/version/4/do/query/?id_greater_than=1857000&fields=id,uuid";
//        Map<String,String> headers = new HashMap<>();
//        headers.put("Content-type","application/x-www-form-urlencoded");
//        headers.put("Authorization","Bearer 00D2w000006VZ7y!ARMAQOzo_kWloD4sKMU8MsqdCX0WfhCnMEp9uBpgQ1jTxRpZFhv9nSC6RZbT5cpC3jTu7GmhNHuKOx30SYlsA1Dhl21DZxGq");
//        headers.put("Pardot-Business-Unit-Id","0Uv2w000000CajJCAS");
//        Connection.Response response = HttpUtils.get(url,headers);
//        String result = Utils.parsingResponse(response);
//        System.out.println(result);
        System.out.println(1/20f);
    }
}
