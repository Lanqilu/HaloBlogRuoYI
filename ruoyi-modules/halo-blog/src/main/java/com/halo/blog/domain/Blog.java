package com.halo.blog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Halo
 * @create 2021/12/06 下午 04:39
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_blog")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    @NotBlank(message = "标题不能为空")
    private String blogTitle;

    private String description;

    @NotBlank(message = "内容不能为空")
    private String content;

    private String contentHtml;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime created;

    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String blogCover;

    private Integer status;

    private Integer blogLike;

    private String tagUid;

    private Integer collectCount;

    private String blogSortUid;

    private Integer isPublish;

    private Integer isOpenComment;

    private Integer isOriginal;

    @TableLogic
    private Integer deleted;
}