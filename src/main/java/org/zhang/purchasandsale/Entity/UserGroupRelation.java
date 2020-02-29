package org.zhang.purchasandsale.Entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_group_relation")
public class UserGroupRelation {

    @Id
    private String id;

    private String userId;
    private String groupId;


}
