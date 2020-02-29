package org.zhang.purchasandsale.Entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "group_right_relation")
public class GroupRightRelation {

    @Id
    private String id;

    private String groupId;
    private String rightId;
    private Integer rightType;


}
