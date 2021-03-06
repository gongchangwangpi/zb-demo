package com.zb.fund.service.fund.impl;

import com.zb.fund.domain.FundType;
import com.zb.fund.mapper.FundTypeMapper;
import com.zb.fund.service.fund.FundTypeService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2019/1/26 0026.
 */
@Slf4j
@Service(value = "fundTypeService")
public class FundTypeServiceImpl implements FundTypeService {

    @Autowired
    private FundTypeMapper fundTypeMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
    public void save(FundType fundType) {
        fundTypeMapper.insert(fundType);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
    public void update(FundType fundType) {
        fundTypeMapper.update(fundType);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public FundType get(String fundTypeCode) {
        return fundTypeMapper.selectByCode(fundTypeCode);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public List<FundType> list() {
        return fundTypeMapper.selectList();
    }
}
