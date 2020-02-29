package org.zhang.purchasandsale.Entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_right_relation")
public class UserRightRelation {

    @Id
    private String id;

    private String userId;
    private String rightId;
    private Integer rightType;


}
