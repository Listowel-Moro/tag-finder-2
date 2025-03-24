package com.listo.tag_finder.config;

import com.listo.tag_finder.service.ParameterStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Autowired
    private ParameterStoreService parameterStoreService;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(parameterStoreService.getParameter("/tag-finder/db/url"));
        dataSource.setUsername(parameterStoreService.getParameter("/tag-finder/db/username"));
        dataSource.setPassword(parameterStoreService.getParameter("/tag-finder/db/password"));
        return dataSource;
    }
}
