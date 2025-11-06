package com.delicias.soft.services.core.supabase.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record SupabaseOrderRouteDTO(

        @JsonProperty("order_id")
        Integer orderId,

        String geometry,

        Double distance,

        Double duration,

        Double latitude

) { }
