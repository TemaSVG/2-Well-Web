package org.skypro.skyshop.model.basket;

import org.skypro.skyshop.model.service.BasketItem;

import java.util.List;

public final class UserBasket {
    private final List<BasketItem> items;
    private final double total;

    public UserBasket(List<BasketItem> items) {
        this.items = items;
        this.total = items.stream()
                .mapToDouble(item -> item.getProduct().getPriceProduct() * item.getQuantity())
                .sum();
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }
}