package com.sputnik.ena.catalogservice.config.tenant;

import org.apache.commons.lang.StringUtils;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;


public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
	private static final String DEFAULT_TENANT_ID = "catalog_default";
	@Override
	public String resolveCurrentTenantIdentifier() {
		String tenant = TenantContext.getCurrentTenant();
		return StringUtils.isNotBlank(tenant)? tenant: DEFAULT_TENANT_ID;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}

}
