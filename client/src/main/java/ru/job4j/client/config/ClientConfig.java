package ru.job4j.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Class ClientConfig.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 21.12.2021
 */
@Configuration
public class ClientConfig {

    @Bean
    public RestTemplate getTemplate() {
        return new RestTemplate();
    }
}
