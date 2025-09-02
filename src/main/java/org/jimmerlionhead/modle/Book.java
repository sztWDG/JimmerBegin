package org.jimmerlionhead.modle;

import jakarta.annotation.Nullable;
import org.babyfish.jimmer.Immutable;

@Immutable
public interface Book {
    String name();

    double price();

    int edition();

    @Nullable
    String description();
}
