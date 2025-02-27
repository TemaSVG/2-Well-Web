package org.skypro.skyshop.model.article;

import org.skypro.skyshop.model.search.ISearchable;

import java.util.Objects;
import java.util.UUID;

public class Article implements ISearchable {
    String articleTitle;
    String articleContent;
    private final UUID id;

    public Article(String articleTitle, String articleContent, UUID id) {
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        Article article = (Article) obj;
        return Objects.equals(articleTitle, article.articleTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleTitle, articleContent);
    }

    @Override
    public String searchTerm() {
        return toString();
    }

    @Override
    public String getTypeContent() {
        return "ARTICLE";
    }

    @Override
    public String getStringRepresentation() {
        return "";
    }

    @Override
    public String toString() {
        return articleTitle + " " + articleContent;
    }
}
