package com.dealchecker.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories("com.dealchecker.repository")
@PropertySource("classpath:persistence-${environment}.properties")
public class AppConfig {
	
	@Resource
	private Environment environment;
	
	@Bean
	public DataSource dataSource() {
		Properties properties = new Properties();
		properties.setProperty("maximumPoolSize", environment.getRequiredProperty("jdbc.maximum.pool.size"));
		properties.setProperty("dataSourceClassName", environment.getRequiredProperty("jdbc.data.source.class.name"));
		properties.setProperty("dataSource.user", environment.getRequiredProperty("jdbc.username"));
		properties.setProperty("dataSource.password", environment.getRequiredProperty("jdbc.password"));
		properties.setProperty("dataSource.url", environment.getRequiredProperty("jdbc.url"));
		properties.setProperty("dataSource.cachePrepStmts", environment.getRequiredProperty("jdbc.cachePrepStmts"));
		properties.setProperty("dataSource.prepStmtCacheSize", environment.getRequiredProperty("jdbc.prepStmtCacheSize"));
		properties.setProperty("dataSource.prepStmtCacheSqlLimit", environment.getRequiredProperty("jdbc.prepStmtCacheSqlLimit"));
		properties.setProperty("dataSource.useServerPrepStmts", environment.getRequiredProperty("jdbc.useServerPrepStmts"));
		
		// you can fallback to apache commons dbcp if you guys want
		// but this hikariCP seems really fast
		HikariConfig config = new HikariConfig(properties);
		return new HikariDataSource(config);
	}
	
	@Bean
    public EntityManagerFactory entityManagerFactory() {
		
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(Boolean.parseBoolean(environment.getRequiredProperty("hibernate.show_sql")));
		
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource());
        factory.setPackagesToScan(environment.getRequiredProperty("hibernate.packages.to.scan"));
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.afterPropertiesSet();

        return factory.getObject();
    }
	
	@Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
