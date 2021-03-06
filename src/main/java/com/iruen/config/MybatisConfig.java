package com.iruen.config;

import com.iruen.Properties;
import com.iruen.dao.CubeoneTestDao;
import com.iruen.domain.CubeoneTestUser;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Map;

/**
 * Created by donghoon on 2016. 8. 16..
 */
@Configuration
@EnableTransactionManagement
@MapperScan(
        basePackages = {"com.iruen.dao"}
)
public class MybatisConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String dbType;
    private String DB_DIRVER;
    private String DB_URL;
    private String USERNAME;
    private String PASSWORD;

    @Autowired
    public MybatisConfig(Properties properties) {
        dbType = properties.getDbType();
        Map<String, Map<String, String>> servers = properties.getServers();
        Map<String, String> dbInfo;

        if (dbType.equals("mysql")) {
            dbInfo = servers.get("mysql");
        } else if (dbType.equals("oracle")) {
            dbInfo = servers.get("oracle");
        } else {
            dbInfo = servers.get("oracle");
        }

        logger.info("DB Type: {}", dbType);

        this.DB_DIRVER = dbInfo.get("driver");
        this.DB_URL = dbInfo.get("url");
        this.USERNAME = dbInfo.get("username");
        this.PASSWORD = dbInfo.get("password");

        logger.info("This DB_DIRVER is {}", DB_DIRVER);
        logger.info("This DB_URL is {}", DB_URL);
        logger.info("This USERNAME is {}", USERNAME);
        logger.info("This PASSWORD is {}", PASSWORD);
    }

    private DataSource makeComboPooledDataSource(String driverClass, String dbUrl) {

        ComboPooledDataSource datasource = new ComboPooledDataSource();

        try {
            datasource.setDriverClass(driverClass);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        datasource.setJdbcUrl(dbUrl);
        datasource.setUser(USERNAME);
        datasource.setPassword(PASSWORD);
        datasource.setInitialPoolSize(30);
        datasource.setMaxPoolSize(40);

        return datasource;
    }

    @Bean
    public DataSource comboPooledDataSource() {
        return makeComboPooledDataSource(DB_DIRVER, DB_URL);
    }


    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(comboPooledDataSource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

        sessionFactory.setDataSource(comboPooledDataSource());
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mybatis/*-mapper.xml"));

        /**
         * domain 객체에 alias 사용하기 위해 설정하기.
         */
        Class[] typeAliases = {CubeoneTestUser.class};
        sessionFactory.setTypeAliases(typeAliases);

        return sessionFactory.getObject();
    }

    @Bean
    public MapperFactoryBean mapperFactoryBean() throws Exception {
        MapperFactoryBean mapperFactoryBean = new MapperFactoryBean();

        /**
         * dao 인터페이스가 추가되면 설정하기.
         */
        mapperFactoryBean.setMapperInterface(CubeoneTestDao.class);
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());

        return mapperFactoryBean;
    }

}