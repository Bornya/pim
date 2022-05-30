package com.bornya.pim.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bornya.pim.config.BasicConfig;
import com.bornya.pim.entity.Email;
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
import java.util.*;

@Service
public class EmailService {

    @Value("${token_file_path}")
    private String token_file_path_mediaedge;

    //进行邮件的条件查询(要根据subject或者campaign来模糊搜索，以及邮件发送的时间（开始时间-结束时间）)
    public List<Email> getEmailsByDate(String startTime, String endTime,String subject,String campaign) throws IOException {
        //设置需要返回的字段详情：
        String fields = "id," +
                "subject," +
                "sentAt";
        String url= "https://pi.pardot.com/api/v5/objects/list-emails?sentAtAfter="+startTime+"&sentAtBefore="+endTime+"&fields="+fields;
        System.out.println(url);
        Connection.Response response = HttpUtils.get(url,getHeaders());
        String result = Utils.parsingResponse(response);
        System.out.println(result);
        List<Email> emailList = new ArrayList<>();
        // 解析数据
        JSONObject jsonObject = JSONObject.parseObject(result);
        String nextPageToken = jsonObject.getString("nextPageToken"); //是否存在下一页
        System.out.println(nextPageToken);
        //邮件列表详情
        JSONArray jsonArray = jsonObject.getJSONArray("values");
        for(int i= 0;i<jsonArray.size();i++){
            Email email = new Email();
            JSONObject obj = jsonArray.getJSONObject(i);
            email.setId(obj.getInteger("id"));
            email.setCampaignName(obj.getString("campaignName"));
            email.setSubject(obj.getString("subject"));
            email.setSentAt(obj.getString("sentAt"));
            emailList.add(email);
        }
        return emailList;
    }
    //获取邮件详情
    public String getEmailById(String id) throws IOException {
        String url= "https://pi.pardot.com/api/v5/objects/emails/"+id;
        Connection.Response response = HttpUtils.get(url,getHeaders());
        String result = Utils.parsingResponse(response);
        System.out.println(result);
        return result;
    }
    //获取邮件统计信息
    public Email getEmailStats(int id) throws IOException {
        String url= "https://pi.pardot.com/api/email/version/4/do/stats/id/"+id;
        Connection.Response response = HttpUtils.get(url,getHeaders());
        String result = Utils.parsingResponse(response);
        System.out.println(result);
        Email email = new Email();
        email.setId(id);
        if(result.contains(BasicConfig.error)|result.contains("error")){
            System.out.println(BasicConfig.error);
        }else {
            //解析用户数据
            Document doc = null;
            try {
                doc = DocumentHelper.parseText(result);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            Element root = doc.getRootElement();// 指向根节点
            List<Element> elements = root.elements();
            Iterator it = elements.get(0).elementIterator();
            while (it.hasNext()) {
                Element element = (Element) it.next();// 一个Item节点
                if (element.getName().equals("sent")) {
                    email.setSent(element.getTextTrim());
                }
                if (element.getName().equals("delivered")) {
                    email.setDelivered(element.getTextTrim());
                }
                if (element.getName().equals("total_clicks")) {
                    email.setTotal_clicks(element.getTextTrim());
                }
                if (element.getName().equals("unique_clicks")) {
                    email.setUnique_clicks(element.getTextTrim());
                }
                if (element.getName().equals("soft_bounced")) {
                    email.setSoft_bounced(element.getTextTrim());
                }
                if (element.getName().equals("hard_bounced")) {
                    email.setHard_bounced(element.getTextTrim());
                }
                if (element.getName().equals("opt_outs")) {
                    email.setOpt_outs(element.getTextTrim());
                }
                if (element.getName().equals("spam_complaints")) {
                    email.setSpam_complaints(element.getTextTrim());
                }
                if (element.getName().equals("opens")) {
                    email.setOpens(element.getTextTrim());
                }
                if (element.getName().equals("unique_opens")) {
                    email.setUnique_opens(element.getTextTrim());
                }
                if (element.getName().equals("delivery_rate")) {
                    email.setDelivery_rate(element.getTextTrim());
                }
                if (element.getName().equals("opens_rate")) {
                    email.setOpens_rate(element.getTextTrim());
                }

                if (element.getName().equals("click_through_rate")) {
                    email.setClick_through_rate(element.getTextTrim());
                }
                if (element.getName().equals("unique_click_through_rate")) {
                    email.setUnique_click_through_rate(element.getTextTrim());
                }
                if (element.getName().equals("click_open_ratio")) {
                    email.setClick_open_ratio(element.getTextTrim());
                }
                if (element.getName().equals("opt_out_rate")) {
                    email.setOpt_out_rate(element.getTextTrim());
                }
                if (element.getName().equals("spam_complaint_rate")) {
                    email.setSpam_complaint_rate(element.getTextTrim());
                }
            }
        }
        return email;
    }


    //API请求头
    public Map<String,String> getHeaders(){
        String token = Utils.getToken(token_file_path_mediaedge).trim();
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-type","application/x-www-form-urlencoded");
        headers.put("Authorization","Bearer "+token);
        headers.put("Pardot-Business-Unit-Id","0Uv2w000000CajJCAS");
        return headers;
    }
}
