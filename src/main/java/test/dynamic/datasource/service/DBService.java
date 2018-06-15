package test.dynamic.datasource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.dynamic.datasource.dao.DbTestMapper;
import test.dynamic.datasource.model.DbTest;

import java.util.List;

/**
 * @author wangbx
 * Created by wangbx on 2018/6/14.
 */
@Service
public class DBService {

    @Autowired
    private DbTestMapper dbTestMapper;

    public List<DbTest> selectAll() {
       return dbTestMapper.selectAll();
    }
}
