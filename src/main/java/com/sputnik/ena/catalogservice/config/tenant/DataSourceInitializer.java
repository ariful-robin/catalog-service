package com.sputnik.ena.catalogservice.config.tenant;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sputnik.ena.catalogservice.config.master.MasterTenant;
import com.zaxxer.hikari.HikariDataSource;

public class DataSourceInitializer {
	private static Logger logger = LoggerFactory.getLogger(TenantContext.class.getName());

	/**
	 * @author robin
	 * @param
	 * @return DataSource
	 */
	public static DataSource createAndConfigureDataSource(
			MasterTenant tenant) {
		
		HikariDataSource ds = new HikariDataSource();
		ds.setUsername(tenant.getUsername());
		ds.setPassword(tenant.getPassword());
		ds.setJdbcUrl(tenant.getUrl());
		ds.setDriverClassName("com.mysql.jdbc.Driver");

		// HikariCP settings - could come from the master_tenant table but
		// hardcoded here for brevity
		// Maximum waiting time for a connection from the pool
		ds.setConnectionTimeout(20000);

		// Minimum number of idle connections in the pool
		ds.setMinimumIdle(10);

		// Maximum number of actual connection in the pool
		ds.setMaximumPoolSize(20);

		// Maximum time that a connection is allowed to sit idle in the pool
		ds.setIdleTimeout(300000);
		ds.setConnectionTimeout(20000);

		// Setting up a pool name for each tenant datasource
		String tenantId = tenant.getTenantId();
		String tenantConnectionPoolName = tenantId + "-connection-pool";
		ds.setPoolName(tenantConnectionPoolName);
		
		logger.info("Configured datasource:" + tenant.getTenantId()
		+ ". Connection poolname:" + tenantConnectionPoolName);
		return ds;
	}
	
	/**
	 * This method is responsible for creating a new tenant schema based on given datasource only if
	 * the schema doesn't exist currently.
	 * @param ds The given datasource
	 * @return Booelean This return true if successful and false otherwise
	 */
	@SuppressWarnings("deprecation")
	public static Boolean createTenant(DataSource ds, String tenantIdentifier) {
		Flyway flyway = new Flyway();
		
		//check the existing tenant list
		/*String[] schemas = flyway.getSchemas();
		List<String> schemaList = Arrays.asList(schemas);
		if(schemaList.contains(tenantIdentifier)) {
			logger.info("Tenant " + tenantIdentifier + "already exist.");
			return false;
		}*/
		
		try {
			flyway.setLocations("db/migration/tenants");
	        flyway.setDataSource(ds);
	        flyway.setSchemas(tenantIdentifier);
	        flyway.migrate();
		}catch(Exception e) {
			logger.info("Tenant Creation Failed");
			return false;
		}
        
		return true;
	}
}
