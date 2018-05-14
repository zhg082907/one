package com.qhgrain.mapper;

import com.qhgrain.pojo.Version;
import com.qhgrain.vo.VersionVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersionMapper {
    @Select("select id,type,code,status,DATE_FORMAT(begin_date,'%Y-%m-%d %H:%i:%S') beginDate," +
            "DATE_FORMAT(end_date,'%Y-%m-%d %H:%i:%S') endDate,DATE_FORMAT(create_date,'%Y-%m-%d %H:%i:%S') createDate," +
            "create_id createId from version where type = #{type} and status = #{status}")
    public Version getOnVersion(@Param("type") int type,@Param("status") int status);

    @Insert("insert into version (type,code,status,begin_date,create_date,create_id) " +
            "values (#{v.type},#{v.code},#{v.status},#{v.beginDate},#{v.createDate},#{v.createId})")
    @Options(useGeneratedKeys = true,keyProperty = "v.id",keyColumn = "id")
    public void add(@Param("v") Version v);

    @Update("update version set status = #{status},end_date = #{endDate} where id = #{id}")
    public void updVersionStatus(@Param("id") Integer id, @Param("status") int versionOff, @Param("endDate") String date);

    @Select("<script>" +
            "select id,type,code,status,DATE_FORMAT(begin_date,'%Y-%m-%d %H:%i:%S') beginDate," +
            "DATE_FORMAT(end_date,'%Y-%m-%d %H:%i:%S') endDate,DATE_FORMAT(create_date,'%Y-%m-%d %H:%i:%S') createDate," +
            "create_id createId from version " +
            "where 1 = 1 " +
            "<if test = 'type != null'>and type = #{type} </if>" +
            "</script>")
    List<Version> getVersions(@Param("type") Integer type);
}
