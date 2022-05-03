package com.bornya.pim.until;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class TxtUtils {

    /**
     * 写入txt
     * @param path 需要写入txt的路径
     * @since 2021/1/8 19:37
     */
    public static void write(String path, String result) {
        BufferedWriter bw = null;
        FileWriter fr = null;
        try {
            //将写入转化为流的形式
            fr = new FileWriter(path,true);
            bw = new BufferedWriter(fr);
            bw.write(result);
            bw.newLine();  //换行用
//            //一次写一行
//            for (String s : list) {
//                bw.write(s);
//                bw.newLine();  //换行用
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写入txt
     * @param path 需要写入txt的路径
     * @param list 需要写入的字符串的list
     * @since 2021/1/8 19:37
     */
    public static void write(String path, List<String> list) {
        BufferedWriter bw = null;
        FileWriter fr = null;
        try {
            //将写入转化为流的形式
            fr = new FileWriter(path,true);
            bw = new BufferedWriter(fr);
//            bw.write(result);
//            bw.newLine();  //换行用
            //一次写一行
            for (String s : list) {
                bw.write(s);
                bw.newLine();  //换行用
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> read(File file) {
        // 使用ArrayList来存储每行读取到的字符串
        List<String> list = new ArrayList<>();
        try {
            InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
            BufferedReader bf = new BufferedReader(inputReader);
            // 按行读取字符串
            String str;
            while ((str = bf.readLine()) != null) {
                list.add(str);
            }
            bf.close();
            inputReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    //随机获取List的值
    public static String getListValue(List<String> list){
        int size=list.size();
        Random random = new Random();
        return list.get(random.nextInt(size));
    }



    /**
     * 根据行数删除某一指定行
     *
     * @param file             txt文件路径
     */
    public static void removeLineForLineNumber(String file, int start,int end) {
        try {
            //获取原文件
            File oldFile = new File(file);
            if (!oldFile.isFile()) {
                return;
            }
            // 判断一下文件的总行数，比较lineNumber是否合理
            if(end> Files.lines(Paths.get(file)).count()){
                return ;
            }
            //构造临时文件
            File newFile = new File(oldFile.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(newFile));
            String lineStr = null;//某一行的值
            int lineCount = 0;//行数
            while ((lineStr = br.readLine()) != null) {
                lineCount++;
                if (lineCount<=end&&lineCount>=start) {
                    pw.println("");
                    pw.flush();
                } else {
                    pw.println(lineStr);
                    pw.flush();
                }
            }
            pw.close();
            br.close();
            //删除原文件
            if (!oldFile.delete()) {
                return ;
            }
            //重命名
            if (!newFile.renameTo(oldFile)) {
                return ;
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 创建文件
     * @param fileName
     * @return
     */
    public static boolean createFile(File fileName)throws Exception{
        boolean flag=false;
        try{
            if(!fileName.exists()){
                fileName.createNewFile();
                flag=true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }


    //修改拨号电话溥里面的ip地址
    public static void updateVpm(String ip){
        File file=new File("./rasphone.pbk");
        List<String> list=TxtUtils.read(file);
        file.delete();
        list.set(138,"PhoneNumber="+ip);
        TxtUtils.write("./rasphone.pbk",list);
    }


    //每次读取第一行
    public static String getFirstLine(String filePath){
        File file =new File(filePath);
        List<String> list = read(file);
        String line = "error";
        if (list.size() > 0) {
            line = list.get(0);
            list.remove(0);
            file.delete();
            write(filePath,list);
        }
        return line;
    }
    //读取指定的前多少行
    public static List<String> getCustomLine(String filePath,int count){
        File file =new File(filePath);
        List<String> list = read(file);
        List<String> result =new ArrayList<>();
        List<String> remaining = new ArrayList<>();
        if (list.size() > 0) {
            if (list.size()>count){
                result = list.subList(0,count);
                remaining = list.subList(count,list.size());
            }else{
                result = list;
            }
            file.delete();
            write(filePath,remaining);
        }
        return result;
    }






    public static void main(String[] args) throws Exception {


    }
}
