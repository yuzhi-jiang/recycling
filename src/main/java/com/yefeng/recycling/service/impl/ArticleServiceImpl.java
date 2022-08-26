package com.yefeng.recycling.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yefeng.recycling.DTO.ArticleDTO;
import com.yefeng.recycling.entity.Article;
import com.yefeng.recycling.entity.VArticlePO;
import com.yefeng.recycling.mapper.ArticleMapper;
import com.yefeng.recycling.service.IArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yefeng
 * @since 2022-08-23
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {




    @Resource
    ArticleMapper articleMapper;

    @Override
    public ArrayList<ArticleDTO> getNotices(Integer typeId) {
        ArrayList<VArticlePO> articlePOList = articleMapper.findArticleFromViewByTypeId(typeId);
//        if (articlePOList==null||articlePOList.size()==0)return null;
        return (ArrayList<ArticleDTO>) articlePOList.stream().map(po -> {
            ArticleDTO articleDTO = new ArticleDTO();
            BeanUtils.copyProperties(po, articleDTO);
            return articleDTO;
        }).collect(Collectors.toList());

    }
}
