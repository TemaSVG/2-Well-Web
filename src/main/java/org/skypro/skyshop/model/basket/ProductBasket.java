package org.skypro.skyshop.model.basket;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProductBasket implements Serializable {
    private final Map<UUID, Integer> products;

    public ProductBasket() {
        products = new HashMap<>();
    }

    public void addProduct(UUID id) {
        products.merge(id, 1, Integer::sum);
    }

    public Map<UUID, Integer> getProducts() {
        return Map.copyOf(products);
    }
}
