package com.dingtalk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dingtalk.model.CardList;
//import com.dingtalk.model.InspectionList;
import com.dingtalk.model.IdUserId;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Mapper
public interface CardListMapper extends BaseMapper<CardList> {
    @Select("SELECT `cardId` FROM CardList WHERE shopName = #{shopName} and channelId = #{channelId} and cardStatus = #{cardStatus} and published = #{published}")
    Integer getToId(@Param("shopName") String shopName, @Param("channelId") String channelId, @Param("cardStatus") String cardStatus, @Param("published") Integer published);

    @Select("SELECT `cardId`,`userId` FROM CardList WHERE shopName = #{shopName} and channelId = #{channelId} and cardStatus = #{cardStatus} and published = #{published}")
    IdUserId getIdUserId(@Param("shopName") String shopName, @Param("channelId") String channelId, @Param("cardStatus") String cardStatus, @Param("published") Integer published);

    @Select("SELECT DISTINCT `userId` FROM stream_data WHERE departName like concat('%',#{departName},'%')")
    ArrayList<String> getListUserId(@Param("departName") String departName);

//    @Select("SELECT DISTINCT `shop_name` FROM shop_dim WHERE shop_no = #{shopId}")
//    String getShopName(@Param("shopId") String shopId);

//    @Select("Select DISTINCT `storeName` from shop_setting where storeId = #{storeId}")
//    String getShopName(@Param("storeId") String storeId);

    @Select("select id from user where role = 'Author'")
    ArrayList<String> getAuthorId();

//    @Select("SELECT `id` FROM topic WHERE topicTitle = #{topicTitle}")
//    String getShopUid(@Param("topicTitle") String topicTitle);

    @Select("SELECT `cardCreateTime` FROM CardList WHERE shopName = #{shopName} and channelId = #{channelId} and cardStatus = #{cardStatus} and published = #{published}")
    LocalDateTime getCardDate(@Param("shopName") String shopName, @Param("channelId") String channelId, @Param("cardStatus") String cardStatus, @Param("published") Integer published);

    //    enum('PENDING', 'OVERDUE', 'PROCESSED', 'OPROCESSED', 'UNABLE', 'MISSIDEN')
    @Update("update CardList set published = 0 where cardStatus = 'PENDING' and published = 1")
    int getListShopId();

    @Update("update CardList set imageUrl = #{imageUrl}, imageUrlLarge = #{imageUrlLarge} ,itemCount = #{itemCount} where cardId = ${cardId}")
    int upDataImgelUrl(@Param("imageUrl")String imageUrl,@Param("imageUrlLarge")String imageUrlLarge,@Param("itemCount")Integer itemCount,@Param("cardId")Integer cardId);

//    {"cardId", "ItemId", "ItemName", "itemStatus", "itemStausName", "published", "itemCreateTime", "rating"};
    @Delete("delete from itemList where cardId = ${cardId}")
    int deleteItemList(@Param("cardId") Integer cardId);

    @Update("update CardList set cardCreateTime = #{cardCreateTime} where cardId = #{cardId}")
    boolean upDateCreateTime(@Param("cardId") Integer cardId, @Param("cardCreateTime") String cardCreateTime);

//    @Insert()
//    int insertData(@Param())

//    @Select("SELECT `cardId` FROM CardList WHERE channelName = #{channelName} and cardStatus = #{cardStatus}")
//    Integer[] getToIdList(@Param("channelName") String channelName,@Param("cardStatus") String cardStatus);
}
