package com.xyai.shenniuban.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.xyai.shenniuban.enums.TestEnum;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
public class TestData implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField(jdbcType = JdbcType.INTEGER)
    private Integer testInt;
    @TableField("test_str")
    private String testStr;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    private TestEnum testEnum;

    @Version
    private Integer version;
    /**
     * 多租户
     * 不用配置实体字段,但是数据库需要该字段
     */
    private Long tenantId;
}
