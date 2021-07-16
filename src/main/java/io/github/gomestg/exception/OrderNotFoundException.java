package io.github.gomestg.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        super("Order Not Found");
    }
}
