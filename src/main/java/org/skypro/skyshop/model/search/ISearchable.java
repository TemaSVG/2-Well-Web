package org.skypro.skyshop.model.search;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public interface ISearchable {
    UUID getId();

    String searchTerm();

    String getTypeContent();

    String getStringRepresentation();
}
