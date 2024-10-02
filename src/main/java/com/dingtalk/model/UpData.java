package com.dingtalk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpData {
    @JsonProperty("date")
    private String date;
    @JsonProperty("corridor")
    private String corridor;
    @JsonProperty("mesage")
    private String mesage;
    @JsonProperty("shop_name")
    private String shop_name;
}
