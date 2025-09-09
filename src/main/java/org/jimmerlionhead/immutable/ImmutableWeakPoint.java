package org.jimmerlionhead.immutable;

import org.jimmerlionhead.modle.TreeNode;
import org.jimmerlionhead.modle.TreeNodeDraft;

import java.util.Collections;
import java.util.List;

public class ImmutableWeakPoint {
    public static void main(String[] args) {
        // 传统方式创建和修改不可变对象非常麻烦
        TreeNodeRecord rootRecord = new TreeNodeRecord(
                "Root",
                List.of(
                        new TreeNodeRecord(
                                "Food",
                                List.of(
                                        new TreeNodeRecord(
                                                "Drink",
                                                List.of(new TreeNodeRecord("Cococola", Collections.emptyList()),
                                                        new TreeNodeRecord("Fenta", Collections.emptyList())
                                                )
                                        )
                                )
                        )
                )
        );
        // 输出
        System.out.println("rootRecord = " + rootRecord);
        // 尤其是修改的时候，需要 copy 每一层级的所有属性
        // 这里只有两个属性，若有十个属性呢? 折磨
        TreeNodeRecord rootRecord2 = new TreeNodeRecord(
                rootRecord.name(),
                List.of(
                        new TreeNodeRecord(
                                rootRecord.children().getFirst().name(),
                                List.of(
                                        new TreeNodeRecord(
                                                rootRecord.children().getFirst().children().getFirst().name(),
                                                List.of(
                                                        new TreeNodeRecord("Cococola plus", Collections.emptyList()),
                                                        rootRecord.children().getFirst().children().getFirst().children().getFirst()
                                                )
                                        )
                                )
                        )
                )
        );
        // 输出
        System.out.println("rootRecord2 = " + rootRecord2);

        // Jimmer 中创建深层级不可变对象已经很简单了
        TreeNode rootNode1 = TreeNodeDraft.$
                .produce(root -> root
                        .setName("Root")
                        .addIntoChildren(food -> food
                                .setName("Food")
                                .addIntoChildren(drink -> drink
                                        .setName("Drink")
                                        .addIntoChildren(cococola -> cococola.setName("Cococala"))
                                        .addIntoChildren(fenta -> fenta.setName("Fenta"))
                                )
                        )
                );
        // 输出
        System.out.println("rootNode1 = " + rootNode1);

        // Jimmer 不可变对象修改时更加简单,只需要关注想要修改的属性即可，相当清爽
        TreeNode rootNode2 = TreeNodeDraft.$
                .produce(rootNode1, draft -> draft
                        .children(false).getFirst() // Food
                        .children(false).getFirst() // Drink
                        .children(false).getFirst() // Cococola
                        .setName("Cococola plus")
                );
        // 输出
        System.out.println("rootNode2 = " + rootNode2);
    }
}
