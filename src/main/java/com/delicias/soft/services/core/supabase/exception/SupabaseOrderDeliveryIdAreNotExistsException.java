package com.delicias.soft.services.core.supabase.exception;

public class SupabaseOrderDeliveryIdAreNotExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SupabaseOrderDeliveryIdAreNotExistsException(String message) {
        super(message);
    }

    public SupabaseOrderDeliveryIdAreNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
