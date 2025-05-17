package com.healthcareplatform.AuthenticationService.config.database;

import com.healthcareplatform.AuthenticationService.config.VaultConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {

    @Autowired
    private VaultConfig vaultConfig;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(vaultConfig.getDriverClassName());
        dataSource.setUrl(vaultConfig.getDbUrl());
        dataSource.setUsername(vaultConfig.getDbUsername());
        dataSource.setPassword(vaultConfig.getDbPassword());
        return dataSource;
    }


}
