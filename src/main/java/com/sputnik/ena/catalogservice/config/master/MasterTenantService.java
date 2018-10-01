package com.sputnik.ena.catalogservice.config.master;

import org.springframework.data.repository.query.Param;

public interface MasterTenantService {
	MasterTenant findByTenantId(@Param("tenantId") String tenantId);

}
