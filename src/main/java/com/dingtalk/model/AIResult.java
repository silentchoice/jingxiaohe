package com.dingtalk.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Description;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIResult {
    @NonNull
    @JsonProperty("stock_status")
    private String stock_status;
    @JsonProperty("tock_goods")
    private String tock_goods;
    @JsonProperty("shop_no")
    private String shop_no;
    @JsonProperty("shop_name")
    private String shop_name;
    @NonNull
    @JsonProperty("corridor")
    private String corridor;
    @JsonProperty("goods_category")
    private String goods_category;
    @NonNull
    @JsonProperty("identify_goods")
    private List<String> identify_goods;
    @NonNull
    @JsonProperty("identify_probability")
    private List<String> identify_probability;
    @NonNull
    @JsonProperty("image_url")
    private String image_url;
    @JsonProperty("image_url_large")
    @JsonAlias({"images_url_large"})
    private String image_url_large;
    @JsonProperty("ext_info")
    private Map ext_info;
}
