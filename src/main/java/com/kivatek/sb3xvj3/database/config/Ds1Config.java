package com.kivatek.sb3xvj3.database.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * Ds1Config
 */
@Configuration
@MapperScan(basePackages = "com.kivatek.sb3xvj3.database.mapper.ds1", sqlSessionFactoryRef = "sqlSessionFactory1")
public class Ds1Config {

    @Bean(name = "ds1")
    @ConfigurationProperties(prefix = "spring.datasource.ds1")
    @Primary
    public DataSource primaryDataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "tx1")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("ds1") final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mybatisConfiguration1")
    @ConfigurationProperties(prefix = "mybatis.configuration")
    @Primary
    public org.apache.ibatis.session.Configuration configuration() {
        return new org.apache.ibatis.session.Configuration();
    }

    @Bean(name = "sqlSessionFactory1", destroyMethod = "")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("ds1") final DataSource dataSource,
            @Qualifier("mybatisConfiguration1") org.apache.ibatis.session.Configuration configuration)
            throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setDataSource(dataSource);
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        return sqlSessionFactory;
    }

    @Bean(name = "sqlSessionTemplate1")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("sqlSessionFactory1") final SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}