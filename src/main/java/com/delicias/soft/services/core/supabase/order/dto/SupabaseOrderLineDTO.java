package com.delicias.soft.services.core.supabase.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record SupabaseOrderLineDTO(
        Integer id,
        @JsonProperty("product_name")
        String productName,
        Integer quantity,
        Double price,
        String description,
        @JsonProperty("order_id")
        Integer orderId
) { }
