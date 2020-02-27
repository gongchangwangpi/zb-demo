package com.zb.mongo.demo.service.impl;

import com.zb.mongo.demo.domain.Blog;
import com.zb.mongo.demo.repository.BlogRepository;
import com.zb.mongo.demo.service.BlogService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author zhangbo
 * @date 2020-02-14
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void create(Blog blog) {
        blogRepository.save(blog);
    }

    @Override
    public void update(Blog blog) {
        blogRepository.save(blog);
    }

    @Override
    public void delete(ObjectId id) {
        blogRepository.deleteById(id);
    }

    @Override
    public List<Blog> findByAuthor(String author) {
        return blogRepository.findAllByAuthor(author);
    }

    @Override
    public Optional<Blog> findById(ObjectId id) {
        return blogRepository.findById(id);
    }

    @Override
    public List<Blog> page(int page, int size) {
        Query query = new Query();
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, Blog.class);
    }
}
