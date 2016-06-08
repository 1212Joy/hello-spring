package cn.com.basic.config;

import cn.com.basic.service.HelloWorldService;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;

/**
 * Created by zhaijiayi on 2016/5/3.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"cn.com.basic"})
@MapperScan(sqlSessionFactoryRef = "sessionFactory", basePackages = "cn.com.basic.dao")
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {

    public static final String TRANSACTION_MANAGER = "transactionManager";
    @Bean
    @ConfigurationProperties(prefix="datasource")
    public DataSource datasource() {
        return DataSourceBuilder.create().build();
    }
    @Bean
    public SqlSessionFactoryBean sessionFactory() throws PropertyVetoException, IOException {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setTypeAliasesPackage("cn.com.basic");
        sessionFactory.setDataSource(datasource());


        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:cn/com/basic/mapper/*.xml");
        sessionFactory.setMapperLocations(resources);

        return sessionFactory;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() throws PropertyVetoException {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(datasource());
        dataSourceTransactionManager.setDefaultTimeout(10);
        return dataSourceTransactionManager;
    }

    @Bean
    public HelloWorldService aaa() {

        return new HelloWorldService ();
    }


}
