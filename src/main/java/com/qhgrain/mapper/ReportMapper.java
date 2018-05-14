package com.qhgrain.mapper;

import com.qhgrain.pojo.Report;
import com.qhgrain.vo.ReportPageVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ReportMapper {
    @Select("<script>" +
            "select out_way outWay,out_notice_num outNoticeNum,DATE_FORMAT(billing_date,'%Y-%m-%d %H:%i:%S') billingDate,DATE_FORMAT(out_date,'%Y-%m-%d %H:%i:%S') outDate," +
            "customer,grain_oil_kind grainOilKind,item_name itemName,grade,spec,num,wh_num whNum,location,unit_price unitPrice,total_price totalPrice " +
            "from report " +
            "where 1 = 1 " +
            "<if test = 'billingDateOne != null'>and billing_date &gt;= #{billingDateOne} </if>" +
            "<if test = 'billingDateTwo != null'>and billing_date &lt;= #{billingDateTwo} </if>" +
            "<if test = 'outDateOne != null'>and out_date &gt;= #{outDateOne} </if>" +
            "<if test = 'outDateTwo != null'>and out_date &lt;= #{outDateTwo} </if>" +
            "<if test = 'outWay != null'>and out_way = #{outWay} </if>" +
            "<if test = 'grainOilKind != null'>and grain_oil_kind = #{grainOilKind} </if>" +
            "<if test = 'itemName != null'>and item_name like CONCAT('%',#{itemName},'%') </if>" +
            "<if test = 'customer != null'>and customer like CONCAT('%',#{customer},'%') </if>" +
            "</script>")
    public List<ReportPageVo> reportList(@Param("billingDateOne") String billingDateOne,
                                         @Param("billingDateTwo") String billingDateTwo,
                                         @Param("outDateOne") String outDateOne,
                                         @Param("outDateTwo") String outDateTwo,
                                         @Param("outWay") String outWay,
                                         @Param("grainOilKind") String grainOilKind,
                                         @Param("itemName") String itemName,
                                         @Param("customer") String customer);

    @Insert("<script>" +
            "insert into report (out_way,out_notice_num,billing_date,out_date,customer,grain_oil_kind," +
            "item_name,grade,spec,num,wh_num,location,unit_price,total_price,create_id,create_date) values " +
            "<foreach collection ='list' item='item' index= 'index' separator =','> " +
            "(#{item.outWay},#{item.outNoticeNum},#{item.billingDate},#{item.outDate},#{item.customer},#{item.grainOilKind}," +
            "#{item.itemName},#{item.grade},#{item.spec},#{item.num},#{item.whNum},#{item.location},#{item.unitPrice},#{item.totalPrice},#{item.createId},#{item.createDate}) " +
            "</foreach >" +
            "</script>")
    public void batchAdd(List<Report> list);

    @Select("SELECT CONCAT(out_notice_num,out_date,grain_oil_kind,item_name,grade,spec,wh_num,location) FROM report ")
    public Set<String> getKeys();
}
