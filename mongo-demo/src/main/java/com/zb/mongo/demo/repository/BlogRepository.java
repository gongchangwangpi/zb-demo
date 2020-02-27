package com.zb.mongo.demo.repository;

import com.zb.mongo.demo.domain.Blog;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author zhangbo
 * @date 2020-02-14
 */
public interface BlogRepository extends MongoRepository<Blog, ObjectId> {

    List<Blog> findAllByAuthor(String author);

}
