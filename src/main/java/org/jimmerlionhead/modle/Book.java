package org.jimmerlionhead.modle;

import jakarta.annotation.Nullable;
import org.babyfish.jimmer.Immutable;

import java.util.List;

@Immutable
public interface Book {
    String name();

    double price();

    int edition();

    @Nullable
    String description();

    BookStore bookStore();

    List<Author> authors();
}
