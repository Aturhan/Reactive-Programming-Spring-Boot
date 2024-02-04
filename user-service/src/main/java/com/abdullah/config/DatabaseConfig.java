package com.abdullah.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties({R2dbcProperties.class})
public class DatabaseConfig extends AbstractR2dbcConfiguration {

    @Value("${findjob.database.host}")
    private String host;

    @Value("${findjob.database.port}")
    private int port;

    @Value("${findjob.database.name}")
    private String name;

    @Value("${findjob.database.username}")
    private String username;

    @Value("${findjob.database.password}")
    private String password;
    @Value("${findjob.database.schema}")
    private String schema;

    @Value("${findjob.database.pool.size.initial}")
    private int initial;

    @Value("${findjob.database.pool.size.max}")
    private int max;



    @Override
    @Bean
    public ConnectionFactory connectionFactory() {

        final PostgresqlConnectionFactory factory = new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host(host)
                        .database(name)
                        .port(port)
                        .username(username)
                        .password(password)
                        .schema(schema)
                        .build()
        );
        final ConnectionPoolConfiguration configuration = ConnectionPoolConfiguration.builder(factory)
                .initialSize(initial)
                .maxSize(max)
                .build();
        return new ConnectionPool(configuration);
    }
    @Bean
    public ReactiveTransactionManager manager(ConnectionFactory factory){
        return new R2dbcTransactionManager(factory);
    }

}
