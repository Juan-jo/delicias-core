package com.delicias.soft.services.core.supabase.order.service;

import com.delicias.soft.services.core.common.OrderStatus;
import com.delicias.soft.services.core.supabase.order.dto.SupabaseOrderDTO;
import com.delicias.soft.services.core.supabase.order.dto.SupabaseOrderLineDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Set;


@Service
public class CoreSupabaseOrderService {


    private final RestTemplate restTemplate;


    @Value("${delicias.supabase.url}")
    private String supabaseUrl;

    @Value("${delicias.supabase.key}")
    private String supabaseKey;

    private final String URL_ORDERS = "/rest/v1/orders";
    private final String URL_ORDER_LINE = "/rest/v1/order_line";

    public CoreSupabaseOrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void changeStatus(Integer orderId, OrderStatus status) {

        String url = supabaseUrl + URL_ORDERS + "?id=eq." + orderId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseKey);
        headers.set("Authorization", "Bearer " + supabaseKey);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, String> requestBody = Collections.singletonMap("status", status.name());

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                entity,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.NO_CONTENT || response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Status actualizado correctamente para la orden " + orderId);
        } else {
            throw new RuntimeException("Error actualizando el status. Código HTTP: " + response.getStatusCode());
        }

    }

    public void saveOrder(SupabaseOrderDTO order) {

        String url = supabaseUrl + URL_ORDERS;


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseKey);
        headers.set("Authorization", "Bearer " + supabaseKey);

        HttpEntity<SupabaseOrderDTO> request = new HttpEntity<>(order, headers);

        restTemplate.postForEntity(url, request, String.class);

    }

    public void saveLines(Set<SupabaseOrderLineDTO> lines) {

        String url = supabaseUrl + URL_ORDER_LINE;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseKey);
        headers.set("Authorization", "Bearer " + supabaseKey);

        for (SupabaseOrderLineDTO line: lines) {

            HttpEntity<SupabaseOrderLineDTO> request = new HttpEntity<>(line, headers);

            restTemplate.postForEntity(url, request, String.class);
        }
    }


}
