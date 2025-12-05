package com.delicias.soft.services.core.kafka;

import java.util.UUID;

public record KafkaOrderDeliveryPositionDTO(
        Integer orderId,
        Integer delivererId,
        UUID navigationRouteId,
        Integer deliveryOrderRelId,
        Double latitude,
        Double longitude
) { }