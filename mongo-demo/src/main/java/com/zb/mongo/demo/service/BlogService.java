package com.zb.mongo.demo.service;

import com.zb.mongo.demo.domain.Blog;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

/**
 * @author zhangbo
 * @date 2020-02-14
 */
public interface BlogService {

    void create(Blog blog);

    void update(Blog blog);

    void delete(ObjectId id);

    List<Blog> findByAuthor(String author);

    Optional<Blog> findById(ObjectId id);

    List<Blog> page(int page, int size);
}
