package org.zhang.purchasandsale.Entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "role_right_relation")
public class RoleRightRelation {

    @Id
    private String id;

    private String roleId;
    private String rightId;
    private Integer rightType;


}
