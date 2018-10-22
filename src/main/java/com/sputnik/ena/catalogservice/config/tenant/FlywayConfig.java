package com.sputnik.ena.catalogservice.config.tenant;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sputnik.ena.catalogservice.config.master.MasterTenantRepository;

/**
 * This class is a responsible to configure flyway. Default schema 
 * and all the tenant schemas will be updated through flyway based on
 * migration script stored in resources/db/migration
 * @author robin
 *
 */
@Configuration
public class FlywayConfig {
	public static String DEFAULT_SCHEMA = "catalog";

    private Logger logger = LoggerFactory.getLogger(getClass());


	@Bean
	@SuppressWarnings("deprecation")
    public Flyway flyway(DataSource dataSource) {
        logger.info("Migrating default schema ");
		Flyway flyway = new Flyway();
        flyway.setLocations("db/migration/default");
        flyway.setDataSource(dataSource);
        flyway.setSchemas("catalog");
        flyway.migrate();
        return flyway;
    }

    @Bean
    public Boolean tenantsFlyway(MasterTenantRepository repository, DataSource dataSource){
        repository.findAll().forEach(tenant -> {
            String schema = tenant.getTenantId();
            Flyway flyway = new Flyway();
            flyway.setLocations("db/migration/tenants");
            flyway.setDataSource(dataSource);
            flyway.setSchemas(schema);
            //flyway.baseline();
            flyway.migrate();
            logger.info("Migration of "+ schema + "was successful");
            
        });
        return true;
    }


}
