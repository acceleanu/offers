package com.deltapunkt.start;

import com.deltapunkt.start.repo.OffersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OffersConfiguration {
    @Bean
    OffersRepository offersRepository() {
        return new OffersRepository();
    }
}
