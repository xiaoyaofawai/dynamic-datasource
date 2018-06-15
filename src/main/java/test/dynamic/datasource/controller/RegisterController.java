package test.dynamic.datasource.controller;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.XmlWebApplicationContext;
import test.dynamic.datasource.datasource.RouteDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author wangbx
 * Created by wangbx on 2018/6/14.
 */
@Controller
@RequestMapping("/register")
public class RegisterController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @ResponseBody
    @RequestMapping("/datasource")
    public String register(String username, String password, String url, String name) {

        XmlWebApplicationContext webApplicationContext = (XmlWebApplicationContext)applicationContext;
        ConfigurableListableBeanFactory configurableListableBeanFactory = webApplicationContext.getBeanFactory();
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        try {
            ((DruidDataSource) dataSource).init();
            configurableListableBeanFactory.registerSingleton(name, dataSource);
            RouteDataSource routeDataSource = (RouteDataSource)applicationContext.getBean("dataSource");
            routeDataSource.register(name, dataSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
