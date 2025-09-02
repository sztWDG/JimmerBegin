package org.jimmerlionhead.modle;

import org.babyfish.jimmer.Immutable;

import java.util.List;

@Immutable
public interface TreeNode {
    String name();

    List<TreeNode> children();
}
