package org.lzs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean(name = "datasource1")
    @Primary
    @ConfigurationProperties(prefix = "datasource1.spring.datasource") // application.properteis中对应属性的前缀
    public DataSource dataSource1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "datasource2")
    @ConfigurationProperties(prefix = "datasource2.spring.datasource") // application.properteis中对应属性的前缀
    public DataSource ssoDataSource() {
        return DataSourceBuilder.create().build();
    }
}
