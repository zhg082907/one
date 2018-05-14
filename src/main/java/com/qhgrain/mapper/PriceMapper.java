package com.qhgrain.mapper;

import com.qhgrain.pojo.Price;
import com.qhgrain.vo.PricePageVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceMapper {
    @Select("SELECT code from (SELECT LEFT(code,1) a , RIGHT(code,LENGTH(code)-1) b ,CODE,id FROM price WHERE vid=#{vid} ORDER BY b desc,a desc LIMIT 1) c")
    public String getMaxCodeByVersionId(@Param("vid") Integer id);

    @Insert("<script>" +
            "insert into price (price,code,vid,flag,create_date,create_id) values " +
            "<foreach collection ='list' item='item' index= 'index' separator =','> " +
            "(#{item.price},#{item.code},#{item.vid},#{item.flag},#{item.createDate},#{item.createId}) " +
            "</foreach >" +
            "</script>")
    public void batchAdd(@Param("list") List<Price> prices);

    @Select("<script>" +
            "select v.code versionCode,DATE_FORMAT(v.begin_date,'%Y-%m-%d %H:%i:%S') beginDate,DATE_FORMAT(v.end_date,'%Y-%m-%d %H:%i:%S') endDate,p.price price,p.code priceCode " +
            "from version v left join price p on v.id = p.vid " +
            "where v.type = #{type} " +
            "<if test = 'beginDateOne != null'>and v.begin_date &gt;= #{beginDateOne} </if>" +
            "<if test = 'beginDateTwo != null'>and v.end_date &lt;= #{beginDateTwo} </if>" +
            "<if test = 'price != null'>and p.price = #{price} </if>" +
            "<if test = 'code != null'>and p.code like CONCAT('%',#{code},'%') </if>" +
            "<if test = 'status != null'>and v.status = #{status} </if>" +
            "order by versionCode desc , priceCode asc" +
            "</script>")
    public List<PricePageVo> priceList(@Param("type") Integer type,
                                       @Param("beginDateOne") String beginDateOne,
                                       @Param("beginDateTwo") String beginDateTwo,
                                       @Param("price") Double price,
                                       @Param("code") String code,
                                       @Param("status") Integer status);

    @Select("select id,price,code,vid,flag,DATE_FORMAT(create_date,'%Y-%m-%d %H:%i:%S') createDate,create_id createId from price where vid = #{vid}")
    public List<Price> getPriceListByVersionId(@Param("vid") Integer id);

    @Select("select id,price,code,vid,flag,DATE_FORMAT(create_date,'%Y-%m-%d %H:%i:%S') createDate,create_id createId from price where vid = #{vid} and price = #{price}")
    public Price getPriceByVersionIdAndPrice(@Param("vid") Integer id, @Param("price") double price);

    @Select("select id,price,code,vid,flag,DATE_FORMAT(create_date,'%Y-%m-%d %H:%i:%S') createDate,create_id createId from price where vid = #{vid} and code = #{code}")
    public Price getPriceByVersionIdAndCode(@Param("vid") Integer id, @Param("code") String code);

    @Select("select id,price,code,vid,flag,DATE_FORMAT(create_date,'%Y-%m-%d %H:%i:%S') createDate,create_id createId from price")
    public List<Price> priceAll();
}
