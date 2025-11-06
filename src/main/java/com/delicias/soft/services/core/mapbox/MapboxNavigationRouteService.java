package com.delicias.soft.services.core.mapbox;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MapboxNavigationRouteService {

    @Value("${delicias.mapbox.token}")
    private String mapboxToken;


    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    public MapboxNavigationRouteService(
            RestTemplate restTemplate,
            ObjectMapper objectMapper)
    {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    public JsonNode getFirstRoute(List<double[]> coordinates) {

        String coordinateString = coordinates.stream()
                .map(coord -> coord[0] + "," + coord[1])
                .collect(Collectors.joining(";"));

        String url = "https://api.mapbox.com/directions/v5/mapbox/driving/" + coordinateString;
        String uri = UriComponentsBuilder.fromUriString(url)
                .queryParam("access_token", mapboxToken)
                .queryParam("geometries", "polyline6")
                .queryParam("overview", "full")
                .queryParam("annotations", "distance,duration,speed")
                .queryParam("steps", "true")
                .build()
                .toUriString();

        try {

            String response = restTemplate.getForObject(uri, String.class);

            if (response == null) {
                throw new RuntimeException("Respuesta vacía de Mapbox");
            }

            JsonNode root = objectMapper.readTree(response);
            JsonNode routes = root.path("routes");

            if (routes.isArray() && !routes.isEmpty()) {
                return routes.get(0); // ✅ primera ruta
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la respuesta de Mapbox", e);
        }


    }
}
