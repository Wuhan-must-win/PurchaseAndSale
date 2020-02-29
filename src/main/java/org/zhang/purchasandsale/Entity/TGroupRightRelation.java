package org.zhang.purchasandsale.Entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_group_right_relation")
public class TGroupRightRelation {

    @Id
    private String id;

    private String groupId;
    private String roleId;


}
