DELETE FROM master_tenant;

insert into master_tenant(id, tenant_id, url, username, password,version) VALUES
(1, 'catalog_ena', 'jdbc:mysql://192.168.190.236:3306/catalog_ena?useSSL=false', 'docker', 'admin',0),
(2, 'catalog_dio', 'jdbc:mysql://192.168.190.236:3306/catalog_dio?useSSL=false', 'docker', 'admin',0)
;