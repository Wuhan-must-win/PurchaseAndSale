package org.zhang.purchasandsale.Entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_rule_relation")
public class UserRuleRelation {

    @Id
    private String id;

    private String userId;
    private String roleId;


}
