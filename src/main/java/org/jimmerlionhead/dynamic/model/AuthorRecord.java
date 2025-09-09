package org.jimmerlionhead.dynamic.model;

import java.time.LocalDate;
import java.util.List;

public record AuthorRecord(
        String firstName,
        String lastName,
        String gender,
        LocalDate birthday,
        List<BookRecord> books
) {
}
