package org.jimmerlionhead.dynamic.model;

import java.util.List;

public record BookRecord(
        String name,
        int edition,
        double price,
        String description,
        BookStore bookStore,
        List<AuthorRecord> authors
) {
}
