package com.epam.esm.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.util.ResourceBundle;

@Configuration
public class DataSourceConfig {

    private static final String DB_DRIVER = "db.driver";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_URL = "db.url";
    private static final String DB_POOL_MAX_SIZE = "db.poolMaxSize";

    @Profile("production")
    @Bean
    public DataSource poolDataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String driverClassName = resource.getString(DB_DRIVER);
        dataSource.setDriverClassName(driverClassName);
        String username = resource.getString(DB_USER);
        dataSource.setUsername(username);
        String password = resource.getString(DB_PASSWORD);
        dataSource.setPassword(password);
        String jdbcUrl = resource.getString(DB_URL);
        dataSource.setJdbcUrl(jdbcUrl);
        int poolMaxSize = Integer.parseInt(resource.getString(DB_POOL_MAX_SIZE));
        dataSource.setMaximumPoolSize(poolMaxSize);
        return dataSource;
    }

    @Profile("development")
    @Bean
    public DataSource embeddedDataSource(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:test-data.sql")
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public TransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
