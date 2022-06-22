package com.bornya.pim.until;

import com.alibaba.fastjson.JSON;
import org.jsoup.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class test {

    public static void main(String[] args) throws IOException {
        String url = "https://api.postmarkapp.com/email";
        Map<String,String> headers= new HashMap<>();
        headers.put("Accept","application/json");
        headers.put("Content-Type","application/json");
        headers.put("X-Postmark-Server-Token","4808083e-566d-4a1f-a080-e45e5de0d28a");

        Map<String,String> params = new HashMap<>();
        params.put("From","readermanagement@circulation.asia");
        params.put("To","ricky@omnidataservices.com");
        params.put("Subject","Hello from Postmark");
        params.put("HtmlBody","<strong>Hello</strong> dear Postmark user");
        params.put("MessageStream","pardot-api");
        Connection.Response response = HttpUtils.post(url,headers,JSON.toJSONString(params));
        String result = Utils.parsingResponse(response);
        System.out.println(result);

    }
}
