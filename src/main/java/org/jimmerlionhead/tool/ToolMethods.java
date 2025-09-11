package org.jimmerlionhead.tool;

import com.fasterxml.jackson.core.JsonProcessingException;
import kotlin.reflect.jvm.internal.impl.utils.DFS;
import org.babyfish.jimmer.DraftObjects;
import org.babyfish.jimmer.ImmutableObjects;
import org.babyfish.jimmer.UnloadedException;
import org.jimmerlionhead.modle.Book;
import org.jimmerlionhead.modle.BookDraft;
import org.jimmerlionhead.modle.BookProps;

public class ToolMethods {
    public static void main(String[] args) throws JsonProcessingException {

        forImmutableObjects();

        forDraftObjects();

    }

    // 不可变对象
    public static void forImmutableObjects() throws JsonProcessingException {

        Book book = BookDraft.$
                .produce(draft -> draft
                        .setName("Java")
                        .setEdition(1)
                        // .setPrice(100)
                        .setDescription(null)
                );
        // 直接访问 unloaded 的属性会抛出 UnloadedException
        try {
            double price = book.price();
        } catch (UnloadedException e) {
            e.printStackTrace();
        }

        // 判断实行是否已经复制
        boolean isNameLoaded = ImmutableObjects.isLoaded(book, BookProps.NAME);
        boolean isPriceLoaded = ImmutableObjects.isLoaded(book, BookProps.PRICE);
        if (isNameLoaded) {
            System.out.println("book.name() = " + book.name());
        }
        if (isPriceLoaded) {
            System.out.println("book.price() = " + book.price());
        }

        // get 获取值
        // 与 book.name() 是等价的, 通常使用 book.name()
        String name = ImmutableObjects.get(book, BookProps.NAME);
        System.out.println("name = " + name);

        // language=json
        String json = """
                {
                  "name": "Java",
                  "edition": 1,
                  "price": 100
                }
                """;
        Book bookFromJson = ImmutableObjects.fromString(Book.class, json);
        System.out.println("bookFromJson = " + bookFromJson);
    }

    // 可变对象
    public static void forDraftObjects() throws JsonProcessingException {
        // set 方法
        Book book1 = BookDraft.$
                .produce(draft -> draft
                        .setName("Java")
                        .setPrice(88)
                );
        System.out.println("book1 = " + book1);

        // unload 去除 price 属性，注意使用的是 DraftObjects 而不是 ImmutableObjects
        // DraftObjects 就是可变对象工具类，ImmutableObjects 是不可变对象工具类
        Book book2 = BookDraft.$
                .produce(book1, draft -> DraftObjects
                        .unload(draft, BookProps.PRICE)
                );
        System.out.println("book2 = " + book2);

        // 访问 unloaded 属性会抛出 UnloadedException
        try {
            double price = book2.price();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Book book3 = BookDraft.$
                .produce(book1, draft -> DraftObjects
                        .hide(draft, BookProps.PRICE)
                );
        System.out.println("book3 = " + book3);

        Book book4 = BookDraft.$
                .produce(book3, draft -> DraftObjects
                        .show(draft, BookProps.PRICE)
                );
        System.out.println("book4 = " + book4);
    }

}
