package org.jimmerlionhead.modle;

import org.babyfish.jimmer.Immutable;

import java.time.LocalDate;

@Immutable
public interface Author {
    String firstName();

    String lastName();

    String gender();

    LocalDate birthday();

}
