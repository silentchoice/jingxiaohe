package com.dingtalk.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("personnel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Personnel {
    private String personnelName;
    private String personnelDetail;
    private Integer areaId;

}
