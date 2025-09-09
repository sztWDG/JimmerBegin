package org.jimmerlionhead.dynamic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNodePOJO {
    private Integer id;
    private String name;
    private Integer parentId;
    private List<TreeNodePOJO> children;


}
