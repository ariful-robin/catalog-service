DROP TABLE IF EXISTS master_tenant;
CREATE TABLE `master_tenant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(30) DEFAULT NULL,
  `tenant_id` varchar(30) DEFAULT NULL,
  `url` varchar(256) DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;