package com.yefeng.recycling.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yefeng.recycling.DTO.ArticleDTO;
import com.yefeng.recycling.entity.Article;

import java.util.ArrayList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
public interface IArticleService extends IService<Article> {

    ArrayList<ArticleDTO> getNotices(Integer typeId);
}
