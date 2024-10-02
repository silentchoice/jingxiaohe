package com.dingtalk.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.annotation.CreatedDate;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class TestModle {
    @CreatedDate
    private LocalDateTime createDate;

    public static void main(String[] args) {
        System.out.println((char[]) null);
    }

    public TestModle(){}

    @Test
    public void testSecond(){
        String time = "2024-07-08T07:11:55.256";
        LocalDateTime parse = LocalDateTime.parse(time);
        Duration between = Duration.between(parse, LocalDateTime.now());
        long seconds = between.getSeconds();
        System.out.println(seconds);
    }
}
