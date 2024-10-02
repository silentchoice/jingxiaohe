package com.dingtalk;

import com.dingtalk.mapper.CardListMapper;
import com.dingtalk.model.AIResult;
import com.dingtalk.model.IdUserId;
import com.dingtalk.model.UpData;
import com.dingtalk.util.DatabaseUtils;
import com.dingtalk.util.RobotMessage;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shade.com.alibaba.fastjson2.JSONObject;

@RestController
public class AiHelpController {
    @Autowired
    private CardListMapper cardListMapper;
    private HashMap<List<String>, LocalDateTime> resultHashMap = new HashMap();
//    private final String[] AiModleColumn = new String[]{"event_type", "shop_name", "shelves_code", "area_code", "goods_name", "scene_type", "scene_detail", "result_picture", "create_time"};
    private final String[] CardListColumn = new String[]{"shopId", "shopName", "channelId", "channelName", "cardStatus", "imageUrl", "imageUrlLarge", "itemCount", "cardCreateTime", "cardFinishTime", "createdAt", "updatedAt", "published", "userId", "cardStatusType", "cardStatusTypeName"};
    private final String[] ItemListColumn = new String[]{"cardId", "ItemId", "ItemName", "itemStatus", "itemStausName", "published", "itemCreateTime", "rating"};

    @PostMapping({"/data"})
    public JSONObject receiveData(@RequestBody AIResult data) throws Exception {
        JSONObject jsonObject = new JSONObject();
        System.out.println("================================数据分页====================================");
        System.out.println("data" + data.toString());
        if (data.getIdentify_goods().size() != data.getIdentify_probability().size()) {
            System.out.println("识别物品数量与识别物品概率数量不一致");
            jsonObject.put("code", "-1");
            jsonObject.put("message", "识别物品数量与识别物品概率数量不一致");
            return JSONObject.from(jsonObject);
        } else {
            JSONObject emergency = emergency(data);
            System.out.println(emergency);
            if (emergency != null) {
                System.out.println(emergency);
                jsonObject.put("code", "0");
                jsonObject.put("message", "数据发送成功");
                return JSONObject.from(jsonObject);
            } else {
                jsonObject.put("code", "-2");
                jsonObject.put("message", "未知异常");
                System.out.println("数据发送成功");
                return JSONObject.from(jsonObject);
            }
        }
    }

