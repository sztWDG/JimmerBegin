package org.jimmerlionhead.dynamic;

import org.jetbrains.annotations.Nls;
import org.jimmerlionhead.dynamic.model.BookRecord;
import org.jimmerlionhead.dynamic.model.TreeNodePOJO;
import org.springframework.security.core.parameters.P;

public class WhyWeNeedDynamic {
    public static void main(String[] args) {
        BookRecord bookRecord = fromDB1();
        // 场景一：因为业务需求，有可能出现很多 DTO，而每个 DTO 都是一个单独文件，后期维护难度极大
        record BookDTO1(String name,
                        int edition,
                        double price,
                        String description
                        ){
        }
        record BookDTO2(String name,
                        double price,
                        String description
        ){
        }
        record BookDTO3(String name,
                        int edition,
                        double price
        ){
        }
    }

    public static BookRecord fromDB1() {
        return null;
    }

    // 场景二：null 是有歧义的，无法区分是没有赋值，还是赋值为 null
    public static void whatIsNull() {
        // 将设这个对象是前端通过 JSON 传到后端来的
        TreeNodePOJO treeNodePOJO = new TreeNodePOJO();
        treeNodePOJO.setName("name");
        treeNodePOJO.setParentId(null);
    }

    public static TreeNodePOJO findTreeNodeFromDB(TreeNodePOJO treeNodePOJO){
        // language= sql
        String sql = "select * from tree_node where name = 'name' and parent_id is null";

        if (treeNodePOJO.getName() != null) {
            sql += " and name = '" + treeNodePOJO.getName() + "'";
        }

        if (treeNodePOJO.getParentId() != null) {
            sql += " and parent = '" + treeNodePOJO.getParentId() + "'";
        }

        return null;
    }


}
