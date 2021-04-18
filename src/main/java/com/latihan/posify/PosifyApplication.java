package com.latihan.posify;

import com.latihan.posify.audit.AuditorAwareImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PosifyApplication {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
    public static void main(String[] args) {
        SpringApplication.run(PosifyApplication.class, args);
    }

}
