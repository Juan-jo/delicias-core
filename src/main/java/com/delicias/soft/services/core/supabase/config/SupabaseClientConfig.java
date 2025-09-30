package com.delicias.soft.services.core.supabase.config;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SupabaseClientConfig {

    @Bean
    public RestTemplate restTemplate() {

        RequestConfig requestConfig = RequestConfig.custom()
                .setResponseTimeout(5000, java.util.concurrent.TimeUnit.MILLISECONDS)
                .build();

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(factory);

    }
}
