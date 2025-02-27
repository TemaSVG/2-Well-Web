package org.skypro.skyshop.model.product;

import java.util.UUID;

public class SimpleProduct extends Product {
    final int priceProduct;
    private final UUID id;

    public SimpleProduct(String nameProduct, int productPrice, UUID id) throws IllegalArgumentException {
        super(nameProduct);
        this.priceProduct = productPrice;
        this.id = id;
        if (productPrice <= 0){
            throw new IllegalArgumentException("Цена товара не может быть 0 или меньше");
        }
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public double getPriceProduct() {
        return priceProduct;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", nameProduct, getPriceProduct());
    }

    @Override
    public boolean isSpecial() {
        return false;
    }
}
