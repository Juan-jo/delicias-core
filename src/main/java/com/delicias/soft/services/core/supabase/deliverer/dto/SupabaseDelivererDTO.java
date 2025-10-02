package com.delicias.soft.services.core.supabase.deliverer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;

@Builder
public record SupabaseDelivererDTO(
        UUID id,
        String name,
        @JsonProperty("logo_url")
        String logoUrl, // puede venir NULL
        Double rating  // puede venir NULL
) {
}
