package com.bornya.pim.task;

import com.bornya.pim.entity.CustomUser;
import com.bornya.pim.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduleTask {

    @Autowired
    UserInfoService userInfoService;

    //3.添加定时任务
    @Scheduled(cron = "0/60 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() throws Exception {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        //查询这一个小时新增的所有数据
        //批量更新，让这一个小时的数据的自定字段uuid的值更新为id的进行aes加密后的值
        List<CustomUser> list = userInfoService.getLastNewUser();
        if(list.size()>0){
            System.out.println("存在新增数据,批量对uuid进行赋值");
            System.out.println("新增数据长度："+list.size());
            userInfoService.batchUpdate(list);
        }
    }

}
