package com.bornya.pim.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@RequestMapping("/systemi")
public class SystemController {
    @GetMapping("/runtime")
    public ResponseEntity<String> getSystemFile(@RequestParam(required = true) String f) throws IOException {
        String logPath = "./" + f;
        FileReader reader = new FileReader(logPath);
        BufferedReader bufferedReader = new BufferedReader(reader);

        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            builder.append(line).append("\n");
        }
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(builder.toString());
    }
}
