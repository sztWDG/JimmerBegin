package org.jimmerlionhead.immutable;

import org.jimmerlionhead.modle.Book;
import org.jimmerlionhead.modle.BookDraft;
import org.jimmerlionhead.modle.Immutables;
import org.jimmerlionhead.modle.TreeNode;

import java.util.Map;

public class WhyImmutable {
    public static void main(String[] args) {
        // Jimmer 不会允许循环引用的存在，会直接在编译器报出异常
        // noCircularReference();

        // Map: 可变的修改后会导致 map 对应的 value 为 null
        immutableKey();

    }

    public static void noCircularReference() {
        try {
            TreeNode aggregateRoot = Immutables.createTreeNode(aggregateRootDraft ->
                    aggregateRootDraft
                            .setName("Aggregate Root")
                            .addIntoChildren(childDraft ->
                                            childDraft
                                                    .setName("Child")
                                                    .setParent(aggregateRootDraft)
                                    //抛出CircularReferenceException
                            )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void threadSafe() {
        MutableBook mutableBook = MutableBook.builder()
                .name("My Book")
                .edition(1)
                .price(100)
                .description("My description")
                .build();
//        MutableBook mutableBook = new MutableBook();
//        mutableBook.setName("My Book");
//        mutableBook.setEdition(1);
//        mutableBook.setPrice(100);
//        mutableBook.setDescription("Mutable Book");

        Book immutableBook = BookDraft.$
                .produce(draft -> draft
                        .setName("Immutable Book")
                        .setEdition(2)
                        .setPrice(1000)
                        .setDescription("Immutable Book")
                );

        // 多线程环境下修改可变对象可能会导致数据不一致
        // 要想在多线程环境下安全使用可变对象，需要额外的同步机制，会降低性能
        new Thread(() -> {
            mutableBook.setPrice(200);
        }).start();

        // 不可变对象在多线程环境下是安全的
        new Thread(() -> {
            // 不能修改不可变对象
            // immutableBook.setPrice(); // 编译错误，压根就没有这个 set 方法
        }).start();
    }

    public static void immutableKey() {
        MutableBook mutableBook = new MutableBook();
        mutableBook.setPrice(100);

        Book immutableBook = BookDraft.$.produce(draft -> draft.setPrice(100));

        Map<MutableBook, Integer> mapOfMutableKey = Map.of(mutableBook, 100);
        Map<Book, Integer> mapOfImmutableKey = Map.of(immutableBook, 100);
        // 修改了属性，导致 hash 变化
        mutableBook.setPrice(200);
        // 与之前同样的属性，却无法工作
        MutableBook mutableBook2 = new MutableBook();
        mutableBook2.setPrice(100);

        Integer price1 = mapOfMutableKey.get(mutableBook2);
        System.out.println("price1: " + price1);
        // 不可变对象则可以正常工作
        Book immutableBook2 = BookDraft.$.produce(draft -> draft.setPrice(100));
        Integer price2 = mapOfImmutableKey.get(immutableBook2);
        System.out.println("price2: " + price2);
    }
    
}