    @PostMapping({"/updata"})
    public Long upData(@RequestBody UpData data) {
        try {
            String corridor = data.getCorridor();
            long l = Long.parseLong(corridor);
            return l;
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    @RequestMapping({"/datespaceseconds"})
    public Long getDateSpaceSeconds(@RequestParam(value = "seconds",required = false,defaultValue = "7200") String seconds) {
        try {
            Long l = Long.parseLong(seconds);
            return l;
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public JSONObject emergency(AIResult aiResult) throws Exception {
        String corridor = aiResult.getCorridor();
        String tockGoods = aiResult.getTock_goods();
        List<String> identifyGoods = aiResult.getIdentify_goods();
        List<String> identifyProbabilitys = aiResult.getIdentify_probability();
        String stockStatus = aiResult.getStock_status();
        String imageUrl = aiResult.getImage_url();
        String imageUrlLarge = aiResult.getImage_url_large();
        String shopName = aiResult.getShop_name();
        String shopNo = aiResult.getShop_no();
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnnnnnnnn"));
//        String shopName = this.cardListMapper.getShopName(shopId);
        String initalFinishTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnnnnnnnn"));
        if ("0".equals(stockStatus)) {
            JSONObject jsonObject = new JSONObject();
            return JSONObject.from(jsonObject.put("success", "正常物品数据接收成功"));
        } else {
            String[] result;
            if (imageUrlLarge != null && !"null".equals(imageUrlLarge)) {
                result = new String[]{tockGoods, shopName, corridor, imageUrlLarge, "", ""};
            } else {
                result = new String[]{tockGoods, shopName, corridor, imageUrl, "", ""};
            }
//            if ("金旅城店".equals(shopId)) {
//                shopName = shopId;
//            }
//            String shopUid = this.cardListMapper.getShopUid(shopName);
            Integer cardId = this.cardListMapper.getToId(shopName, corridor, "PENDING", 1);
            System.out.println("dataTime:" + dateTime + ",cardId:" + cardId);
            JSONObject resultJSON = new JSONObject();
            ArrayList<String> authorId = this.cardListMapper.getAuthorId();
            if (cardId == null) {
                ArrayList<String> listUserId = this.cardListMapper.getListUserId(shopName);
                int size = listUserId.size();
                Random random = new Random();
                int randomIndex = random.nextInt(size);
                String[] CardListValues = new String[]{shopNo, shopName, corridor, "通道" + corridor, "PENDING", imageUrl, imageUrlLarge, String.valueOf(identifyGoods.size()), dateTime, initalFinishTime, dateTime, initalFinishTime, "1", listUserId.get(randomIndex), "0", ""};
                DatabaseUtils.writeToDatabase("CardList", this.CardListColumn, CardListValues);
                cardId = this.cardListMapper.getToId(shopName, corridor, "PENDING", 1);
                result[4] = cardId.toString();
                IdUserId idUserId = this.cardListMapper.getIdUserId(shopName, corridor, "PENDING", 1);
                result[5] = idUserId.getUserId();
                RobotMessage.sendMessage(result);
                System.out.println("发送消息给店员：" + result[5] + ",时间为：" + LocalDateTime.now());
                for (String s : authorId) {
                    result[5] = s;
                    RobotMessage.sendMessage(result);
                    System.out.println("发送消息给管理员：" + result[5] + ",时间为：" + LocalDateTime.now());
                }

                for(int i = 0; i < identifyGoods.size(); ++i) {
                    String[] ItemListValues;
                    if (identifyGoods.get(i).contains("破损")) {
                        ItemListValues = new String[]{String.valueOf(cardId), "", identifyGoods.get(i), "DAMAGED", "破损", "1", dateTime, identifyProbabilitys.get(i)};
                        DatabaseUtils.writeToDatabase("ItemList", this.ItemListColumn, ItemListValues);
                    } else if (identifyGoods.get(i).contains("缺货")) {
                        ItemListValues = new String[]{String.valueOf(cardId), "", identifyGoods.get(i), "STOCKOUT", "缺货", "1", dateTime, identifyProbabilitys.get(i)};
                        DatabaseUtils.writeToDatabase("ItemList", this.ItemListColumn, ItemListValues);
                    }
                }

                resultJSON.put("success", "接收数据成功");
                System.out.println(resultJSON);
                return resultJSON;
            } else {
                int upDataCount = this.cardListMapper.upDataImgelUrl(imageUrl, imageUrlLarge, identifyGoods.size(), cardId);
                System.out.println(cardId+":"+shopName+"，数据更新成功");
                this.cardListMapper.deleteItemList(cardId);
                for(int i = 0; i < identifyGoods.size(); ++i) {
                    String[] ItemListValues;
                    if (identifyGoods.get(i).contains("破损")) {
                        ItemListValues = new String[]{String.valueOf(cardId), "", identifyGoods.get(i), "DAMAGED", "破损", "1", dateTime, identifyProbabilitys.get(i)};
                        DatabaseUtils.writeToDatabase("ItemList", this.ItemListColumn, ItemListValues);
                    } else if (identifyGoods.get(i).contains("缺货")) {
                        ItemListValues = new String[]{String.valueOf(cardId), "", identifyGoods.get(i), "STOCKOUT", "缺货", "1", dateTime, identifyProbabilitys.get(i)};
                        DatabaseUtils.writeToDatabase("ItemList", this.ItemListColumn, ItemListValues);
                    }
                }
                LocalDateTime cardCreateTime = this.cardListMapper.getCardDate(shopName, corridor, "PENDING", 1);
                Duration between = Duration.between(cardCreateTime, LocalDateTime.now());
                long seconds = between.getSeconds();
                IdUserId idUserId = this.cardListMapper.getIdUserId(shopName, corridor, "PENDING", 1);
                if (seconds >= 7200L) {
                    result[4] = idUserId.getCardId().toString();
                    result[5] = idUserId.getUserId();
                    boolean bool = this.cardListMapper.upDateCreateTime(cardId, dateTime);
                    System.out.println("通道" + corridor + "的数据修改：" + bool);
                    RobotMessage.sendMessage(result);
                    System.out.println("发送消息给店员：" + result[5] + ",时间为：" + LocalDateTime.now());
                    for (String s : authorId) {
                        result[5] = s;
                        RobotMessage.sendMessage(result);
                        System.out.println("发送消息给管理员：" + result[5] + ",时间为：" + LocalDateTime.now());
                    }
                }
                resultJSON.put("success", "接收数据成功");
                System.out.println(resultJSON);
                return resultJSON;
            }
        }
    }

    public Integer getId() {
        return 1;
    }
}
