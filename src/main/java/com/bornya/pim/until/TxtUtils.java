package com.bornya.pim.until;

import com.alibaba.fastjson.JSONArray;
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

        String result = "{\n" +
                "    \"nextPageToken\": null,\n" +
                "    \"nextPageUrl\": null,\n" +
                "    \"values\": [\n" +
                "        {\n" +
                "            \"id\": 138016402,\n" +
                "            \"campaignId\": 269,\n" +
                "            \"clientType\": \"Web\",\n" +
                "            \"createdAt\": \"2022-05-24T14:18:11+08:00\",\n" +
                "            \"createdById\": 43136543,\n" +
                "            \"isDeleted\": false,\n" +
                "            \"isPaused\": false,\n" +
                "            \"isSent\": true,\n" +
                "            \"name\": \"HK_DIG_FULL_20220525_sticky rice dumplings\",\n" +
                "            \"senderOptions\": [\n" +
                "                {\n" +
                "                    \"address\": \"enews@tatlerasia.com\",\n" +
                "                    \"name\": \"Tatler Dining Hong Kong\",\n" +
                "                    \"type\": \"general_user\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"sentAt\": \"2022-05-25T12:00:00+08:00\",\n" +
                "            \"subject\": \"'Tis the season for sticky rice dumplings\",\n" +
                "            \"updatedAt\": \"2022-05-25T12:18:50+08:00\",\n" +
                "            \"updatedById\": 43136543\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 138017536,\n" +
                "            \"campaignId\": 269,\n" +
                "            \"clientType\": \"Web\",\n" +
                "            \"createdAt\": \"2022-05-24T16:22:28+08:00\",\n" +
                "            \"createdById\": 52903841,\n" +
                "            \"isDeleted\": false,\n" +
                "            \"isPaused\": false,\n" +
                "            \"isSent\": true,\n" +
                "            \"name\": \"PH_DIN_The Best Italian Restaurants in the Philippines_FULL20220525\",\n" +
                "            \"senderOptions\": [\n" +
                "                {\n" +
                "                    \"address\": \"enews@tatlerasia.com\",\n" +
                "                    \"name\": \"Tatler Dining Philippines\",\n" +
                "                    \"type\": \"general_user\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"sentAt\": \"2022-05-25T08:00:00+08:00\",\n" +
                "            \"subject\": \"The Best Italian Restaurants in the Philippines\",\n" +
                "            \"updatedAt\": \"2022-05-25T08:16:22+08:00\",\n" +
                "            \"updatedById\": 52903841\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 138735991,\n" +
                "            \"campaignId\": 269,\n" +
                "            \"clientType\": \"Web\",\n" +
                "            \"createdAt\": \"2022-05-25T10:28:43+08:00\",\n" +
                "            \"createdById\": 43135353,\n" +
                "            \"isDeleted\": false,\n" +
                "            \"isPaused\": false,\n" +
                "            \"isSent\": true,\n" +
                "            \"name\": \"SG_DIN_Nasi Lemak_FULL20220525\",\n" +
                "            \"senderOptions\": [\n" +
                "                {\n" +
                "                    \"address\": \"enews@tatlerasia.com\",\n" +
                "                    \"name\": \"Tatler Dining Singapore\",\n" +
                "                    \"type\": \"general_user\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"sentAt\": \"2022-05-25T11:30:00+08:00\",\n" +
                "            \"subject\": \"The Best Nasi Lemak in Singapore\",\n" +
                "            \"updatedAt\": \"2022-05-25T11:41:09+08:00\",\n" +
                "            \"updatedById\": 43135353\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 138736063,\n" +
                "            \"campaignId\": 269,\n" +
                "            \"clientType\": \"Web\",\n" +
                "            \"createdAt\": \"2022-05-25T10:54:41+08:00\",\n" +
                "            \"createdById\": 43912313,\n" +
                "            \"isDeleted\": false,\n" +
                "            \"isPaused\": false,\n" +
                "            \"isSent\": true,\n" +
                "            \"name\": \"MY_DIN_Discovering Martell Chanteloup XXO Via a Multi-Sensorial Experience_FULL20220525\",\n" +
                "            \"senderOptions\": [\n" +
                "                {\n" +
                "                    \"address\": \"enews@tatlerasia.com\",\n" +
                "                    \"name\": \"Tatler Dining Malaysia\",\n" +
                "                    \"type\": \"general_user\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"sentAt\": \"2022-05-25T11:00:00+08:00\",\n" +
                "            \"subject\": \"Discovering Martell Chanteloup XXO Via a Multi-Sensorial Experience\",\n" +
                "            \"updatedAt\": \"2022-05-25T11:07:25+08:00\",\n" +
                "            \"updatedById\": 43912313\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 138919225,\n" +
                "            \"campaignId\": 269,\n" +
                "            \"clientType\": \"Web\",\n" +
                "            \"createdAt\": \"2022-05-25T12:13:57+08:00\",\n" +
                "            \"createdById\": 42610783,\n" +
                "            \"isDeleted\": false,\n" +
                "            \"isPaused\": false,\n" +
                "            \"isSent\": true,\n" +
                "            \"name\": \"SG_EDM_Marriott(Tomlinson)_FULL20220526\",\n" +
                "            \"senderOptions\": [\n" +
                "                {\n" +
                "                    \"address\": \"enews@tatlerasia.com\",\n" +
                "                    \"name\": \"Tatler Dining Singapore\",\n" +
                "                    \"type\": \"general_user\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"sentAt\": \"2022-05-26T16:00:00+08:00\",\n" +
                "            \"subject\": \"Afternoon Tea Doesn’t Get Any Better Than This! \",\n" +
                "            \"updatedAt\": \"2022-05-26T16:10:18+08:00\",\n" +
                "            \"updatedById\": 42610783\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 138926422,\n" +
                "            \"campaignId\": 269,\n" +
                "            \"clientType\": \"Web\",\n" +
                "            \"createdAt\": \"2022-05-25T15:29:12+08:00\",\n" +
                "            \"createdById\": 43115723,\n" +
                "            \"isDeleted\": false,\n" +
                "            \"isPaused\": false,\n" +
                "            \"isSent\": true,\n" +
                "            \"name\": \"MY_DIG_WinnersTropheeChopard_FULL20220526\",\n" +
                "            \"senderOptions\": [\n" +
                "                {\n" +
                "                    \"address\": \"enews@tatlerasia.com\",\n" +
                "                    \"name\": \"Tatler Malaysia\",\n" +
                "                    \"type\": \"general_user\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"sentAt\": \"2022-05-26T10:00:00+08:00\",\n" +
                "            \"subject\": \"Cannes 2022: Winners of Trophée Chopard\",\n" +
                "            \"updatedAt\": \"2022-05-26T10:12:43+08:00\",\n" +
                "            \"updatedById\": 43115723\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 138930622,\n" +
                "            \"campaignId\": 269,\n" +
                "            \"clientType\": \"Web\",\n" +
                "            \"createdAt\": \"2022-05-25T16:23:28+08:00\",\n" +
                "            \"createdById\": 43135353,\n" +
                "            \"isDeleted\": false,\n" +
                "            \"isPaused\": false,\n" +
                "            \"isSent\": true,\n" +
                "            \"name\": \"SG_DIG_Which ‘Bling Empire’ Star Earns the Most_FULL20220526\",\n" +
                "            \"senderOptions\": [\n" +
                "                {\n" +
                "                    \"address\": \"enews@tatlerasia.com\",\n" +
                "                    \"name\": \"Tatler Singapore\",\n" +
                "                    \"type\": \"general_user\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"sentAt\": \"2022-05-26T13:00:00+08:00\",\n" +
                "            \"subject\": \"Which ‘Bling Empire’ Star Earns The Most?\",\n" +
                "            \"updatedAt\": \"2022-05-26T13:09:34+08:00\",\n" +
                "            \"updatedById\": 43135353\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 138977041,\n" +
                "            \"campaignId\": 269,\n" +
                "            \"clientType\": \"Web\",\n" +
                "            \"createdAt\": \"2022-05-25T18:11:17+08:00\",\n" +
                "            \"createdById\": 43114733,\n" +
                "            \"isDeleted\": false,\n" +
                "            \"isPaused\": false,\n" +
                "            \"isSent\": true,\n" +
                "            \"name\": \"TW_DIG慶端午20間款質感名粽推薦_ACT20220526\",\n" +
                "            \"senderOptions\": [\n" +
                "                {\n" +
                "                    \"address\": \"enews@tatlerasia.com\",\n" +
                "                    \"name\": \"Tatler Taiwan\",\n" +
                "                    \"type\": \"general_user\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"sentAt\": \"2022-05-26T11:00:00+08:00\",\n" +
                "            \"subject\": \"慶端午20間款質感名粽推薦\",\n" +
                "            \"updatedAt\": \"2022-05-26T11:16:04+08:00\",\n" +
                "            \"updatedById\": 43114733\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 140419438,\n" +
                "            \"campaignId\": 269,\n" +
                "            \"clientType\": \"Web\",\n" +
                "            \"createdAt\": \"2022-05-26T10:29:55+08:00\",\n" +
                "            \"createdById\": 42932363,\n" +
                "            \"isDeleted\": false,\n" +
                "            \"isPaused\": false,\n" +
                "            \"isSent\": true,\n" +
                "            \"name\": \"HK_DIG_The Best Hong Kong Homes on  the Market: May 2022_FULL20220526\",\n" +
                "            \"senderOptions\": [\n" +
                "                {\n" +
                "                    \"address\": \"enews@tatlerasia.com\",\n" +
                "                    \"name\": \"Tatler Hong Kong\",\n" +
                "                    \"type\": \"general_user\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"sentAt\": \"2022-05-26T11:00:00+08:00\",\n" +
                "            \"subject\": \"The Best Hong Kong Homes on  the Market: May 2022\",\n" +
                "            \"updatedAt\": \"2022-05-26T11:16:04+08:00\",\n" +
                "            \"updatedById\": 42932363\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 140419486,\n" +
                "            \"campaignId\": 269,\n" +
                "            \"clientType\": \"Web\",\n" +
                "            \"createdAt\": \"2022-05-26T10:56:04+08:00\",\n" +
                "            \"createdById\": 52903841,\n" +
                "            \"isDeleted\": false,\n" +
                "            \"isPaused\": false,\n" +
                "            \"isSent\": true,\n" +
                "            \"name\": \"PH_DIG_SEA's Biggest Nike Store Now Open in the Philippines_FULL20220526\",\n" +
                "            \"senderOptions\": [\n" +
                "                {\n" +
                "                    \"address\": \"enews@tatlerphilippines.com\",\n" +
                "                    \"name\": \"Tatler Philippines \",\n" +
                "                    \"type\": \"general_user\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"sentAt\": \"2022-05-26T11:30:00+08:00\",\n" +
                "            \"subject\": \"SEA's Biggest Nike Store Now Open in the Philippines\",\n" +
                "            \"updatedAt\": \"2022-05-26T11:38:26+08:00\",\n" +
                "            \"updatedById\": 52903841\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 140784427,\n" +
                "            \"campaignId\": 269,\n" +
                "            \"clientType\": \"Web\",\n" +
                "            \"createdAt\": \"2022-05-26T18:19:23+08:00\",\n" +
                "            \"createdById\": 42610713,\n" +
                "            \"isDeleted\": false,\n" +
                "            \"isPaused\": false,\n" +
                "            \"isSent\": true,\n" +
                "            \"name\": \"Tatler MetaVersed Ep7_Winners\",\n" +
                "            \"senderOptions\": [\n" +
                "                {\n" +
                "                    \"address\": \"enews@tatlerasia.com\",\n" +
                "                    \"name\": \"Tatler Asia\",\n" +
                "                    \"type\": \"general_user\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"sentAt\": \"2022-05-26T18:19:23+08:00\",\n" +
                "            \"subject\": \"Congratulations: World of Women Whitelist\",\n" +
                "            \"updatedAt\": \"2022-05-26T18:29:33+08:00\",\n" +
                "            \"updatedById\": 42610713\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(result);
        System.out.println(jsonObject.getString("nextPageToken"));
        JSONArray jsonArray = jsonObject.getJSONArray("values");
        JSONObject obj = jsonArray.getJSONObject(0);
        System.out.println(obj.getString("id"));



    }
}
