package com.yefeng.recycling.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yefeng.recycling.entity.Article;
import com.yefeng.recycling.entity.VArticlePO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
public interface ArticleMapper extends BaseMapper<Article> {

    @Select("SELECT * from v_article where type_id=#{type_id}")
    ArrayList<VArticlePO> findArticleFromViewByTypeId(@Param("type_id") Integer typeId);

}
