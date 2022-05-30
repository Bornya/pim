package com.bornya.pim.controller;


import com.alibaba.fastjson.JSON;
import com.bornya.pim.entity.Email;
import com.bornya.pim.service.EmailService;
import com.bornya.pim.until.ExcelExport2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;


    //进行条件查询--获取给定时间范围的邮件详细信息
    @GetMapping("/getEmailBySendTime")
    public String getEmailBySendTime(String startTime, String endTime,String subject,String campaign) throws Exception {
        List<Email>  emailList = emailService.getEmailsByDate(startTime, endTime, subject, campaign);
        return JSON.toJSONString(emailList);
    }

    /**
     *
     * **/
    @RequestMapping("/toexcel")
    public void testExprotExcel(HttpServletResponse response) throws IOException {
        //创建一个数组用于设置表头
        String[] arr = new String[]{"id","campaignName","subject","sentAt",
                "totalClickThroughRate","totalClicks","uniqueClicks",
                "uniqueOpens","spamComplaints","spamComplaintRate",
                "softBounces","sent","queued",
                "optOutRate","optOuts","opens",
                "openRate","deliveryRate","delivered",
                "ctr","clickToOpenRate","bounces"};
        //调用Excel导出工具类
        List<Email>  emailList = emailService.getEmailsByDate("2022-05-24T09:17:44-08:00", "2022-05-26T09:17:44-08:00", "", "");
        ExcelExport2.export(response,emailList,arr);
    }

    //获取单个邮件详情
    @GetMapping("/getEmailDetail")
    public String getEmailDetail(int id) throws Exception {
        Email  email = emailService.getEmailStats(id);
        return JSON.toJSONString(email);
    }


}
