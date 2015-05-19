package com.bko;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bko.domain.User;
import com.bko.persistence.UserDAO;
import com.bko.persistence.UserDAOImpl;



@Configuration
@ComponentScan(basePackages = "com.bko")
@PropertySource({ "classpath:mysql.properties" })
//@PropertySource("classpath:oracle.properties")
//@ComponentScan(basePackages = "com.bko")
@EnableTransactionManagement
public class DataBaseConfig {
	// http://www.baeldung.com/hibernate-4-spring

	@Autowired
	private Environment env;

	/*
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://192.168.1.7:3306/usersdb");
		dataSource.setUsername("ben");
		dataSource.setPassword("eritrea2012");
		return dataSource;
	}
	*/
	
	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

		return transactionManager;
	}
	@Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
    	LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
    	sessionBuilder.addProperties(hibernateProperties());
    	sessionBuilder.addAnnotatedClasses(User.class);
    	return sessionBuilder.buildSessionFactory();
    }
/*
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.bko.persistence" });
		sessionFactory.setHibernateProperties(hibernateProperties());

		return sessionFactory;
	}
	*/
   
	@Bean
    public DataSource dataSource() {
		System.out.println("Class Name:" + env.getProperty("jdbc.driverClassName"));
        final BasicDataSource dataSource = new BasicDataSource();
        
        //dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("jdbc.driverClassName")));
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        //dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("jdbc.url")));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        //dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("jdbc.user")));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        //dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("jdbc.pass")));
        dataSource.setPassword(env.getProperty("jdbc.password"));

        return dataSource;
    }
    
    
    final Properties hibernateProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));

        hibernateProperties.setProperty("hibernate.show_sql", "true");
        // hibernateProperties.setProperty("hibernate.format_sql", "true");
        // hibernateProperties.setProperty("hibernate.globally_quoted_identifiers", "true");

        return hibernateProperties;
    }
    /*
    @Autowired
    @Bean(name = "userDao")
    public UserDAO getUserDao(SessionFactory sessionFactory) {
    	return new UserDAOImpl(sessionFactory);
    }
    */
}
