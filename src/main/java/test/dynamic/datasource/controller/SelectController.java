package test.dynamic.datasource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import test.dynamic.datasource.datasource.RouteDataSource;
import test.dynamic.datasource.model.DbTest;
import test.dynamic.datasource.service.DBService;

import java.util.List;

/**
 * @author wangbx
 * Created by wangbx on 2018/6/14.
 */
@Controller
@RequestMapping("/select")
public class SelectController {

    @Autowired
    private DBService dbService;

    @ResponseBody
    @RequestMapping("/selectAll")
    private List<DbTest> select(String dataSource) {
        RouteDataSource.DATASOURCE.set(dataSource);
        return dbService.selectAll();
    }
}
