package org.zhang.purchasandsale.Entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_role")
public class SysRole {

    @Id
    private String id;
    private String roleName;
    private String parentId;
    private String description;

    @Column(name = "create_date", columnDefinition = "datetime")
    private Date createDate;

}
