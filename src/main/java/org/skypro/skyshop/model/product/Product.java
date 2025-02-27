package org.skypro.skyshop.model.product;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.ISearchable;

import java.util.Objects;

public abstract class Product implements ISearchable {
    final String nameProduct;

    public Product(String nameProduct) throws NullPointerException {
        this.nameProduct = nameProduct;

        if (nameProduct.isBlank()) {
            throw new NullPointerException("Имя продукта не может быть пустым");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Product product = (Product) obj;
        return Objects.equals(nameProduct, product.nameProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nameProduct);
    }

    @JsonIgnore
    @Override
    public String getTypeContent() {
        return "PRODUCT";
    }

    @JsonIgnore
    @Override
    public String searchTerm() {
        return nameProduct;
    }

    @Override
    public String getStringRepresentation() {
        return "";
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public abstract double getPriceProduct();

    public abstract boolean isSpecial();

}
