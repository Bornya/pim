package com.bornya.pim.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@RequestMapping("/systemi")
public class SystemController {
    @GetMapping("/runtime")
    public String getSystemLog() throws IOException {
        String logPath = "./nohup.out";
        FileReader reader = new FileReader(logPath);
        BufferedReader bufferedReader = new BufferedReader(reader);

        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            builder.append(line).append("\n");
        }
        return builder.toString();
    }
}
