package org.skypro.skyshop.model.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StorageService {
    private final Map<UUID, Product> productStorage;
    private final Map<UUID, Article> articleStorage;

    public StorageService(Map<UUID, Product> productStorage, Map<UUID, Article> articleStorage) {
        this.productStorage = productStorage;
        this.articleStorage = articleStorage;
        initializeStorage();
    }

    private void initializeStorage() {
        List<Product> products = List.of(
                new SimpleProduct("Капуста", 100, UUID.randomUUID()),
                new DiscountedProduct("Морковь", 20, 200, UUID.randomUUID())
        );

        List<Article> articles = List.of(
                new Article("Сырочек", "Очень вкусный", UUID.randomUUID()),
                new Article("Булка", "вкусная", UUID.randomUUID())
        );

        products.forEach(product -> productStorage.put(product.getId(), product));
        articles.forEach(article -> articleStorage.put(article.getId(), article));
    }

    public Collection<Product> getAllProducts() {
        return productStorage.values();
    }

    public Collection<Article> getAllArticles() {
        return articleStorage.values();
    }

    public Collection<SearchResult> getAllSearchable() {
        return Stream.concat(
                        productStorage.values().stream(),
                        articleStorage.values().stream()
                )
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }
}
