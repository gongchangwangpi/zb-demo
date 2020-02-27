package com.zb.mongo.demo.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author zhangbo
 * @date 2020-02-14
 */
@Data
@Document(value = "blog")
public class Blog {

    @Id
    private ObjectId id;

    private String title;

    private String content;

    @Indexed
    private String author;

    @Indexed
    private LocalDateTime createTime;

}
