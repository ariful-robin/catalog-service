package com.sputnik.ena.catalogservice.config.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterTenantRepository
        extends JpaRepository<MasterTenant, Long> {

    /**
     * Using a custom named query
     * @param tenantId
     * @return
     */
    @Query("select p from MasterTenant p where p.tenantId = :tenantId")
    MasterTenant findByTenantId(@Param("tenantId") String tenantId);
}