package com.spring.service;

/**
 * Created by books on 2017/8/24.
 */
public interface Transaction2Service {


    void required();

    void requires_new(Long id);

    void supports();

    void nested(Long id);

    void manualCommit(Long id);
}
