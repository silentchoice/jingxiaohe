package com.dingtalk;

import com.dingtalk.mapper.CardListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableScheduling
public class StaticScheduleTask {
    @Autowired
    private CardListMapper cardListMapper;
    @Scheduled(cron = "0 0 1 * * ?")
    private void configureTasks(){
        int number = cardListMapper.getListShopId();
        System.out.println(number+"time："+ LocalDateTime.now());
    }
}
