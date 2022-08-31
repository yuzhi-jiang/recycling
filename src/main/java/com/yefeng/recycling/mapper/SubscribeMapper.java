package com.yefeng.recycling.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yefeng.recycling.entity.Subscribe;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
public interface SubscribeMapper extends BaseMapper<Subscribe> {


    @Insert("insert into subscribe(phone,address,address_point,scheduled_time,user_id,status)" +
            " values(#{phone},#{address},#{addressPoint},#{scheduledTime},#{userId},#{status})")
    Integer insertSubscribe(Subscribe subscribe);


    @Update("update subscribe set subscribe.status=#{status} where id=#{id}")
    Integer updateStatus(Integer id,Integer status);

    @Select("SELECT * from subscribe")
//            @Result(column = "address_point",javaType = MyPoint.class,property = "")
//    @Select("SELECT id,phone,address,ST_AsText(address_point) as address_point,scheduled_time,user_id,create_time,create_by,update_by,update_time,subscribe.status from subscribe")
    List<Subscribe> selectAllSubscribe();

}
