package org.jimmerlionhead.nullable;

import org.jimmerlionhead.modle.Book;
import org.jimmerlionhead.modle.BookDraft;

import java.util.List;
import java.util.Optional;

public class WhyNeedNullSafe {
    public static void main(String[] args) {
        Book book1 = BookDraft.$
                .produce(draft -> draft
                        .setName("java")
                        .setEdition(1)
                        .setPrice(100)
                        .setDescription(null)
                );
        System.out.println("book1 = " + book1);
        System.out.println("book1.name().equals(\"java\") = " + book1.name().equals("java") );
        boolean euquals = book1.description().equals("");

        try {
            Book book2 = BookDraft.$
                    .produce(draft -> draft
                            .setName(null)
                            .setEdition(1)
                            .setPrice(100)
                            .setDescription(null)
                    );
            System.out.println("book2 = " + book2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void npeIsBigProblem() {
        BookRecord bookRecord = new BookRecord("Java",
                new BookStoreRecord("MEANING", null),
                List.of(new AuthorRecord("LionHead", null))
        );

        // npe 问题
        bookRecord.bookstore().books().getFirst();
        bookRecord.authors().getFirst().lastName().equals("");
        // 以往解决方式，判空，巨麻烦
        // 方法一：
        if (bookRecord.bookstore() != null) {
            if (bookRecord.bookstore().books() != null) {
                // 以此类推
            }
        }
        // 方法二：
        Optional.ofNullable(bookRecord.bookstore())
                .map(BookStoreRecord::books);
                //.map() 继续链式调用，相比与上一种箭头代码好很多了


    }
}
