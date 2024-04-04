package kz.sdu.project.config;

import kz.sdu.project.repository.PersonRepo;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestConfig {
    @Bean
    @Primary
    public PersonRepo getUserRepository() {
        return Mockito.mock(PersonRepo.class);
    }
}