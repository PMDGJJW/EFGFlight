package com.pmdgjjw.efguser.service.Impl;


import com.pmdgjjw.efguser.service.BaseService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/4/9 19:33
 * @Description
 */

public class BaseImpl<T> implements BaseService<T> {

    @Autowired
    Mapper<T> mapper;

    @Override
    public  int deleteByPrimaryKey(Object o) {
        return mapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(T t) {
        return mapper.delete(t);
    }

    @Override
    public int insert(T t) {
        return mapper.insert(t);
    }

    @Override
    public int insertSelective(T t) {
        return mapper.insertSelective(t);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return mapper.existsWithPrimaryKey(o);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public T selectByPrimaryKey(Object o) {
        return mapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(T t) {
        return mapper.selectCount(t);
    }

    @Override
    public List<T> select(T t) {
        return mapper.select(t);
    }

    @Override
    public T selectOne(T t) {
        return mapper.selectOne(t);
    }

    @Override
    public int updateByPrimaryKey(T t) {
        return mapper.updateByPrimaryKey(t);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        return mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public int deleteByExample(Object o) {
        return mapper.deleteByExample(o);
    }

    @Override
    public List<T> selectByExample(Object o) {
        return mapper.selectByExample(o);
    }

    @Override
    public int selectCountByExample(Object o) {
        return mapper.selectCountByExample(o);
    }

    @Override
    public T selectOneByExample(Object o) {
        return mapper.selectOneByExample(o);
    }

    @Override
    public int updateByExample(T t, Object o) {
        return mapper.updateByExample(t,o);
    }

    @Override
    public int updateByExampleSelective(T t, Object o) {
        return mapper.updateByExampleSelective(t,o);
    }

    @Override
    public List<T> selectByExampleAndRowBounds(Object o, RowBounds rowBounds) {
        return mapper.selectByExampleAndRowBounds(o,rowBounds);
    }

    @Override
    public List<T> selectByRowBounds(T t, RowBounds rowBounds) {
        return mapper.selectByRowBounds(t,rowBounds);
    }
}
