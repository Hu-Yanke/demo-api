package com.common.datasource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Objects;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018/12/6 0006 14:19
 */
@Configuration
@MapperScan(basePackages = {"com.mapper.mysql"}, sqlSessionFactoryRef = "mysqlSqlSessionFactory")
public class MysqlDataSourceConfig {

	@Primary
	@Bean(name = "mysqlDataSource")
	@ConfigurationProperties(prefix = "datasource.mysql")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Resource
	private MybatisProperties properties;

	@Primary
	@Bean(name = "mysqlSqlSessionFactory")
	public SqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
			sqlSessionFactoryBean.setMapperLocations(this.properties.resolveMapperLocations());
		}
		return sqlSessionFactoryBean;
	}

	@Bean(name = "mysqlTransactionManager")
	public PlatformTransactionManager mysqlTransactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}
