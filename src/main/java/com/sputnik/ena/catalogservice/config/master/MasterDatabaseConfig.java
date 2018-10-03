package com.sputnik.ena.catalogservice.config.master;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
		"com.sputnik.ena.catalogservice.config.master" }, 
		entityManagerFactoryRef = "masterEntityManagerFactory", 
		transactionManagerRef = "masterTransactionManager")
public class MasterDatabaseConfig {
	 
	private static final Logger logger = LoggerFactory
			.getLogger(MasterDatabaseConfig.class);
	
	
	/**
     * Creates the master datasource bean which is required for creating the
     * entity manager factory bean <br/>
     * <br/>
     * Note that using names for beans is not mandatory but it is a good
     * practice to ensure that the intended beans are being used where required.
     * 
     * @return
     */
	@Bean(name = "masterDataSource")
	@ConfigurationProperties(prefix = "master.datasource")
    public DataSource masterDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	/**
     * Creates the entity manager factory bean which is required to access the
     * JPA functionalities provided by the JPA persistence provider, i.e.
     * Hibernate in this case. <br/>
     * <br/>
     * Note the <b>{@literal @}Primary</b> annotation which tells Spring boot to
     * create this entity manager as the first thing when starting the
     * application.
     * 
     * @return
     */
	
	@Primary
    @Bean(name = "masterEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        // Set the master data source
        em.setDataSource(masterDataSource());

        // The master tenant entity and repository need to be scanned
        em.setPackagesToScan(
                new String[] { MasterTenant.class.getPackage().getName(),
                        MasterTenantRepository.class.getPackage().getName() });
        // Setting a name for the persistence unit as Spring sets it as
        // 'default' if not defined
        em.setPersistenceUnitName("masterdb-persistence-unit");

        // Setting Hibernate as the JPA provider
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        // Set the hibernate properties
        em.setJpaProperties(hibernateProperties());
        logger.info("Setup of masterEntityManagerFactory succeeded.");
        return em;
    }
	/**
     * This transaction manager is appropriate for applications that use a
     * single JPA EntityManagerFactory for transactional data access. <br/>
     * <br/>
     * Note the <b>{@literal @}Qualifier</b> annotation to ensure that the
     * <tt>masterEntityManagerFactory</tt> is used for setting up the
     * transaction manager.
     * 
     * @param emf
     * @return
     */
    @Bean(name = "masterTransactionManager")
    public JpaTransactionManager masterTransactionManager(
            @Qualifier("masterEntityManagerFactory") EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

	private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(org.hibernate.cfg.Environment.DIALECT,
                "org.hibernate.dialect.MySQL5Dialect");
        properties.put(org.hibernate.cfg.Environment.SHOW_SQL, true);
        properties.put(org.hibernate.cfg.Environment.FORMAT_SQL, true);
        properties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "update");
        return properties;
    }
}
