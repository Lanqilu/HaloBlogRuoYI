package com.halo.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.halo.blog.domain.Blog;
import com.ruoyi.common.core.domain.R;

/**
 * @author Halo
 * @create 2021/12/06 下午 04:44
 * @description
 */
public interface BlogService extends IService<Blog> {

    /**
     * 返回所有博客
     */
    IPage<Blog> getAllBlog(int currentPage);

    /**
     * 安装点赞数返回博客
     *
     * @param blogCount 需要的数量
     * @return 分页
     */
    Blog[] getMostLikeBlog(int blogCount);

    /**
     * 根据博客ID返回对应博客内容
     *
     * @param blogId 博客ID
     * @return 博客
     */
    Blog getBlogById(long blogId);

    /**
     * 编辑博客
     *
     * @param blog 博客
     * @return 统一结果
     */
    R editBlog(Blog blog);

    /**
     * 新建博客
     *
     * @param blog 博客
     * @return 统一结果
     */
    R newBlog(Blog blog);

    /**
     * 根据博客ID删除对应博客
     *
     * @param blogId 博客ID
     * @return 是否删除成功
     */
    Boolean deleteBlog(long blogId);

    /**
     * 点赞博客
     *
     * @param blogId 博客id
     * @return 点赞数
     */
    Integer giveLike(Long blogId);
}
