package com.delicias.soft.services.core.supabase.deliverer.service;

import com.delicias.soft.services.core.supabase.deliverer.dto.SupabaseDelivererDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
public class CoreSupabaseDelivererService {

    private final RestTemplate restTemplate;

    @Value("${delicias.supabase.url}")
    private String supabaseUrl;

    @Value("${delicias.supabase.key}")
    private String supabaseKey;

    private final String URL_DELIVERERS = "/rest/v1/deliverers";


    public CoreSupabaseDelivererService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SupabaseDelivererDTO getDeliverer(UUID id) {
        try {

            String url = supabaseUrl + URL_DELIVERERS + "?id=eq." + id;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("apikey", supabaseKey);
            headers.set("Authorization", "Bearer " + supabaseKey);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<SupabaseDelivererDTO[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    SupabaseDelivererDTO[].class
            );

            return response.getBody() != null && response.getBody().length > 0
                    ? response.getBody()[0]
                    : null;


        } catch (Exception e) {
            log.warn("Fetch Supabase Delivery Not Found {}", id);
            return null;
        }
    }
}
