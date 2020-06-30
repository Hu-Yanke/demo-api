package com.common.datasource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Version 1.0
 * @Since JDK1.8
 * @Author HYK
 * @Date 2018/12/6 0006 14:16
 */
@Configuration
@MapperScan(basePackages = {"com.mapper.oracle"}, sqlSessionFactoryRef = "oracleSqlSessionFactory")
public class OracleDataSourceConfig {

	@Bean(name = "oracleDataSource")
	@ConfigurationProperties(prefix = "datasource.oracle")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Resource
	private MybatisProperties properties;

	@Bean(name = "oracleSqlSessionFactory")
	public SqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
			sqlSessionFactoryBean.setMapperLocations(this.properties.resolveMapperLocations());
		}
		return sqlSessionFactoryBean;
	}

	@Bean(name = "oracleTransactionManager")
	public PlatformTransactionManager oracleTransactionManager(@Qualifier("oracleDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}
