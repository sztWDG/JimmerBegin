package org.jimmerlionhead;

import org.jimmerlionhead.modle.Book;
import org.jimmerlionhead.modle.BookDraft;

public class Main {
    public static void main(String[] args) {
        // 有两种方法去创建不可变接口的实例对象

        //f
        Book book1 = BookDraft.$
                .produce(draft -> draft
                        .setName("Java")
                        .setEdition(1)
                        .setPrice(100)
                        .setDescription("Java For Beginners")
                );
    }
}
