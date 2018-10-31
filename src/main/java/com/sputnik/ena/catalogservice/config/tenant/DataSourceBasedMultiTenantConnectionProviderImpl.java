package com.sputnik.ena.catalogservice.config.tenant;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sputnik.ena.catalogservice.config.master.MasterTenant;
import com.sputnik.ena.catalogservice.config.master.MasterTenantRepository;

public class DataSourceBasedMultiTenantConnectionProviderImpl
	extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
	
	private static final Logger logger = LoggerFactory
			.getLogger(DataSourceBasedMultiTenantConnectionProviderImpl.class);

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MasterTenantRepository masterTenantRepo;

    /**
     * Map to store the tenant ids as key and the data source as the value
     */
    private Map<String, DataSource> dataSourcesMtApp = new TreeMap<>();
    
    
	@Override
	protected DataSource selectAnyDataSource() {
		if (dataSourcesMtApp.isEmpty()) {
            List<MasterTenant> masterTenants = masterTenantRepo.findAll();
            logger.info(">>>> selectAnyDataSource() -- Total tenants:" + masterTenants.size());
            for (MasterTenant masterTenant : masterTenants) {
                dataSourcesMtApp.put(masterTenant.getTenantId(),
                        DataSourceInitializer.createAndConfigureDataSource(masterTenant));
            }
        }
        return this.dataSourcesMtApp.values().iterator().next();
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		 // If the requested tenant id is not present check for it in the master
        // database 'master_tenant' table

        //tenantIdentifier = initializeTenantIfLost(tenantIdentifier);

        //if any tenant is not found in dataSourcesMtApp then whole list of tenants 
		//is fetched and datasource map is reinitialized. 
		if (!this.dataSourcesMtApp.containsKey(tenantIdentifier)) {
            List<MasterTenant> masterTenants = masterTenantRepo.findAll();
            logger.info(
                    ">>>> selectDataSource() -- tenant:" + tenantIdentifier + " Total tenants:" + masterTenants.size());
            for (MasterTenant masterTenant : masterTenants) {
            	DataSource datasource = DataSourceInitializer.createAndConfigureDataSource(masterTenant);
                dataSourcesMtApp.put(masterTenant.getTenantId(),
                        datasource);
            }
        }
        return this.dataSourcesMtApp.get(tenantIdentifier);
	}
	
	/*private String initializeTenantIfLost(String tenantIdentifier) {
        if (TenantContextHolder.getTenant() == null) {

            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            CustomUserDetails customUserDetails = null;
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                customUserDetails = principal instanceof CustomUserDetails ? (CustomUserDetails) principal : null;
            }
            TenantContextHolder.setTenantId(customUserDetails.getTenant());
        }

        if (tenantIdentifier != TenantContextHolder.getTenant()) {
            tenantIdentifier = TenantContextHolder.getTenant();
        }
        return tenantIdentifier;
    }*/
}
