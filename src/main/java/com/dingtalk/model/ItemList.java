package com.dingtalk.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@TableName("ItemList")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemList {
    private String cardId;
    private String ItemId;
    private String ItemName;
    private Double rating;
    private String itemStatus;
    private String itemStatusName;
    private LocalDateTime cardCreateTime;
    private Integer published;
}
