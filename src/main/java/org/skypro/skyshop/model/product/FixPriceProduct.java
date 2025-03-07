package org.skypro.skyshop.model.product;


import java.util.UUID;

public class FixPriceProduct extends Product {
    final int FIX_PRICE_PRODUCT;
    private final UUID id;

    public FixPriceProduct(String nameProduct, int FIX_PRICE_PRODUCT, UUID id) {
        super(nameProduct);
        this.FIX_PRICE_PRODUCT = FIX_PRICE_PRODUCT;
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public double getPriceProduct() {
        return FIX_PRICE_PRODUCT;
    }

    @Override
    public String toString() {
        return String.format("%s: Фиксированная цена %s", nameProduct, getPriceProduct());
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
