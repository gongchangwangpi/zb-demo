package com.zb.fund.service.fund.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zb.fund.domain.Fund;
import com.zb.fund.mapper.FundMapper;
import com.zb.fund.service.fund.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2019/1/26 0026.
 */
@Service(value = "fundService")
public class FundServiceImpl implements FundService {

    @Autowired
    private FundMapper fundMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
    public long save(Fund fund) {
        fundMapper.insert(fund);
        return fund.getId();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
    public int update(Fund fund) {
        return fundMapper.update(fund);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public Fund get(String fundCode) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public List<Fund> list() {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public Page<Fund> pageList() {
        return null;
    }
}
