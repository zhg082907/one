package com.qhgrain.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GokMapper {

    @Insert("<script>" +
            "INSERT INTO gok (gok_name) VALUES " +
            "<foreach collection ='list' item='item' index= 'index' separator =','> " +
            "(#{item}) " +
            "</foreach >" +
            "ON DUPLICATE KEY UPDATE " +
            "gok_name = VALUES(gok_name)" +
            "</script>")
    public void batchAdd(@Param("list") List<String> gokList);

    @Select("select gok_name from gok")
    public List<String> getGokList();
}
