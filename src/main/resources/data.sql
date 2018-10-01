DELETE FROM products;

insert into products(id, code, name, description, price) VALUES
(1, 'P001', 'Product 1', 'Product 1 description', 25),
(2, 'P002', 'Product 2', 'Product 2 description', 32),
(3, 'P003', 'Product 3', 'Product 3 description', 50)
;

DELETE FROM master_tenant;

insert into master_tenant(id, tenant_id, url, username, password,version) VALUES
(1, 'catalog_sputnik', 'jdbc:mysql://192.168.170.140:3306/catalog?useSSL=false', 'docker', 'admin',0)
;