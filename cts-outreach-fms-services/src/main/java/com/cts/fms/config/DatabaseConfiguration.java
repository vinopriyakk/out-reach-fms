package com.cts.fms.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.transaction.annotation.EnableTransactionManagement;

//import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;


@Configuration
@EnableJpaRepositories("com.cts.fms.repository")
//@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
//@EnableTransactionManagement
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private final Environment env;

    public DatabaseConfiguration(Environment env) {
        this.env = env;
    }

    /*@Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }*/
}
