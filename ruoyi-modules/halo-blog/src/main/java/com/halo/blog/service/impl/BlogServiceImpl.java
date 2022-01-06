package com.halo.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.blog.domain.Blog;
import com.halo.blog.mapper.BlogMapper;
import com.halo.blog.service.BlogService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Halo
 * @create 2021/12/06 下午 04:45
 * @description
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Resource
    BlogMapper blogMapper;


    @Override
    public IPage<Blog> getAllBlog(int currentPage) {
        Page<Blog> page = new Page<>(currentPage, 5);
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        // 排除内容列
        wrapper.select("id", "user_id", "blog_title", "description", "created", "status", "blog_cover", "blog_like", "update_time");
        wrapper.orderByDesc("update_time");
        return blogMapper.selectPage(page, wrapper);
    }

    @Override
    public Blog[] getMostLikeBlog(int blogCount) {
        return blogMapper.getMostLikeBlog(blogCount);
    }

    @Override
    public Blog getBlogById(long blogId) {
        Blog blog = blogMapper.selectById(blogId);
        Assert.notNull(blog, "该博客不存在或已被删除");
        return blog;
    }


    @Override
    public Boolean deleteBlog(long blogId) {
        return blogMapper.deleteById(blogId) == 1;
    }

    @Override
    public Integer giveLike(Long blogId) {
        // 数据库中查询点赞数
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("id", blogId).select("blog_like");
        Blog blog = blogMapper.selectOne(wrapper);
        // 点赞数加一
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", blogId).set("blog_like", blog.getBlogLike() + 1);
        blogMapper.update(null, updateWrapper);

        return blog.getBlogLike() + 1;
    }

    @Override
    public R editBlog(Blog blog) {
        Blog temp = blogMapper.selectById(blog.getId());
        // TODO 权限校验
        blog.setUpdateTime(LocalDateTime.now());
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "blogLike", "create_time");
        int insert = blogMapper.updateById(temp);
        // 从 Redis 中删除该博客缓存
//        cleanRedis(blog.getId());
        return insert == 1 ? R.ok() : R.fail();
    }

    @Override
    public R newBlog(Blog blog) {
        Blog temp = new Blog();
        // 获取用户ID
        temp.setUserId(blog.getUserId());
        temp.setCreated(LocalDateTime.now());
        temp.setCreateTime(LocalDateTime.now());
        temp.setCollectCount(0);
        temp.setStatus(0);
        blog.setUpdateTime(LocalDateTime.now());
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "blogLike", "create_time");
        int insert = blogMapper.insert(temp);
        // 删除 Redis 缓存
        // cleanRedis();
        return insert == 1 ? R.ok() : R.fail();
    }
}
