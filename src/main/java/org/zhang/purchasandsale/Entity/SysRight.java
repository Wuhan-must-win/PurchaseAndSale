package org.zhang.purchasandsale.Entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_right")
public class SysRight {

    @Id
    private String id;
    private String rightName;
    private String parentId;
    private String description;

}
