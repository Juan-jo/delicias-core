package com.delicias.soft.services.core.supabase.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;


@Builder
public record SupabaseOrderDTO(
        Integer id,
        @JsonProperty("user_id")
        UUID userId,
        String status,
        @JsonProperty("restaurant_id")
        Integer restaurantId,
        @JsonProperty("delivery_id")
        UUID deliveryId
) {
}
