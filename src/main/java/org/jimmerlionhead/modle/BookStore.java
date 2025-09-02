package org.jimmerlionhead.modle;

import jakarta.annotation.Nullable;
import org.babyfish.jimmer.Immutable;

@Immutable
public interface BookStore {
    String name();

    @Nullable
    String website();
}
