package com.delicias.soft.services.core.supabase.order.service;

import com.delicias.soft.services.core.supabase.order.dto.SupabaseOrderRouteDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class CoreSupabaseOrderRouteService {

    private final RestTemplate restTemplate;


    @Value("${delicias.supabase.url}")
    private String supabaseUrl;

    @Value("${delicias.supabase.key}")
    private String supabaseKey;

    private final String URL_ORDER_ROUTE = "/rest/v1/order_route";

    public CoreSupabaseOrderRouteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void createRoute(SupabaseOrderRouteDTO orderRouteDTO) {

        try {

            String url = supabaseUrl + URL_ORDER_ROUTE;


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("apikey", supabaseKey);
            headers.set("Authorization", "Bearer " + supabaseKey);

            HttpEntity<SupabaseOrderRouteDTO> request = new HttpEntity<>(orderRouteDTO, headers);

            restTemplate.postForEntity(url, request, String.class);
        }
        catch(Exception e) {

            System.out.println("Error saving route(createRoute) -> "+e);

        }

    }

    public void patchRoute(SupabaseOrderRouteDTO orderRouteDTO) {

        try {

            String url = supabaseUrl + URL_ORDER_ROUTE + "?order_id=eq." + orderRouteDTO.orderId();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("apikey", supabaseKey);
            headers.set("Authorization", "Bearer " + supabaseKey);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            Map<String, Object> requestBody = Map.of(
                    "geometry", orderRouteDTO.geometry(),
                    "distance", orderRouteDTO.distance(),
                    "duration", orderRouteDTO.duration(),
                    "latitude", Optional.of(orderRouteDTO.latitude()).orElse(null)
            );

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            restTemplate.exchange(
                    url,
                    HttpMethod.PATCH,
                    entity,
                    String.class
            );
        }
        catch (Exception e) {
            System.out.println("Error saving route(patchRoute) -> "+e);
            createRoute(orderRouteDTO);
        }
    }

    public void patchDeliveryPosition(Integer orderId, Double latitude, Double longitude) {
        try {

            String url = supabaseUrl + URL_ORDER_ROUTE + "?order_id=eq." + orderId;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("apikey", supabaseKey);
            headers.set("Authorization", "Bearer " + supabaseKey);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            Map<String, Object> requestBody = Map.of(
                    "latitude", latitude,
                    "longitude", longitude
            );

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            restTemplate.exchange(
                    url,
                    HttpMethod.PATCH,
                    entity,
                    String.class
            );
        }
        catch (Exception e) {
            System.out.println("Error saving route(patchDeliveryPosition) -> "+e);
        }
    }

}
