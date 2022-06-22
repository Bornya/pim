package com.bornya.pim.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bornya.pim.config.BasicConfig;
import com.bornya.pim.entity.CustomUser;
import com.bornya.pim.entity.User;
import com.bornya.pim.until.AESUtil;
import com.bornya.pim.until.HttpUtils;
import com.bornya.pim.until.Utils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserInfoService {

    @Value("${token_file_path}")
    private String token_file_path;

    @Value("${redirect_uri}")
    private String redirect_uri;

    @Value("${client_secret}")
    private String client_secret;

    @Value("${client_id}")
    private String client_id;

    @Value("${fromEmailAddress}")
    private String fromEmailAddress;

    @Value("${toEmailAddress}")
    private String toEmailAddress;


    //OAuth授权流程
    //获取code
    public String getCode() throws IOException {
        String url = "https://login.salesforce.com/services/oauth2/authorize?response_type=code&client_id="+ client_id +"&redirect_uri="+redirect_uri;
        Connection.Response response = HttpUtils.get(url);
        String result = Utils.parsingResponse(response);
        System.out.println(result);
        return result;
    }
    public String getToken(String code) throws IOException {
        String url = "https://login.salesforce.com/services/oauth2/token";
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-type","application/x-www-form-urlencoded");
        Map<String,String> params = new HashMap<>();
        params.put("grant_type","authorization_code");
        params.put("code",code);
        params.put("client_id",client_id);
        params.put("client_secret",client_secret);
        params.put("redirect_uri",redirect_uri);
        Connection.Response response = HttpUtils.post(headers,url,params);
        String result = Utils.parsingResponse(response);
        if(result.equals("error")){
            return result;
        }
        System.out.println(result);
        //解析返回数据，获取token
        JSONObject jsonObject = JSON.parseObject(result);
        String access_token = jsonObject.getString("access_token");
        Utils.setToken(token_file_path,access_token);
        return access_token;
    }

    //用户信息查询API
    public User getUserInfo(String uuid) throws Exception {
        User user = new User();
        //进行解密，将uuid的值转化为id
        String uid =AESUtil.getDecryptId(uuid);
        String url = "https://pi.pardot.com/api/prospect/version/4/do/read/id/"+uid;
        Connection.Response response = HttpUtils.get(url,getHeaders());
        String result = Utils.parsingResponse(response);
        System.out.println(result);
        if(result.contains(BasicConfig.error)|result.contains("error")){
            System.out.println(BasicConfig.error);
        }else{
            //解析用户数据
            Document doc = null;
            try {
                doc = DocumentHelper.parseText(result);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            Element root = doc.getRootElement();// 指向根节点
            System.out.println();
            List<Element> elements = root.elements();
            Iterator it = elements.get(0).elementIterator();
            while (it.hasNext()) {
                Element element = (Element) it.next();// 一个Item节点
                if(element.getName().equals("id")){
                    user.setId(element.getTextTrim());
                }
                if(element.getName().equals("email")){
                    user.setEmail(element.getTextTrim());
                }
                if(element.getName().equals("Tatler_Digest_New")){
                    user.setTatler_Digest_New(Boolean.parseBoolean(element.getTextTrim()));
                }
                if(element.getName().equals("Tatler_Homes_list")){
                    user.setTatler_Homes_list(Boolean.parseBoolean(element.getTextTrim()));
                }

                if(element.getName().equals("Tatler_Digest_Hong_Kong_list")){
                    user.setTatler_Digest_Hong_Kong_list(Boolean.parseBoolean(element.getTextTrim()));
                }
                if(element.getName().equals("Tatler_Digest_Philippines_list")){
                    user.setTatler_Digest_Philippines_list(Boolean.parseBoolean(element.getTextTrim()));
                }
                if(element.getName().equals("Tatler_Digest_Singapore_list")){  //住
                    user.setTatler_Digest_Singapore_list(Boolean.parseBoolean(element.getTextTrim()));
                }
                if(element.getName().equals("Tatler_Digest_Taiwan_list")){
                    user.setTatler_Digest_Taiwan_list(Boolean.parseBoolean(element.getTextTrim()));
                }
                if(element.getName().equals("Tatler_Digest_Malaysia_list")){ //住
                    user.setTatler_Digest_Malaysia_list(Boolean.parseBoolean(element.getTextTrim()));
                }
                if(element.getName().equals("Tatler_Dining_Hong_Kong_list")){ //住
                    user.setTatler_Dining_Hong_Kong_list(Boolean.parseBoolean(element.getTextTrim()));
                }
                if(element.getName().equals("Tatler_Dining_Malaysia_list")){ //住
                    user.setTatler_Dining_Malaysia_list(Boolean.parseBoolean(element.getTextTrim()));
                }
                if(element.getName().equals("Tatler_Dining_Singapore_list")){ //住
                    user.setTatler_Dining_Singapore_list(Boolean.parseBoolean(element.getTextTrim()));
                }
                if(element.getName().equals("Tatler_Dining_Philippines_list")){ //住
                    user.setTatler_Dining_Philippines_list(Boolean.parseBoolean(element.getTextTrim()));
                }
                if(element.getName().equals("Tatler_Digest_Malaysia_list")){ //住
                    user.setTatler_Digest_Philippines_list(Boolean.parseBoolean(element.getTextTrim()));
                }
                if(element.getName().equals("Front_Female")){ //住
                    user.setFront_Female(Boolean.parseBoolean(element.getTextTrim()));
                }
                if(element.getName().equals("Generation_T_list")){ //住
                    user.setGeneration_T_list(Boolean.parseBoolean(element.getTextTrim()));
                }
            }
        }
        return user;
    }
    //用户信息更新API
    public String updateUserInfo(User user) throws IOException {
        String url = "https://pi.pardot.com/api/prospect/version/4/do/update/id/"+user.getId()
                +"?Tatler_Digest_New="+user.isTatler_Digest_New()
                +"&Tatler_Homes_list="+user.isTatler_Homes_list()
                +"&Tatler_Digest_Hong_Kong_list="+user.isTatler_Digest_Hong_Kong_list()
                +"&Tatler_Digest_Philippines_list="+user.isTatler_Digest_Philippines_list()
                +"&Tatler_Digest_Singapore_list="+user.isTatler_Digest_Singapore_list()
                +"&Tatler_Digest_Taiwan_list="+user.isTatler_Digest_Taiwan_list()
                +"&Tatler_Digest_Malaysia_list="+user.isTatler_Digest_Malaysia_list()
                +"&Tatler_Dining_Hong_Kong_list="+user.isTatler_Dining_Hong_Kong_list()
                +"&Tatler_Dining_Malaysia_list="+user.isTatler_Dining_Malaysia_list()
                +"&Tatler_Dining_Singapore_list="+user.isTatler_Dining_Singapore_list()
                +"&Tatler_Dining_Philippines_list="+user.isTatler_Dining_Philippines_list()
                +"&Front_Female="+user.isFront_Female()
                +"&Generation_T_list="+user.isGeneration_T_list()
                ;
        Connection.Response response = HttpUtils.get(url,getHeaders());
        String result = Utils.parsingResponse(response);
        System.out.println(result);
        return result;
    }

    //查询一个小时内新的所有数据
    public List<CustomUser> getLastNewUser() throws Exception {
        List<CustomUser> list = new ArrayList<>();
//        String url = "https://pi.pardot.com/api/prospect/version/4/do/query/?created_after="+Utils.getTime(100);
        //取最新的id 字段
        Map<String,String> map = Utils.readConfig(token_file_path);
        String url="https://pi.pardot.com/api/prospect/version/4/do/query/?id_greater_than="+map.get("id")+"&fields=id,uuid&limit=100&sort_by=id";
        System.out.println(url);
        Connection.Response response = HttpUtils.get(url,getHeaders());
        String result = Utils.parsingResponse(response);
        if(result.contains("access_token is invalid")){
            emailNotification(LocalDateTime.now()+"|access_token is invalid, unknown, or malformed: Inactive token","error");
        }
        //解析结果
        String startId= map.get("id");
        if(result.contains("prospect")){
            Document doc = null;
            try {
                doc = DocumentHelper.parseText(result);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            Element root = doc.getRootElement();// 指向根节点
            List<Element> elements = root.elements();
            List<Element> elementList = elements.get(0).elements();
            for (Element value : elementList) {
                Iterator it = value.elementIterator();
                CustomUser customUser = new CustomUser();
                String id = "";
                String getUuid = "";
                String uuid = "";
                while (it.hasNext()) {
                    Element element = (Element) it.next();// 一个Item节点
                    if (element.getName().equals("id")) {
                        id = element.getTextTrim();
                        startId = id;
                    }
                    if (element.getName().equals("uuid")) {
                        getUuid = element.getTextTrim();
                    }
                }
                if(id.length()>2){  //测试环境，监控测试账号
                    uuid = AESUtil.getEncryptUUid(id);
                    if(!getUuid.equals(uuid)){
                        customUser.setId(id);
                        customUser.setUuid(uuid);
                        list.add(customUser);
                    }
                }
            }
        }
        System.out.println("最新的起始id:"+startId);
        Utils.writeConfig(token_file_path,"id",startId);
        return list;
    }
    //获取最新的id
    public String getLastId(){
        Map<String,String> map = Utils.readConfig(token_file_path);
        return map.get("id");
    }

    // 更新自定义字段UUId
    public void batchUpdate(List<CustomUser> userList) throws InterruptedException {
        for (CustomUser c:userList
        ) {
            String url = "https://pi.pardot.com/api/prospect/version/4/do/update/id/" + c.getId()
                    + "?uuid=" + c.getUuid();
            Connection.Response response = HttpUtils.get(url, getHeaders());
            if(response==null){
                System.out.print(c.getId()+"更新失败");
            }else if(response.statusCode()==200){
                System.out.print(c.getId()+"更新成功");
            }else{
                System.out.print(c.getId()+"更新失败");
            }
            Thread.sleep(500);
        }
        String id = userList.get(userList.size()-1).getId();
        System.out.println("最新的起始id:"+id);
        Utils.writeConfig(token_file_path,"id",id);
    }
    //API请求头
    public Map<String,String> getHeaders(){
        String token = Utils.getToken(token_file_path).trim();
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-type","application/x-www-form-urlencoded");
        headers.put("Authorization","Bearer "+token);
        headers.put("Pardot-Business-Unit-Id","0Uv2w000000CajJCAS");
        return headers;
    }

    //邮件通知
    public void emailNotification(String message,String type) throws IOException {
        String url = "https://api.postmarkapp.com/email";
        Map<String,String> headers= new HashMap<>();
        headers.put("Accept","application/json");
        headers.put("Content-Type","application/json");
        headers.put("X-Postmark-Server-Token","4808083e-566d-4a1f-a080-e45e5de0d28a");
        Map<String,String> params = new HashMap<>();
        params.put("From",fromEmailAddress);
        params.put("To",toEmailAddress);
        if(type.equals("error")){
            params.put("Subject","Tatler Pardot Form");
        }else{
            params.put("Subject","Tatler Pardot Form Error Notification");
        }
        params.put("HtmlBody",message);
        params.put("MessageStream","pardot-api");
        Connection.Response response = HttpUtils.post(url,headers,JSON.toJSONString(params));
        String result = Utils.parsingResponse(response);
        System.out.println(result);
    }
}
