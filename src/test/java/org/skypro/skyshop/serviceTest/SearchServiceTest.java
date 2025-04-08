//package org.skypro.skyshop.serviceTest;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.skypro.skyshop.model.article.Article;
//import org.skypro.skyshop.model.product.Product;
//import org.skypro.skyshop.model.product.SimpleProduct;
//import org.skypro.skyshop.model.service.StorageService;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class SearchServiceTest {
//
//    private StorageService storageService;
//
//    @BeforeEach
//    void setUp() {
//        storageService = mock(StorageService.class);
//    }
//
//    @Test
//    void testGetProductById() {
//        UUID id = UUID.randomUUID();
//        Product product = new SimpleProduct("TestProduct", 100, id);
//
//        when(storageService.getProductById(id)).thenReturn(Optional.of(product));
//
//        Optional<Product> result = storageService.getProductById(id);
//
//        assertTrue(result.isPresent());
//        assertEquals(product, result.get());
//    }
//
//    @Test
//    void testGetProductByIdNotFound() {
//        UUID id = UUID.randomUUID();
//
//        when(storageService.getProductById(id)).thenReturn(Optional.empty());
//
//        Optional<Product> result = storageService.getProductById(id);
//
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void testGetAllProducts() {
//        List<Product> products = List.of(
//                new SimpleProduct("Product1", 100, UUID.randomUUID()),
//                new SimpleProduct("Product2", 200, UUID.randomUUID())
//        );
//
//        when(storageService.getAllProducts()).thenReturn(products);
//
//        Collection<Product> result = storageService.getAllProducts();
//
//        assertEquals(products.size(), result.size());
//        assertTrue(result.containsAll(products));
//    }
//
//    @Test
//    void testGetAllArticles() {
//        List<Article> products = List.of(
//                new Article("Article1", "TestContent", UUID.randomUUID()),
//                new Article("Article2", "TestContent2", UUID.randomUUID())
//        );
//
//        when(storageService.getAllArticles()).thenReturn(products);
//
//        Collection<Article> result = storageService.getAllArticles();
//
//        assertEquals(products.size(), result.size());
//        assertTrue(result.containsAll(products));
//    }
//
//    @Test
//    void testGetAllSearchable() {
//        List<Product> products = List.of(
//                new SimpleProduct("Product1", 100, UUID.randomUUID()),
//                new SimpleProduct("Product2", 200, UUID.randomUUID())
//        );
//
//        List<Article> articles = List.of(
//                new Article("Article1", "TestContent", UUID.randomUUID()),
//                new Article("Article2", "TestContent2", UUID.randomUUID())
//        );
//
//        when(storageService.getAllProducts()).thenReturn(products);
//        when(storageService.getAllArticles()).thenReturn(articles);
//        when(storageService.getAllSearchable()).thenReturn((Collection) Stream.concat(products.stream(), articles.stream()).collect(Collectors.toList()));
//
//        int expectedSize = products.size() + articles.size();
//        Collection<?> result = storageService.getAllSearchable();
//
//        assertEquals(expectedSize, result.size());
//    }
//
//    @Test
//    void testGetAllSearchableEmpty() {
//        when(storageService.getAllSearchable()).thenReturn(List.of());
//        Collection<?> result = storageService.getAllSearchable();
//
//        assertEquals(0, result.size());
//        assertTrue(result.isEmpty());
//    }
//
//}
//
//
//
package org.skypro.skyshop.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.service.SearchService;
import org.skypro.skyshop.model.service.StorageService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchServiceTest {

    private StorageService storageService;
    private SearchService searchService;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        storageService = mock(StorageService.class);
        searchService = mock(SearchService.class);

    }

    @Test
    void testSearchNoObjects() {
        when(storageService.getAllSearchable()).thenReturn(List.of());

        Collection<SearchResult> result = searchService.search("pattern");

        assertTrue(result.isEmpty());
    }

    @Test
    void testSearchNoMatchingObjects() {
        List<Product> products = List.of(
                new SimpleProduct("Product1", 100, UUID.randomUUID()),
                new SimpleProduct("Product2", 200, UUID.randomUUID())
        );

        List<Article> articles = List.of(
                new Article("Article1", "TestContent", UUID.randomUUID()),
                new Article("Article2", "TestContent2", UUID.randomUUID())
        );

        when(storageService.getAllSearchable()).thenReturn((Collection) Stream.concat(products.stream(), articles.stream()).collect(Collectors.toList()));

        Collection<SearchResult> result = searchService.search("non-matching-pattern");

        assertTrue(result.isEmpty());
    }

    @Test
    void testSearchMatchingObject() {
        UUID id = UUID.randomUUID();
        Product matchingProduct = new SimpleProduct("MatchingProduct", 100, id);
        Product nonMatchingProduct = new SimpleProduct("NonMatchingProduct", 200, UUID.randomUUID());
        Collection<SearchResult> expectedResults =   List.of(
                new SearchResult(matchingProduct.getId(), matchingProduct.getNameProduct(), matchingProduct.getTypeContent()),
                new SearchResult(nonMatchingProduct.getId(), nonMatchingProduct.getNameProduct(), nonMatchingProduct.getTypeContent())
        );

        when(searchService.search("MatchingProduct")).thenReturn(expectedResults);

        assertEquals(1, searchService.search("MatchingProduct").stream().filter(result -> "MatchingProduct".equals(result.getName())).count());
        assertTrue(searchService.search("MatchingProduct").stream().anyMatch(result -> "MatchingProduct".equals(result.getName())));
    }

    @Test
    void testAddProduct() {
        UUID id = UUID.randomUUID();
        Product product = new SimpleProduct("TestProduct", 100, id);

        when(storageService.getProductById(id)).thenReturn(Optional.of(product));

        storageService.addProduct(product);

        Optional<Product> result = storageService.getProductById(id);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
    }
}
