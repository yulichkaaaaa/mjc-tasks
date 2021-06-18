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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ResourceBundle;

/**
 * Class configure data sources of application.
 * There are different data sources for different profiles.
 *
 * @author Shuleiko Yulia
 */
@EnableTransactionManagement
@Configuration
public class DataSourceConfig {

    private static final String DATABASE_RESOURCE_BUNDLE = "database";
    private static final String DB_DRIVER = "db.driver";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_URL = "db.url";
    private static final String DB_POOL_MAX_SIZE = "db.poolMaxSize";
    private static final String CREATE_DATABASE_SCRIPT = "classpath:schema.sql";
    private static final String FILL_DATABASE_SCRIPT = "classpath:test-data.sql";

    /**
     * Configure data source for production profile.
     * Use MySql and Hikari connection pool.
     *
     * @return the {@code DataSource object}
     */
    @Profile("production")
    @Bean
    public DataSource poolDataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        ResourceBundle resource = ResourceBundle.getBundle(DATABASE_RESOURCE_BUNDLE);
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

    /**
     * Configure data source for development profile.
     * Use embedded database H2.
     *
     * @return the {@code DataSource object}
     */
    @Profile("development")
    @Bean
    public DataSource embeddedDataSource(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(CREATE_DATABASE_SCRIPT)
                .addScript(FILL_DATABASE_SCRIPT)
                .build();
    }

    /**
     * Create the {@code JdbcTemplate} object as a bean
     *
     * @param dataSource the {@code DataSource} object
     * @return the {@code JdbcTemplate} object
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * Create the {@code TransactionManager} object as a bean
     *
     * @param dataSource the {@code DataSource} object
     * @return the {@code TransactionManager} object
     */
    @Bean
    public TransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
