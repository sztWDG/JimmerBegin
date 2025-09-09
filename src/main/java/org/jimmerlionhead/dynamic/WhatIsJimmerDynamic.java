package org.jimmerlionhead.dynamic;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.babyfish.jimmer.ImmutableObjects;
import org.babyfish.jimmer.UnloadedException;
import org.jimmerlionhead.modle.*;

import java.time.LocalDate;
import java.util.List;

public class WhatIsJimmerDynamic {

    public static void main(String[] args) {

        anyPropertyCombination();

        // ⼀个完整的不可变对象，包含所有属性
        Book bookWithAllFields = BookDraft.$
                .produce(draft -> draft
                        .setName("Java")
                        .setEdition(1)
                        .setPrice(100)
                        .setDescription("learning java")
                );
        System.out.println("bookWithAllFields = " + bookWithAllFields);
        // ⼀个残缺的不可变对象，只包含name属性，其他属性都是未赋值，即unloaded状态
        Book bookWithoutDescription = BookDraft.$
                .produce(draft -> draft
                        .setName("Java")
                        .setEdition(1)
                        .setPrice(100)
                );
        // unloaded 未赋值的属性不会参与序列化
        System.out.println("bookWithoutDescription = " + bookWithoutDescription);
        // 访问未赋值的属性会抛出UnloadException
        try {
            String description = bookWithoutDescription.description();
            System.out.println("description = " + description);
        } catch (UnloadedException e) {
            e.printStackTrace();
        }
        // 可以使⽤⼯具⽅法判断属性是否已赋值
        if (ImmutableObjects.isLoaded(bookWithoutDescription, BookProps.DESCRIPTION)) {
            System.out.println("bookWithoutDescription.description() = " +
                    bookWithoutDescription.description());
        }

        // language=json
        String json = """
                {
                "name": "java",
                "price": 100.0,
                "description": null
                }
                """;
        // 注意区分 JSON 中不传某属性，与传某属性且值为 null 的区别
        // JSON 中不包含 edition 属性，所以反序列化后的不可变对象也没有该属性
        // JSON 中明确 description 属性的值为 null, 反序列化后也依旧为 null
        try {
            Book bookFromJson = ImmutableObjects.fromString(Book.class, json);
            System.out.println("bookFromJson = " + bookFromJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // Jimmer 对 unloaded 和 null 的处理
    public static void differenceBetweenUnloadedAndNull() {
        TreeNode treeNode = TreeNodeDraft.$.produce(draft -> draft.setName("food"));

        String sql = "select * from tree_node where name = ? ";

        if (ImmutableObjects.isLoaded(treeNode, TreeNodeProps.PARENT)) {
            if (treeNode.parent() == null) {
                sql += " and parent_id is null";
            } else {
                sql += " and parent_id = ?";
            }
        }
    }

    public static void anyPropertyCombination() {

        Book bookWithAllFields = BookDraft.$.
                produce(draft -> draft
                        .setName("Java")
                        .setEdition(1)
                        .setPrice(100)
                        .setDescription("Java For Beginners")
                        .setBookStore(
                                BookStoreDraft.$.produce(store -> store
                                        .setName("MANNING")
                                        .setWebsite(null)
                                )
                        )
                        .setAuthors(
                                List.of(
                                        AuthorDraft.$.produce(author -> author
                                                .setFirstName("Xinkai")
                                                .setLastName("Qiu")
                                                .setGender("M")
                                                .setBirthday(LocalDate.of(2002, 3, 1))
                                        )
                                )
                        )
                );
        System.out.println("bookWithAllFields = " + bookWithAllFields);

        Book bookAnyProperty = BookDraft.$
                .produce(draft -> draft
                        .setName("Java")
                        .setEdition(1)
                        .setPrice(100)
                        .applyBookStore(store -> store
                                .setName("MANNING")
                        )
                        .addIntoAuthors(author -> author
                                .setFirstName("Xinkai")
                                .setLastName("Qiu")
                        )

                );
        System.out.println("bookAnyProperty = " + bookAnyProperty);
    }
}
