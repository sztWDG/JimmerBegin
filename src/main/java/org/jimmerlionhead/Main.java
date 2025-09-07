package org.jimmerlionhead;

import org.jimmerlionhead.modle.Book;
import org.jimmerlionhead.modle.BookDraft;
import org.jimmerlionhead.modle.Immutables;

public class Main {
    public static void main(String[] args) {
        // 有两种方法去创建不可变接口的实例对象

        //方法一
        Book book1 = BookDraft.$
                .produce(draft -> draft
                        .setName("Java")
                        .setEdition(1)
                        .setPrice(100)
                        .setDescription("Java For Beginners")
                );
        // 方法二
        Book book2 = Immutables.createBook(draft -> draft
                .setName("Java")
                .setEdition(2)
                .setPrice(100)
                .setDescription("Java For Beginners")
        );
        System.out.println("Book1: " + book1);
        System.out.println("Book2: " + book2);

        // “修改” 不可变对象：基于已有的不可变对象，返回新的不可变对象
        // 方式一: 使用 Draft 代理中 produce 的重载版本
        Book book3 = BookDraft.$.produce(book1, draft -> draft.setPrice(88));
        //方式二: 使用 Immutables 聚合接口中的重载版本
        Book book4 = Immutables.createBook(book2, draft -> draft.setPrice(99));
        System.out.println("Book3: " + book3);
        System.out.println("Book4: " + book4);

        
    }
}
