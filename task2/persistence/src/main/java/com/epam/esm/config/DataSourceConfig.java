package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Class configure data sources of application.
 * There are different data sources for different profiles.
 *
 * @author Shuleiko Yulia
 */
@EnableTransactionManagement
@Configuration
@PropertySource("classpath:database.properties")
public class DataSourceConfig {

    private static final String CREATE_DATABASE_SCRIPT = "classpath:schema.sql";
    private static final String FILL_DATABASE_SCRIPT = "classpath:test-data.sql";

    /**
     * Configure connection pool.
     *
     * @return the {@code HikariConfig} object
     */
    @Bean
    @ConfigurationProperties(prefix = "prod.datasource")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    /**
     * Configure data source for production profile.
     * Use MySql database.
     *
     * @return the {@code HikariDataSource} object
     */
    @Bean
    @Profile("production")
    public DataSource hikariDataSource() {
        return new HikariDataSource(hikariConfig());
    }

    /**
     * Configure data source for development profile.
     * Use embedded database H2.
     *
     * @return the {@code DataSource object}
     */
    @Profile("development")
    @Bean
    public DataSource embeddedDataSource() {
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
}
