package org.jimmerlionhead.nullable;

import java.util.List;

public record BookStoreRecord(String name, List<BookRecord> books) {

}

record BookRecord(String name, BookStoreRecord bookstore, List<AuthorRecord> authors){

}

record AuthorRecord(String firstName, String lastName){

}
