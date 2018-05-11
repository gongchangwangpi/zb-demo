package com.spring.service;

/**
 * 事物传播测试
 *
 * Created by books on 2017/8/23.
 */
public interface TransactionService {


    void required();

    void requires_new(Long id);

    void supports();

    void nested(Long id);

    void manualCommit(Long id);

}
