package com.yefeng.recycling.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yefeng.recycling.entity.Task;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
public interface TaskMapper extends BaseMapper<Task> {


    @Insert("insert into task(subscribe_id,call_time,salesman,salesman_phone,status)" +
            " values(#{subscribeId},#{callTime},#{salesman},#{salesmanPhone},#{status})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    Integer insertTask(Task task);



}
