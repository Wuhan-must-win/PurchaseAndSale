package org.zhang.purchasandsale.Entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_organization")
public class Organization {


    @Id
    private String id;

    private String parentId;
    private String orgName;
    @Column(name = "create_date", columnDefinition = "datetime")
    private Date createDate;
    private String description;
}
