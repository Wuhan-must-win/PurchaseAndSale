package org.zhang.purchasandsale.Entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_group")
public class SysGroup {


    @Id
    private String id;
    private String groupName;
    private String parentId;
    @Column(name = "create_date", columnDefinition = "datetime")
    private Date createDate;

}
