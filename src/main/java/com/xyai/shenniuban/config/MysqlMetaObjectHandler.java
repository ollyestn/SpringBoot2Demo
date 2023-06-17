package com.xyai.shenniuban.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

public class MysqlMetaObjectHandler implements MetaObjectHandler {

    /**
     * 测试 user 表 name 字段为空自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("*************************");
        System.out.println("insert of mysql fill");
        System.out.println("*************************");
        // 测试下划线
        Object createDatetime = this.getFieldValByName("createDatetime", metaObject);
        System.out.println("createDatetime=" + createDatetime);
        if (createDatetime == null) {
            //测试实体没有的字段，配置在公共填充，不应该set到实体里面
            this.strictInsertFill(metaObject, "createDatetime1", LocalDateTime.class, LocalDateTime.now())
                    .strictInsertFill(metaObject, "createDatetime", LocalDateTime.class, LocalDateTime.now());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("*************************");
        System.out.println("update of mysql fill");
        System.out.println("*************************");
        //测试实体没有的字段，配置在公共填充，不应该set到实体里面
        this.strictUpdateFill(metaObject, "updateDatetime1", LocalDateTime.class, LocalDateTime.now())
                .strictUpdateFill(metaObject, "updateDatetime", LocalDateTime.class, LocalDateTime.now());
    }
}
