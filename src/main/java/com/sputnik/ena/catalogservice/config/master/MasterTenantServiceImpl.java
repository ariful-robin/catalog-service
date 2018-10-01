package com.sputnik.ena.catalogservice.config.master;

import org.springframework.beans.factory.annotation.Autowired;

public class MasterTenantServiceImpl implements MasterTenantService {

	@Autowired
    MasterTenantRepository masterTenantRepo;

    @Override
    public MasterTenant findByTenantId(String tenantId) {
        return masterTenantRepo.findByTenantId(tenantId);
    }
}
