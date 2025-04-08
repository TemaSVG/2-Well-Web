package org.skypro.skyshop.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.service.BasketService;
import org.skypro.skyshop.model.service.StorageService;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BasketServiceTest {

    private ProductBasket productBasket;
    private StorageService storageService;
    private BasketService basketService;

    @BeforeEach
    void setUp() {
        productBasket = mock(ProductBasket.class);
        storageService = mock(StorageService.class);
        basketService = new BasketService(productBasket, storageService);
    }

    @Test
    void testAddNonExistentProductToBasketThrowsException() {
        UUID id = UUID.randomUUID();
        when(storageService.getProductById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class, () -> basketService.addProductToBasket(id));
    }

    @Test
    void testAddExistingProductCallsAddProductOnProductBasket() {
        UUID id = UUID.randomUUID();
        Product product = new SimpleProduct("TestProduct", 100, id);
        when(storageService.getProductById(id)).thenReturn(Optional.of(product));

        basketService.addProductToBasket(id);

        verify(productBasket, times(1)).addProduct(id);
    }

    @Test
    void testGetUserBasketReturnsEmptyBasketWhenProductBasketIsEmpty() {
        when(productBasket.getProducts()).thenReturn(Map.of());

        UserBasket userBasket = basketService.getUserBasket();

        assertTrue(userBasket.getItems().isEmpty());
        assertEquals(0, userBasket.getTotal());
    }

    @Test
    void testGetUserBasketReturnsCorrectBasketWhenProductBasketHasItems() {
        UUID id = UUID.randomUUID();
        Product product = new SimpleProduct("TestProduct", 100, id);
        when(productBasket.getProducts()).thenReturn(Map.of(id, 2));
        when(storageService.getProductById(id)).thenReturn(Optional.of(product));

        UserBasket userBasket = basketService.getUserBasket();

        assertEquals(1, userBasket.getItems().size());
        assertEquals(product, userBasket.getItems().get(0).getProduct());
        assertEquals(2, userBasket.getItems().get(0).getQuantity());
        assertEquals(200, userBasket.getTotal());
    }

    @Test
    void testGetUserBasketThrowsExceptionWhenProductNotFound() {
        UUID id = UUID.randomUUID();
        when(productBasket.getProducts()).thenReturn(Map.of(id, 2));
        when(storageService.getProductById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class, () -> basketService.getUserBasket());
    }


}