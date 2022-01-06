package com.halo.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.halo.blog.domain.Blog;
import com.halo.blog.service.BlogService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Halo
 * @create 2021/12/06 下午 04:37
 * @description
 */
@RestController
@Api(tags = "博客控制器")
public class BlogController {
    @Autowired
    BlogService blogService;

    /**
     * 获取全部文章
     *
     * @param currentPage
     * @return
     */
    @GetMapping("/blogs")
//    @RequiresPermissions("system:dept:list")
    public R list(@RequestParam(defaultValue = "1") Integer currentPage) {
        IPage<Blog> allBlog = blogService.getAllBlog(currentPage);
        return R.ok(allBlog);
    }

    /**
     * 工具文章 ID 查询文章
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable(name = "id") Long id) {
        return R.ok(blogService.getBlogById(id));
    }

    /**
     * 获取点赞数最高的文章
     */
    @GetMapping("/blogs/most/like/{blogCount}")
    public R mostLike(@PathVariable(name = "blogCount") int blogCount) {
        return R.ok(blogService.getMostLikeBlog(blogCount));
    }

    @PostMapping("/edit")
    public R edit(@Validated @RequestBody Blog blog) {
        Long blogId = blog.getId();
        // TODO 需要验证传入的用户ID是否正确
        // 判断是新建还是编辑
        if (blogId == null) {
            return blogService.newBlog(blog);
        } else {
            return blogService.editBlog(blog);
        }
    }

    /**
     * 根据文章ID，给对应文章点赞
     */
    @GetMapping("/like/{blogId}")
    public R like(@PathVariable(name = "blogId") Long blogId) {
        // TODO 一个用户只能点一个赞
        return R.ok(blogService.giveLike(blogId), "点赞成功");
    }

}
