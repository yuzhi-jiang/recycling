package com.yefeng.recycling.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yefeng.recycling.entity.Power;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
public interface PowerMapper extends BaseMapper<Power> {

    @Select("select p.*" +
            "from power as p join role_power rp on p.id = rp.power_id\n" +
            "where rp.role_id=#{roleId}")
    List<Power> findPowerByRoleId(@Param("roleId") Integer roleId);
}
