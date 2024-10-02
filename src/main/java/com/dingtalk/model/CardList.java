package com.dingtalk.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName("CardList")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardList {
    //    channelId,varchar(191)
//    channelName,varchar(255)
//    cardStatus,"enum('PENDING','OVERDUE','PROCESSED','OPROCESSED','UNABLE','MISSIDEN')"
//    imageUrl,varchar(191)
//    rating,"decimal(65,30)"
//    itemCount,int
//    cardCreateTime,datetime(3)
//    cardFinishTime,datetime(3)
//    createdAt,datetime(3)
//    updatedAt,datetime(3)
//    published,tinyint(1)
    @TableField(value = "cardId")
    private String cardId;
    @TableField(value = "channelId")
    private String channelId;
    @TableField(value = "channelName")
    private String channelName;
    @TableField(value = "cardStatus")
    private String cardStatus;
    @TableField(value = "imageUrl")
    private String imageUrl;
    //    private String rating;
    @TableField(value = "itemCount")
    private Integer itemCount;
    @TableField(value = "cardCreateTime")
    private LocalDateTime cardCreateTime;
    @TableField(value = "cardFinishTime")
    private LocalDateTime cardFinishTime;
    @TableField(value = "createdAt")
    private LocalDateTime createdAt;
    @TableField(value = "updatedAt")
    private LocalDateTime updatedAt;
    @TableField(value = "published")
    private Integer published;

}
