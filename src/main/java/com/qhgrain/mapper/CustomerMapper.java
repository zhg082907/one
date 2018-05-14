package com.qhgrain.mapper;

import com.qhgrain.pojo.Customer;
import com.qhgrain.vo.CustomerPageVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMapper {
    @Select("SELECT code from (SELECT LEFT(code,1) a , RIGHT(code,LENGTH(code)-1) b ,CODE,id FROM customer WHERE vid=#{vid} ORDER BY b desc,a desc LIMIT 1) c")
    public String getMaxCodeByVersionId(@Param("vid") Integer id);

    @Insert("<script>" +
            "insert into customer (customer,code,vid,flag,create_date,create_id) values " +
            "<foreach collection ='list' item='item' index= 'index' separator =','> " +
            "(#{item.customer},#{item.code},#{item.vid},#{item.flag},#{item.createDate},#{item.createId}) " +
            "</foreach >" +
            "</script>")
    public void batchAdd(@Param("list") List<Customer> customers);

    @Select("<script>" +
            "select v.code versionCode,DATE_FORMAT(v.begin_date,'%Y-%m-%d %H:%i:%S') beginDate,DATE_FORMAT(v.end_date,'%Y-%m-%d %H:%i:%S') endDate,c.customer customer,c.code customerCode " +
            "from version v left join customer c on v.id = c.vid " +
            "where v.type = #{type} " +
            "<if test = 'beginDateOne != null'>and v.begin_date &gt;= #{beginDateOne} </if>" +
            "<if test = 'beginDateTwo != null'>and v.end_date &lt;= #{beginDateTwo} </if>" +
            "<if test = 'customer != null'>and c.customer like CONCAT('%',#{customer},'%') </if>" +
            "<if test = 'code != null'>and c.code like CONCAT('%',#{code},'%') </if>" +
            "<if test = 'status != null'>and v.status = #{status} </if>" +
            "order by versionCode desc , customerCode asc" +
            "</script>")
    public List<CustomerPageVo> customerList(@Param("type") Integer type,
                                             @Param("beginDateOne") String beginDateOne,
                                             @Param("beginDateTwo") String beginDateTwo,
                                             @Param("customer") String customer,
                                             @Param("code") String code,
                                             @Param("status") Integer status);

    @Select("select id,customer,code,vid,flag,DATE_FORMAT(create_date,'%Y-%m-%d %H:%i:%S') createDate,create_id createId from customer where vid = #{vid}")
    public List<Customer> getCustomerListByVersionId(@Param("vid") Integer id);

    @Select("select id,customer,code,vid,flag,DATE_FORMAT(create_date,'%Y-%m-%d %H:%i:%S') createDate,create_id createId from customer where vid = #{vid} and customer = #{customer}")
    public Customer getCustomerByVersionIdAndCustomer(@Param("vid") Integer id, @Param("customer") String customer);

    @Select("select id,customer,code,vid,flag,DATE_FORMAT(create_date,'%Y-%m-%d %H:%i:%S') createDate,create_id createId from customer where vid = #{vid} and code = #{code}")
    public Customer getCustomerByVersionIdAndCode(@Param("vid") Integer id, @Param("code") String code);

    @Select("select id,customer,code,vid,flag,DATE_FORMAT(create_date,'%Y-%m-%d %H:%i:%S') createDate,create_id createId from customer")
    public List<Customer> customerAll();
}
