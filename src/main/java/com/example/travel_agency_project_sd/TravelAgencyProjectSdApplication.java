package com.example.travel_agency_project_sd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan({"controller","models", "repositories", "services"})
@EntityScan("models")
@EnableJpaRepositories("repositories")
public class TravelAgencyProjectSdApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelAgencyProjectSdApplication.class, args);
    }

}
