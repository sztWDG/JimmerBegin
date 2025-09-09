package org.jimmerlionhead.dynamic.model;

import java.util.List;

public record BookStore(
        String name,
        String website,
        List<BookRecord> books
) {
}
