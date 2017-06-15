package db.builders;

public class SchemaBuilder {
    public String buildUsers() {
        return "CREATE TABLE IF NOT EXISTS `users` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(255) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
    }

    public String buildAddresses() {
        return "CREATE TABLE IF NOT EXISTS `addresses` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `index` int(11) DEFAULT NULL,\n" +
                "  `street` varchar(255) DEFAULT NULL,\n" +
                "  `user_id` bigint(20) NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `FK1fa36y2oqhao3wgg2rw1pi459` (`user_id`),\n" +
                "  CONSTRAINT `FK1fa36y2oqhao3wgg2rw1pi459` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
    }

    public String buildPhones() {
        return "CREATE TABLE IF NOT EXISTS `phones` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `code` int(11) DEFAULT NULL,\n" +
                "  `number` varchar(255) DEFAULT NULL,\n" +
                "  `user_id` bigint(20) NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `FKmg6d77tgqfen7n1g763nvsqe3` (`user_id`),\n" +
                "  CONSTRAINT `FKmg6d77tgqfen7n1g763nvsqe3` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
    }
}
