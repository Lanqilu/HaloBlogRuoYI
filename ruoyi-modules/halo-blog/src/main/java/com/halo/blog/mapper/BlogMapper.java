package com.halo.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.halo.blog.domain.Blog;
import org.apache.ibatis.annotations.Select;

/**
 * @author Halo
 * @create 2021/12/06 下午 04:46
 * @description
 */
public interface BlogMapper extends BaseMapper<Blog> {

    @Select("SELECT id, user_id, blog_title, description, created, status, blog_cover, blog_like, update_time" +
            " FROM m_blog ORDER BY blog_like desc LIMIT #{count}")
    Blog[] getMostLikeBlog(int count);
}
